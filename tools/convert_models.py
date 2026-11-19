"""Convert 1.12.2 box models (ModelRenderer code) into Minecraft 1.21 model code.

Old style (1.12.2 / Techne / MDXLib):
    this.head = new ModelRenderer(this, 0, 65);
    this.head.setRotationPoint(0.0F, 21.5F, 4.5F);
    this.head.addBox(-3.5F, -2.5F, -7.0F, 7, 5, 7, 0.0F);
    this.setRotation(head, 0.4F, 0F, 0F);
    this.head.addChild(this.beak);

New style (1.21):
    PartDefinition head = root.addOrReplaceChild("head",
        CubeListBuilder.create().texOffs(0, 65).addBox(-3.5F, -2.5F, -7.0F, 7, 5, 7),
        PartPose.offsetAndRotation(0.0F, 21.5F, 4.5F, 0.4F, 0F, 0F));

The geometry is identical (same units, same pivots, same texture layout), so the
textures from the old mod work unchanged.

Usage (from the repo root):
    python tools/convert_models.py            # converts every model under legacy-1.12.2/.../client/model
Writes:  src/main/java/org/avp/client/model/geometry/<Name>Model.java
"""
import os, re, sys, glob

SRC = "legacy-1.12.2/src/main/java/org/avp/client/model"
OUT = "src/main/java/org/avp/client/model/geometry"
PKG = "org.avp.client.model.geometry"

NUM = r"\s*(-?[\d.]+(?:[eE]-?\d+)?)[FfDd]?\s*"


def num(s):
    return float(s.rstrip("FfDd"))


def expr(s):
    """Evaluate a Java number expression such as -((float) Math.PI / 5F)."""
    e = re.sub(r"\((?:float|double|int)\)", "", s)
    e = e.replace("Math.PI", "3.141592653589793")
    e = re.sub(r"(\d(?:\.\d*)?(?:[eE]-?\d+)?)[FfDd]\b", r"\1", e)
    if not re.fullmatch(r"[\d.eE+\-*/() ]+", e):
        raise ValueError(s)
    return float(eval(e, {"__builtins__": {}}))


def args(s):
    """Split 'a, (b, c), d' at top-level commas."""
    out, depth, cur = [], 0, ""
    for ch in s:
        if ch == "," and depth == 0:
            out.append(cur.strip()); cur = ""
            continue
        depth += (ch == "(") - (ch == ")")
        cur += ch
    if cur.strip():
        out.append(cur.strip())
    return out


def fl(x):
    """Format a float as Java source."""
    if x == int(x):
        return f"{int(x)}.0F"
    return f"{x!r}F".replace("e", "E")


def constructor_body(src, cls):
    m = re.search(r"public\s+" + cls + r"\s*\([^)]*\)\s*\{", src)
    if not m:
        return None
    i, depth = m.end(), 1
    while depth and i < len(src):
        depth += {"{": 1, "}": -1}.get(src[i], 0)
        i += 1
    return src[m.end():i - 1]


def strip_this(s):
    return re.sub(r"\bthis\.", "", s).strip()


class Part:
    def __init__(self, name, u, v):
        self.name, self.u, self.v = name, u, v
        self.pivot = [0.0, 0.0, 0.0]
        self.offset = [0.0, 0.0, 0.0]
        self.rot = [0.0, 0.0, 0.0]
        self.mirror = False
        self.boxes = []  # (u, v, mirror, x, y, z, w, h, d, inflate)
        self.children = []
        self.parent = None
        self.tex_size = None  # set when constructed


def convert(path):
    src = open(path, encoding="utf-8", errors="replace").read()
    cls = os.path.basename(path)[:-5]
    body = constructor_body(src, cls)
    warnings = []
    if body is None:
        return None, [f"{cls}: no constructor found"]
    tw = re.search(r"textureWidth\s*=" + NUM + ";", body)
    th = re.search(r"textureHeight\s*=" + NUM + ";", body)
    tex_w = int(num(tw.group(1))) if tw else 64
    tex_h = int(num(th.group(1))) if th else 32
    parts = {}
    order = []

    cur_size = [tex_w, tex_h]
    for stmt in split_statements(body):
        s = re.sub(r"//[^\n]*", "", strip_this(stmt)).strip()
        if not s:
            continue
        try:
            handled = parse_statement(s, parts, order, cur_size, warnings, cls)
        except (ValueError, KeyError, IndexError, SyntaxError) as e:
            handled = False
        if not handled:
            warnings.append(f"{cls}: could not convert '{s[:90]}'")

    # The layer uses the texture size most boxes were made with; other boxes get a texture scale.
    sizes = [b[10] for p in parts.values() for b in p.boxes]
    if sizes:
        tex_w, tex_h = max(set(sizes), key=sizes.count)

    roots = [n for n in order if parts[n].parent is None]
    new_name = (cls[5:] if cls.startswith("Model") else cls) + "Model"
    if new_name[0].isdigit():
        new_name = "Gun" + new_name  # Model88MOD4 -> Gun88MOD4Model (Java names can't start with a digit)

    def cubes(p):
        if not p.boxes:
            return "CubeListBuilder.create()"
        out, last_uv, last_mirror = "CubeListBuilder.create()", None, False
        for (u, v, mirror, x, y, z, w, h, d, infl, size) in p.boxes:
            if (u, v) != last_uv:
                out += f".texOffs({u}, {v})"
                last_uv = (u, v)
            if mirror != last_mirror:
                out += ".mirror()" if mirror else ".mirror(false)"
                last_mirror = mirror
            args = f"{fl(x)}, {fl(y)}, {fl(z)}, {fl(w)}, {fl(h)}, {fl(d)}"
            if size != (tex_w, tex_h):
                # box was made for a different texture size in 1.12 -> scale its UVs to match
                out += f".addBox({args}, new CubeDeformation({fl(infl)}), {fl(size[0] / tex_w)}, {fl(size[1] / tex_h)})"
            elif infl:
                out += f".addBox({args}, new CubeDeformation({fl(infl)}))"
            else:
                out += f".addBox({args})"
        return out

    def pose(p):
        # 1.12 "offset" is in blocks and applied before the pivot; it's the same as moving the pivot
        px, py, pz = (p.pivot[i] + p.offset[i] * 16 for i in range(3))
        if any(p.rot):
            return f"PartPose.offsetAndRotation({fl(px)}, {fl(py)}, {fl(pz)}, {fl(p.rot[0])}, {fl(p.rot[1])}, {fl(p.rot[2])})"
        return f"PartPose.offset({fl(px)}, {fl(py)}, {fl(pz)})"

    defs, fields, assigns = [], [], []

    def emit(name, parent_var):
        p = parts[name]
        var = f"p_{name}"
        defs.append(f"        PartDefinition {var} = {parent_var}.addOrReplaceChild(\"{name}\",\n"
                    f"                {cubes(p)},\n                {pose(p)});")
        fields.append(f"    public final ModelPart {name};")
        assigns.append(f"        this.{name} = {'root' if parent_var == 'root' else 'this.' + parent_var[2:]}.getChild(\"{name}\");")
        assigns.append(f"        this.parts.put(\"{name}\", this.{name});")
        for c in p.children:
            emit(c, var)

    for r in roots:
        emit(r, "root")
    if not defs:
        return None, [f"{cls}: no parts found"]

    layer_id = re.sub(r"(?<!^)(?=[A-Z])", "_", new_name[:-5]).lower()
    unused = "    @SuppressWarnings(\"unused\")\n" if any(not parts[r].children for r in roots) else ""
    java = f"""package {PKG};

import java.util.LinkedHashMap;
import java.util.Map;

import org.avp.AliensVsPredator;
import org.avp.client.model.GeneratedModel;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

/**
 * Geometry converted automatically from the 1.12.2 {cls} by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class {new_name} implements GeneratedModel
{{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "{layer_id}"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
{chr(10).join(fields)}

    public {new_name}(ModelPart root)
    {{
        this.root = root;
{chr(10).join(assigns)}
    }}

    @Override
    public ModelPart root()
    {{
        return root;
    }}

    @Override
    public Map<String, ModelPart> parts()
    {{
        return parts;
    }}

    public static LayerDefinition createLayer()
    {{
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
{chr(10).join(defs)}
        return LayerDefinition.create(mesh, {tex_w}, {tex_h});
    }}
}}
"""
    return (new_name, layer_id, java), warnings


def split_statements(body):
    out, depth, cur = [], 0, ""
    for ch in body:
        if ch == ";" and depth == 0:
            out.append(cur); cur = ""
            continue
        depth += (ch == "(") - (ch == ")")
        cur += ch
    return out


def parse_statement(s, parts, order, cur_size, warnings, cls):
    if m := re.fullmatch(r"(\w+)\s*=\s*new (?:ModelRenderer|Part)\((.*)\)", s, re.S):
        a = args(m.group(2))
        if a[0] != "this" or len(a) not in (1, 3):
            return False
        u, v = (int(expr(a[1])), int(expr(a[2]))) if len(a) == 3 else (0, 0)
        p = Part(m.group(1), u, v)
        p.tex_size = tuple(cur_size)
        parts[p.name] = p
        order.append(p.name)
        return True
    if m := re.fullmatch(r"(textureWidth|textureHeight)\s*=\s*(.+)", s):
        cur_size[0 if m.group(1) == "textureWidth" else 1] = int(expr(m.group(2)))
        return True
    if m := re.fullmatch(r"setTextureDimensions\((.*)\)", s):
        a = args(m.group(1))
        cur_size[0], cur_size[1] = int(expr(a[0])), int(expr(a[1]))
        return True
    if m := re.fullmatch(r"(\w+)\.mirror\s*=\s*(true|false)", s):
        parts[m.group(1)].mirror = m.group(2) == "true"
        return True
    if m := re.fullmatch(r"(\w+)\.rotateAngle([XYZ])\s*=\s*(.+)", s):
        parts[m.group(1)].rot["XYZ".index(m.group(2))] = expr(m.group(3))
        return True
    if m := re.fullmatch(r"(\w+)\.(offset|rotationPoint)([XYZ])\s*=\s*(.+)", s):
        target = parts[m.group(1)].offset if m.group(2) == "offset" else parts[m.group(1)].pivot
        target["XYZ".index(m.group(3))] = expr(m.group(4))
        return True
    if m := re.fullmatch(r"(?:setRotation|setRotateAngle)\((.*)\)", s, re.S):
        a = args(m.group(1))
        parts[strip_this(a[0])].rot = [expr(x) for x in a[1:4]]
        return True
    if m := re.fullmatch(r"(\w+)\.(\w+)\((.*)\)", s, re.S):
        p, call, a = parts[m.group(1)], m.group(2), args(m.group(3))
        if call == "setRotationPoint":
            p.pivot = [expr(x) for x in a]
        elif call == "addBox" and len(a) in (6, 7):
            vals = [expr(x) for x in a[:6]]
            infl = expr(a[6]) if len(a) == 7 else 0.0
            p.boxes.append((p.u, p.v, p.mirror, *vals, infl, p.tex_size))
        elif call == "setTextureOffset":
            p.u, p.v = int(expr(a[0])), int(expr(a[1]))
        elif call == "setTextureSize":
            p.tex_size = (int(expr(a[0])), int(expr(a[1])))
        elif call == "addChild":
            child = parts[strip_this(a[0])]
            if child.parent:
                warnings.append(f"{cls}: {child.name} has two parents ({child.parent} and {p.name}); kept the first")
            else:
                child.parent = p.name
                p.children.append(child.name)
        else:
            return False
        return True
    if re.fullmatch(r"super\(.*\)|this\(.*\)", s, re.S):
        return True
    if re.fullmatch(r"(\w+)\.(isHidden|showModel)\s*=.*", s):
        warnings.append(f"{cls}: ignored visibility setting '{s}'")
        return True
    return False


def main():
    os.makedirs(OUT, exist_ok=True)
    files = sorted(glob.glob(f"{SRC}/**/*.java", recursive=True))
    done, all_warnings = [], []
    for f in files:
        src = open(f, encoding="utf-8", errors="replace").read()
        if "new ModelRenderer" not in src and "new Part(" not in src:
            continue
        result, warnings = convert(f)
        all_warnings += warnings
        if result:
            new_name, layer_id, java = result
            open(f"{OUT}/{new_name}.java", "w").write(java)
            done.append((new_name, os.path.relpath(f, SRC)))
    reg = "\n".join(f"        event.registerLayerDefinition({n}.LAYER, {n}::createLayer);" for n, _ in done)
    open(f"{OUT}/AllModelLayers.java", "w").write(f"""package {PKG};

import net.neoforged.neoforge.client.event.EntityRenderersEvent;

/** Registers the geometry of every converted model. Generated by tools/convert_models.py. */
public final class AllModelLayers
{{
    public static void register(EntityRenderersEvent.RegisterLayerDefinitions event)
    {{
{reg}
    }}

    private AllModelLayers()
    {{
    }}
}}
""")
    print(f"converted {len(done)} models into {OUT}")
    with open("tools/convert_models.log", "w") as log:
        for n, f in done:
            log.write(f"OK   {f} -> {n}\n")
        for w in all_warnings:
            log.write(f"WARN {w}\n")
    print(f"{len(all_warnings)} warnings, see tools/convert_models.log")


if __name__ == "__main__":
    main()
