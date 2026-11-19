"""Port the simple (plain cube) blocks and simple items from the 1.12.2 AvP mod to 1.20.1.

Reads the legacy BlockHandler/ItemHandler + assets, writes:
  - Java registration code (ModBlocks / ModItems content lists)
  - blockstates, block/item models, textures, loot tables, mineable tags, en_us.json
"""
import json, os, re, shutil, sys

REPO = sys.argv[1]
LEG = os.path.join(REPO, "legacy-1.12.2/src/main")
LJ = os.path.join(LEG, "java/org/avp")
LA = os.path.join(LEG, "resources/assets/avp")
NEW = os.path.join(REPO, "src/main")
NA = os.path.join(NEW, "resources/assets/avp")
ND = os.path.join(NEW, "resources/data")
JAVA = os.path.join(NEW, "java/org/avp")


def new_id(old):
    return re.sub(r"[^a-z0-9_]", "_", old.lower())


def write_json(path, obj):
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, "w") as f:
        json.dump(obj, f, indent=2)
        f.write("\n")


# ---- old English names -------------------------------------------------
names = {}
with open(os.path.join(LA, "lang/en_US.lang"), encoding="utf-8", errors="replace") as f:
    for line in f:
        m = re.match(r"(tile|item)\.avp:(.+?)\.name=(.*)", line.strip())
        if m:
            names[(m.group(1), m.group(2))] = m.group(3).strip()

# old 1.12 Material -> (1.20 "kind" used by ModBlocks helper, mineable tool)
MATERIAL = {
    "IRON": ("METAL", "pickaxe"),
    "ROCK": ("STONE", "pickaxe"),
    "CLAY": ("PLASTIC", "pickaxe"),
    "CLOTH": ("PADDING", None),
    "SAND": ("SAND", "shovel"),
    "WOOD": ("RESIN", "axe"),
}

# ---- blocks ------------------------------------------------------------
blocks, skipped = [], []
src = open(os.path.join(LJ, "BlockHandler.java"), encoding="utf-8").read()
for m in re.finditer(r"public static final Block (\w+)\s*=\s*new BlockMaterial\(Material\.(\w+)\)(.*?);", src):
    field, mat, chain = m.groups()
    rn = re.search(r'setRegistryName\("([^"]+)"\)', chain).group(1)
    bs_path = os.path.join(LA, "blockstates", rn + ".json")
    tex = None
    if os.path.exists(bs_path):
        bs = json.load(open(bs_path))
        variants = json.dumps(bs.get("variants", {}))
        t = bs.get("defaults", {}).get("textures", {})
        if "cube_all" in variants and "all" in t and len(t) == 1:
            tex = t["all"].split(":")[1]  # e.g. blocks/shippanel
    if tex is None or mat not in MATERIAL or not os.path.exists(os.path.join(LA, "textures", tex + ".png")):
        skipped.append(rn)
        continue
    num = lambda k, d: float(re.search(k + r"\(([\d.]+)F?\)", chain).group(1)) if re.search(k + r"\(", chain) else d
    blocks.append(dict(
        field=field, old=rn, id=new_id(rn), kind=MATERIAL[mat][0], tool=MATERIAL[mat][1],
        hardness=num("setHardness", 1.5), resistance=num("setResistance", num("setHardness", 1.5)),
        light=round(num("setLightLevel", 0) * 15), translucent="TRANSLUCENT" in chain, tex=tex,
        tab="BLOCKS" if "Tab.MAIN" not in chain else "MAIN",
        name=names.get(("tile", rn)) or rn,
    ))

# ---- items -------------------------------------------------------------
items = []
src = open(os.path.join(LJ, "ItemHandler.java"), encoding="utf-8").read()
for m in re.finditer(r"public static final Item (\w+)\s*=\s*new (HookedItem|ItemFood)\(([^)]*)\)(.*?);", src):
    field, cls, args, chain = m.groups()
    rn = re.search(r'setRegistryName\("([^"]+)"\)', chain).group(1)
    tex = os.path.join(LA, "textures/items", rn + ".png")
    if not os.path.exists(tex):
        skipped.append(rn)
        continue
    stack = re.search(r"setMaxStackSize\((\d+)\)", chain)
    tab = re.search(r"setCreativeTab\(Tab\.(\w+)\)", chain)
    it = dict(field=field, old=rn, id=new_id(rn), stack=int(stack.group(1)) if stack else 64,
              tab=tab.group(1) if tab else "MAIN", name=names.get(("item", rn)) or rn, food=None)
    if "setMaxDamage" in chain:  # has durability -> needs real logic later
        skipped.append(rn)
        continue
    if cls == "ItemFood":
        a = [x.strip().rstrip("F") for x in args.split(",")]
        it["food"] = dict(nutrition=int(a[0]), saturation=float(a[1]) if len(a) == 3 else 0.6,
                          meat=a[-1] == "true", always="setAlwaysEdible" in chain)
    items.append(it)

# ---- write assets ------------------------------------------------------
lang = {
    "itemGroup.avp.main": "AvP: Main",
    "itemGroup.avp.blocks": "AvP: Blocks",
    "itemGroup.avp.crafting": "AvP: Crafting",
    "itemGroup.avp.gun_parts": "AvP: Gun Parts",
}
tags = {"pickaxe": [], "shovel": [], "axe": []}
for b in blocks:
    i = b["id"]
    tex_new = "block/" + i
    os.makedirs(os.path.join(NA, "textures/block"), exist_ok=True)
    shutil.copy(os.path.join(LA, "textures", b["tex"] + ".png"), os.path.join(NA, "textures", tex_new + ".png"))
    meta = os.path.join(LA, "textures", b["tex"] + ".png.mcmeta")
    if os.path.exists(meta):
        shutil.copy(meta, os.path.join(NA, "textures", tex_new + ".png.mcmeta"))
    write_json(f"{NA}/blockstates/{i}.json", {"variants": {"": {"model": f"avp:block/{i}"}}})
    model = {"parent": "minecraft:block/cube_all", "textures": {"all": f"avp:{tex_new}"}}
    if b["translucent"]:
        model["render_type"] = "minecraft:translucent"
    write_json(f"{NA}/models/block/{i}.json", model)
    write_json(f"{NA}/models/item/{i}.json", {"parent": f"avp:block/{i}"})
    write_json(f"{ND}/avp/loot_tables/blocks/{i}.json", {
        "type": "minecraft:block",
        "pools": [{"rolls": 1, "entries": [{"type": "minecraft:item", "name": f"avp:{i}"}],
                   "conditions": [{"condition": "minecraft:survives_explosion"}]}]})
    if b["tool"]:
        tags[b["tool"]].append(f"avp:{i}")
    lang[f"block.avp.{i}"] = b["name"]

os.makedirs(os.path.join(NA, "textures/item"), exist_ok=True)
for it in items:
    i = it["id"]
    shutil.copy(os.path.join(LA, "textures/items", it["old"] + ".png"), os.path.join(NA, "textures/item", i + ".png"))
    write_json(f"{NA}/models/item/{i}.json", {"parent": "minecraft:item/generated", "textures": {"layer0": f"avp:item/{i}"}})
    lang[f"item.avp.{i}"] = it["name"]

for tool, vals in tags.items():
    if vals:
        write_json(f"{ND}/minecraft/tags/blocks/mineable/{tool}.json", {"replace": False, "values": vals})
write_json(f"{NA}/lang/en_us.json", lang)

# ---- write Java --------------------------------------------------------
def const(i):
    return i.upper()

def f(x):
    return f"{x:g}F"

bl = []
for b in blocks:
    extra = ""
    if b["light"] or b["translucent"]:
        extra = f', {b["light"]}, {"true" if b["translucent"] else "false"}'
    bl.append(f'    public static final RegistryObject<Block> {const(b["id"])} = simple("{b["id"]}", Kind.{b["kind"]}, {f(b["hardness"])}, {f(b["resistance"])}{extra});')

il = []
for it in items:
    if it["food"]:
        fd = it["food"]
        il.append(f'    public static final RegistryObject<Item> {const(it["id"])} = food("{it["id"]}", {fd["nutrition"]}, {f(fd["saturation"])}, {str(fd["meat"]).lower()}, {str(fd["always"]).lower()});')
    else:
        il.append(f'    public static final RegistryObject<Item> {const(it["id"])} = simple("{it["id"]}", Tab.{it["tab"]}, {it["stack"]});')

def fill(path, marker, body):
    s = open(path).read()
    start, end = f"// <{marker}>", f"// </{marker}>"
    a, b = s.index(start) + len(start), s.index(end)
    s = s[:a] + "\n" + "\n".join(body) + "\n    " + s[b:]
    open(path, "w").write(s)

fill(os.path.join(JAVA, "registry/ModBlocks.java"), "generated", bl)
fill(os.path.join(JAVA, "registry/ModItems.java"), "generated", il)

print(f"blocks ported: {len(blocks)}, items ported: {len(items)}")
print("skipped (need custom code later):", len(skipped))
open(os.path.join(REPO, "PORTING-skipped.txt"), "w").write("\n".join(sorted(skipped)) + "\n")
