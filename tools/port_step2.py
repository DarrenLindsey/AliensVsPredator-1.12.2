"""Step 2 of the AvP port: stairs, slabs, special ores and recipes (NeoForge 1.21.1).

Run from the repo root:  python tools/port_step2.py
Writes JSON data/asset files only; the Java side lives in ModBlocks.java.
"""
import glob, json, os, re, shutil

LA = "legacy-1.12.2/src/main/resources/assets/avp"
NA = "src/main/resources/assets/avp"
ND = "src/main/resources/data"


def write_json(path, obj):
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, "w") as f:
        json.dump(obj, f, indent=2)
        f.write("\n")


def read_json(path, default):
    return json.load(open(path)) if os.path.exists(path) else default


def add_to_tag(path, values):
    tag = read_json(path, {"replace": False, "values": []})
    for v in values:
        if v not in tag["values"]:
            tag["values"].append(v)
    write_json(path, tag)


lang = read_json(f"{NA}/lang/en_us.json", {})
old_lang = {}
for line in open(f"{LA}/lang/en_US.lang", encoding="utf-8", errors="replace"):
    m = re.match(r"tile\.avp:(.+?)\.name=(.*)", line.strip())
    if m:
        old_lang[m.group(1)] = m.group(2).strip()

TRANSLUCENT = {"floorgrill", "ceilinggrill", "industrialglass"}

# ---------------------------------------------------------------- stairs & slabs
SHAPES = {  # new block id -> (kind, base block id)
    "floorgrillstairs": ("stairs", "floorgrill"),
    "ceilinggrillstairs": ("stairs", "ceilinggrill"),
    "industrialbrickstairs": ("stairs", "industrialbricks"),
    "industrialwallstairs": ("stairs", "industrialwall"),
    "industrialglassstairs": ("stairs", "industrialglass"),
    "floorgrillslab": ("slab", "floorgrill"),
    "ceilinggrillslab": ("slab", "ceilinggrill"),
    "industrialslab": ("slab", "industrialwall"),
    "industrialbrickslab": ("slab", "industrialbricks"),
    "industrialglassslab": ("slab", "industrialglass"),
}


def model(path, parent, tex, base):
    m = {"parent": parent, "textures": {"bottom": tex, "top": tex, "side": tex}}
    if base in TRANSLUCENT:
        m["render_type"] = "minecraft:translucent"
    write_json(path, m)


for i, (kind, base) in SHAPES.items():
    tex = f"avp:block/{base}"
    b = f"avp:block/{i}"
    if kind == "stairs":
        model(f"{NA}/models/block/{i}.json", "minecraft:block/stairs", tex, base)
        model(f"{NA}/models/block/{i}_inner.json", "minecraft:block/inner_stairs", tex, base)
        model(f"{NA}/models/block/{i}_outer.json", "minecraft:block/outer_stairs", tex, base)
        variants = {}
        # same rotation table vanilla uses for all stairs
        rot = {"east": 0, "south": 90, "west": 180, "north": 270}
        for facing, y0 in rot.items():
            for half in ("bottom", "top"):
                for shape in ("straight", "inner_left", "inner_right", "outer_left", "outer_right"):
                    mdl = b + ("" if shape == "straight" else "_" + shape.split("_")[0])
                    y = y0
                    if shape in ("inner_left", "outer_left") and half == "bottom":
                        y = y0 + 270
                    elif shape in ("inner_right", "outer_right") and half == "top":
                        y = y0 + 90
                    y %= 360
                    v = {"model": mdl}
                    if half == "top":
                        v["x"] = 180
                    if y:
                        v["y"] = y
                    if half == "top" or y:
                        v["uvlock"] = True
                    variants[f"facing={facing},half={half},shape={shape}"] = v
        write_json(f"{NA}/blockstates/{i}.json", {"variants": variants})
        loot_entry = {"type": "minecraft:item", "name": f"avp:{i}"}
    else:
        model(f"{NA}/models/block/{i}.json", "minecraft:block/slab", tex, base)
        model(f"{NA}/models/block/{i}_top.json", "minecraft:block/slab_top", tex, base)
        write_json(f"{NA}/blockstates/{i}.json", {"variants": {
            "type=bottom": {"model": b},
            "type=top": {"model": b + "_top"},
            "type=double": {"model": f"avp:block/{base}"},
        }})
        # a double slab drops 2
        loot_entry = {"type": "minecraft:item", "name": f"avp:{i}", "functions": [
            {"function": "minecraft:set_count", "count": 2, "add": False,
             "conditions": [{"condition": "minecraft:block_state_property", "block": f"avp:{i}",
                             "properties": {"type": "double"}}]},
            {"function": "minecraft:explosion_decay"}]}
    write_json(f"{NA}/models/item/{i}.json", {"parent": b})
    write_json(f"{ND}/avp/loot_table/blocks/{i}.json", {
        "type": "minecraft:block",
        "pools": [{"rolls": 1, "entries": [loot_entry],
                   "conditions": [{"condition": "minecraft:survives_explosion"}] if kind == "stairs" else []}]})
    lang[f"block.avp.{i}"] = old_lang.get(i, i)
add_to_tag(f"{ND}/minecraft/tags/block/stairs.json", [f"avp:{i}" for i, (k, _) in SHAPES.items() if k == "stairs"])
add_to_tag(f"{ND}/minecraft/tags/item/stairs.json", [f"avp:{i}" for i, (k, _) in SHAPES.items() if k == "stairs"])
add_to_tag(f"{ND}/minecraft/tags/block/slabs.json", [f"avp:{i}" for i, (k, _) in SHAPES.items() if k == "slab"])
add_to_tag(f"{ND}/minecraft/tags/item/slabs.json", [f"avp:{i}" for i, (k, _) in SHAPES.items() if k == "slab"])
add_to_tag(f"{ND}/minecraft/tags/block/mineable/pickaxe.json", [f"avp:{i}" for i in SHAPES])

# ---------------------------------------------------------------- ores with drops
ORES = {  # ore id -> (dropped item, min, max, fortune formula, english name)
    "oresilicon": ("silicon", 1, 3, "uniform", "Silicon Ore"),
    "oremonazite": ("neodymium", 1, 2, "uniform", "Monazite Ore"),
    "orecobalt": ("cobalt", 1, 1, "uniform", "Cobalt Ore"),
}
for i, (drop, lo, hi, _, name) in ORES.items():
    shutil.copy(f"{LA}/textures/blocks/{i}.png", f"{NA}/textures/block/{i}.png")
    write_json(f"{NA}/blockstates/{i}.json", {"variants": {"": {"model": f"avp:block/{i}"}}})
    write_json(f"{NA}/models/block/{i}.json", {"parent": "minecraft:block/cube_all", "textures": {"all": f"avp:block/{i}"}})
    write_json(f"{NA}/models/item/{i}.json", {"parent": f"avp:block/{i}"})
    count = lo if lo == hi else {"type": "minecraft:uniform", "min": lo, "max": hi}
    write_json(f"{ND}/avp/loot_table/blocks/{i}.json", {
        "type": "minecraft:block",
        "pools": [{"rolls": 1, "entries": [{"type": "minecraft:alternatives", "children": [
            {   # Silk Touch: drop the ore block itself
                "type": "minecraft:item", "name": f"avp:{i}",
                "conditions": [{"condition": "minecraft:match_tool", "predicate": {"predicates": {
                    "minecraft:enchantments": [{"enchantments": "minecraft:silk_touch", "levels": {"min": 1}}]}}}]},
            {   # otherwise: drop the item, +0..Fortune extra
                "type": "minecraft:item", "name": f"avp:{drop}",
                "functions": [
                    {"function": "minecraft:set_count", "count": count},
                    {"function": "minecraft:apply_bonus", "enchantment": "minecraft:fortune",
                     "formula": "minecraft:uniform_bonus_count", "parameters": {"bonusMultiplier": 1}},
                    {"function": "minecraft:explosion_decay"}]},
        ]}]}]})
    lang[f"block.avp.{i}"] = name
add_to_tag(f"{ND}/minecraft/tags/block/mineable/pickaxe.json", [f"avp:{i}" for i in ORES])
# the two very hard ores need at least an iron pickaxe (like diamond ore)
add_to_tag(f"{ND}/minecraft/tags/block/needs_iron_tool.json", ["avp:oremonazite", "avp:orecobalt"])

# ---------------------------------------------------------------- shared "c:" tags
# 1.12.2 used the Forge "ore dictionary"; 1.21 uses common tags so other mods' metals work too.
add_to_tag(f"{ND}/c/tags/item/ingots/aluminum.json", ["avp:ingot_aluminum"])
add_to_tag(f"{ND}/c/tags/item/ingots/copper.json", ["avp:ingot_copper"])
add_to_tag(f"{ND}/c/tags/item/ingots.json", ["#c:ingots/aluminum"])
add_to_tag(f"{ND}/c/tags/block/ores.json", [f"avp:{i}" for i in ORES] + ["avp:orecopper", "avp:orebauxite", "avp:orelithium"])
add_to_tag(f"{ND}/c/tags/item/ores.json", [f"avp:{i}" for i in ORES] + ["avp:orecopper", "avp:orebauxite", "avp:orelithium"])

write_json(f"{NA}/lang/en_us.json", lang)

# ---------------------------------------------------------------- recipes
java = open("src/main/java/org/avp/registry/ModBlocks.java").read() + open("src/main/java/org/avp/registry/ModItems.java").read()
AVP = set(re.findall(r'(?:simple|food|stairs|slab|ore)\("([a-z0-9_]+)"', java))

ORE_DICT = {
    "ingotAluminum": {"tag": "c:ingots/aluminum"},
    "ingotCopper": {"tag": "c:ingots/copper"},
    "ingotIron": {"tag": "c:ingots/iron"},
    "IngotIron": {"tag": "c:ingots/iron"},
    "polycarbonate": {"item": "avp:polycarbonate"},
    "silicon": {"item": "avp:silicon"},
    "carbon": {"item": "avp:carbon"},
    "cobalt": {"item": "avp:cobalt"},
    "neodymium": {"item": "avp:neodymium"},
    "obsidian": {"tag": "c:obsidians"},
    "slimeball": {"tag": "c:slime_balls"},
    # "lithium" is left out on purpose: the lithium ingot isn't ported yet
}
VANILLA = {  # (old id, old metadata) -> 1.21 ingredient; metadata None/0 = default
    ("minecraft:coal", 1): {"item": "minecraft:charcoal"},
    ("minecraft:planks", 0): {"tag": "minecraft:planks"},
    ("minecraft:wooden_door", 0): {"item": "minecraft:oak_door"},
    ("minecraft:wool", 0): {"item": "minecraft:white_wool"},
    ("minecraft:wool", 1): {"item": "minecraft:orange_wool"},
}


class Missing(Exception):
    pass


def avp_id(old):
    new = re.sub(r"[^a-z0-9_]", "_", old.split(":")[-1].lower())
    if new not in AVP:
        raise Missing(old)
    return "avp:" + new


def ingredient(x):
    if isinstance(x, list):
        if len(x) != 1:
            return [ingredient(y) for y in x]
        x = x[0]
    if "ore" in x:
        if x["ore"] not in ORE_DICT:
            raise Missing("ore:" + x["ore"])
        return ORE_DICT[x["ore"]]
    item, data = x["item"], x.get("data") or 0
    if not item.startswith("minecraft:"):
        return {"item": avp_id(item)}
    item = item.lower()
    return VANILLA.get((item, data), {"item": item})


done, skipped = [], {}
recipe_dir = f"{ND}/avp/recipe"
for f in sorted(glob.glob(f"{LA}/recipes/*.json")):
    name = re.sub(r"[^a-z0-9_]", "_", os.path.basename(f)[:-5].lower())
    old = json.load(open(f))
    try:
        result = {"id": avp_id(old["result"]["item"])}
        if old["result"].get("count", 1) > 1:
            result["count"] = old["result"]["count"]
        new = {"type": old["type"], "category": "building" if result["id"][4:] in AVP and "blocks" in java else "misc"}
        if old["type"] == "minecraft:crafting_shaped":
            new["pattern"] = old["pattern"]
            used = set("".join(old["pattern"])) - {" "}
            new["key"] = {k: ingredient(v) for k, v in old["key"].items() if k in used}
        else:
            new["ingredients"] = [ingredient(x) for x in old["ingredients"]]
        new["result"] = result
        write_json(f"{recipe_dir}/{name}.json", new)
        done.append(name)
    except Missing as e:
        skipped[name] = str(e)

# smelting (was Java code in CraftingHandler.addSmelting)
SMELTING = [
    ("orecopper", "ingot_copper", 1.0),
    ("orebauxite", "ingot_aluminum", 1.0),
    ("oresilicon", "silicon", 1.0),
    ("food_raw_tentacle", "food_trilo_bite", 2.0),
]
for src, out, xp in SMELTING:
    for kind, time in (("smelting", 200), ("blasting", 100)) if src.startswith("ore") else (("smelting", 200), ("smoking", 100), ("campfire_cooking", 600)):
        write_json(f"{recipe_dir}/{out}_from_{kind}_{src}.json", {
            "type": f"minecraft:{kind}", "category": "misc" if src.startswith("ore") else "food",
            "ingredient": {"item": f"avp:{src}"}, "result": {"id": f"avp:{out}"},
            "experience": xp, "cookingtime": time})

# blocks fixed categories: anything whose result is a block goes in the "building" recipe-book tab
blocks = set(re.findall(r'(?:simple|stairs|slab|ore)\("([a-z0-9_]+)"', open("src/main/java/org/avp/registry/ModBlocks.java").read()))
for name in done:
    p = f"{recipe_dir}/{name}.json"
    r = json.load(open(p))
    r["category"] = "building" if r["result"]["id"][4:] in blocks else "misc"
    write_json(p, r)

print(f"recipes ported: {len(done)} crafting + {sum(1 for _ in glob.glob(recipe_dir + '/*_from_*'))} cooking")
print(f"recipes skipped: {len(skipped)}")
with open("PORTING-skipped-recipes.txt", "w") as f:
    f.write("# Old recipes not ported yet, and the first missing ingredient/result\n")
    for k, v in sorted(skipped.items()):
        f.write(f"{k}: needs {v}\n")
