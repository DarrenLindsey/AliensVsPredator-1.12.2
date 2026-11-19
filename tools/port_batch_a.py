"""Batch A of the AvP port: natural blocks (dirt, grass, logs, leaves, plants).

Run from the repo root:  python tools/port_batch_a.py
Writes textures, models, blockstates, loot tables, tags and names.
The Java side is in ModBlocks.java ("Natural blocks" section) and org/avp/block/.
"""
import json, os, re, shutil

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


def tex(old_name, new_name=None):
    """Copy an old blocks/ texture to block/ and return its new resource name."""
    new_name = new_name or old_name.replace(".", "_")
    os.makedirs(f"{NA}/textures/block", exist_ok=True)
    shutil.copy(f"{LA}/textures/blocks/{old_name}.png", f"{NA}/textures/block/{new_name}.png")
    if os.path.exists(f"{LA}/textures/blocks/{old_name}.png.mcmeta"):
        shutil.copy(f"{LA}/textures/blocks/{old_name}.png.mcmeta", f"{NA}/textures/block/{new_name}.png.mcmeta")
    return f"avp:block/{new_name}"


def single_state(i, model=None):
    write_json(f"{NA}/blockstates/{i}.json", {"variants": {"": {"model": model or f"avp:block/{i}"}}})


def block_item(i):
    write_json(f"{NA}/models/item/{i}.json", {"parent": f"avp:block/{i}"})


def flat_item(i, texture):
    write_json(f"{NA}/models/item/{i}.json", {"parent": "minecraft:item/generated", "textures": {"layer0": texture}})


def drops_self(i):
    write_json(f"{ND}/avp/loot_table/blocks/{i}.json", {"type": "minecraft:block", "pools": [{
        "rolls": 1, "entries": [{"type": "minecraft:item", "name": f"avp:{i}"}],
        "conditions": [{"condition": "minecraft:survives_explosion"}]}]})


SHEARS_OR_SILK = {"condition": "minecraft:any_of", "terms": [
    {"condition": "minecraft:match_tool", "predicate": {"items": "minecraft:shears"}},
    {"condition": "minecraft:match_tool", "predicate": {"predicates": {
        "minecraft:enchantments": [{"enchantments": "minecraft:silk_touch", "levels": {"min": 1}}]}}},
]}

lang = read_json(f"{NA}/lang/en_us.json", {})
old_lang = {}
for line in open(f"{LA}/lang/en_US.lang", encoding="utf-8", errors="replace"):
    m = re.match(r"tile\.avp:(.+?)\.name=(.*)", line.strip())
    if m:
        old_lang[m.group(1)] = m.group(2).strip()


# Clearer names than the 1.12.2 ones ("Unitree Big Leaves" was really the bottom piece)
BETTER_NAMES = {
    "unidirt": "Unidentified Dirt",
    "unitree_leaves": "Unidentified Leaves (Top)",
    "unitree_leaves_m": "Unidentified Leaves (Middle)",
    "unitree_leaves_b": "Unidentified Leaves (Bottom)",
    "paradise_leaves_large": "Large Paradise Leaves",
    "paradise_leaves_med": "Medium Paradise Leaves",
    "paradise_leaves_small": "Small Paradise Leaves",
    "paradise_log_large": "Large Paradise Log",
    "paradise_log_med": "Medium Paradise Log",
    "paradise_log_small": "Small Paradise Log",
    "paradise_dirt_mossy": "Mossy Paradise Dirt",
    "paradise_dirt_podzol": "Paradise Podzol"
}


def name(i, old):
    lang[f"block.avp.{i}"] = BETTER_NAMES.get(i) or old_lang.get(old, old)


# ---------------------------------------------------------------- dirt & grass
for i in ("unidirt", "paradise_dirt"):
    t = tex(i)
    write_json(f"{NA}/models/block/{i}.json", {"parent": "minecraft:block/cube_all", "textures": {"all": t}})
for i, top in (("paradise_dirt_mossy", "paradise_dirt_mossy"), ("paradise_dirt_podzol", "paradise_dirt_podzol_top"),
               ("paradise_grass", "paradise_grass")):
    side = tex("paradise_grass_side") if i == "paradise_grass" else tex("paradise_dirt_podzol_side")
    write_json(f"{NA}/models/block/{i}.json", {"parent": "minecraft:block/cube_bottom_top", "textures": {
        "top": tex(top), "bottom": "avp:block/paradise_dirt", "side": side}})
DIRT = ["unidirt", "paradise_dirt", "paradise_dirt_mossy", "paradise_dirt_podzol", "paradise_grass"]
for i in DIRT:
    single_state(i)
    block_item(i)
    drops_self(i)
    name(i, i)

# ---------------------------------------------------------------- logs
LOGS = {
    "unitree_wood": ("unitree.wood", "unitree_log_top", "unitree_log_side"),
    "paradise_log_large": ("paradise_log_large", "paradise_large_log_top", "paradise_large_log_side"),
    "paradise_log_med": ("paradise_log_med", "paradise_medium_log_top", "paradise_medium_log_side"),
    "paradise_log_small": ("paradise_log_small", "paradise_small_log_top", "paradise_small_log_side"),
}
for i, (old, top, side) in LOGS.items():
    textures = {"end": tex(top), "side": tex(side)}
    write_json(f"{NA}/models/block/{i}.json", {"parent": "minecraft:block/cube_column", "textures": textures})
    write_json(f"{NA}/models/block/{i}_horizontal.json", {"parent": "minecraft:block/cube_column_horizontal", "textures": textures})
    write_json(f"{NA}/blockstates/{i}.json", {"variants": {
        "axis=y": {"model": f"avp:block/{i}"},
        "axis=z": {"model": f"avp:block/{i}_horizontal", "x": 90},
        "axis=x": {"model": f"avp:block/{i}_horizontal", "x": 90, "y": 90},
    }})
    block_item(i)
    drops_self(i)
    name(i, old)

# ---------------------------------------------------------------- leaves
def leaves_loot(i):
    write_json(f"{ND}/avp/loot_table/blocks/{i}.json", {"type": "minecraft:block", "pools": [
        {"rolls": 1, "entries": [{"type": "minecraft:alternatives", "children": [
            {"type": "minecraft:item", "name": f"avp:{i}", "conditions": [SHEARS_OR_SILK]},
            {"type": "minecraft:item", "name": "avp:unitree_sapling", "conditions": [
                {"condition": "minecraft:survives_explosion"},
                {"condition": "minecraft:table_bonus", "enchantment": "minecraft:fortune",
                 "chances": [0.05, 0.0625, 0.083333336, 0.1]}]},
        ]}]}]})


PARADISE_LEAVES = {"paradise_leaves_large": "paradise_large_leaves", "paradise_leaves_med": "paradise_medium_leaves",
                   "paradise_leaves_small": "paradise_small_leaves"}
for i, t in PARADISE_LEAVES.items():
    write_json(f"{NA}/models/block/{i}.json", {"parent": "minecraft:block/cube_all", "textures": {"all": tex(t)},
                                               "render_type": "minecraft:cutout_mipped"})
    single_state(i)
    block_item(i)
    leaves_loot(i)
    name(i, i)

UNITREE_LEAVES = {"unitree_leaves": ("unitree.leaves", "unitree_leaves_top"),
                  "unitree_leaves_m": ("unitree.leaves.m", "unitree_leaves_mid"),
                  "unitree_leaves_b": ("unitree.leaves.b", "unitree_leaves_bot")}
for t in ("unitree_leaves_1_side", "unitree_leaves_1_top", "unitree_leaves_2", "unitree_leaves_3"):
    tex(t)
for i, (old, old_model) in UNITREE_LEAVES.items():
    m = json.load(open(f"{LA}/models/block/{old_model}.json"))
    m["parent"] = "minecraft:block/block"
    m["textures"] = {k: v.replace("avp:blocks/", "avp:block/") for k, v in m["textures"].items()}
    m["render_type"] = "minecraft:cutout_mipped"
    write_json(f"{NA}/models/block/{i}.json", m)
    single_state(i)
    block_item(i)
    leaves_loot(i)
    name(i, old)

# ---------------------------------------------------------------- plants & tendons
# tendons: six random looks, like vanilla's random grass rotations
tendon_models = []
for n in range(1, 7):
    t = tex(f"unitree_tendons_{n}")
    write_json(f"{NA}/models/block/unitree_tendons_{n}.json", {"parent": "minecraft:block/cross", "textures": {"cross": t},
                                                               "render_type": "minecraft:translucent"})
    tendon_models.append({"model": f"avp:block/unitree_tendons_{n}"})
write_json(f"{NA}/blockstates/unitree_tendons.json", {"variants": {"": tendon_models}})
flat_item("unitree_tendons", "avp:block/unitree_tendons_1")
write_json(f"{ND}/avp/loot_table/blocks/unitree_tendons.json", {"type": "minecraft:block", "pools": [
    {"rolls": 1, "entries": [{"type": "minecraft:alternatives", "children": [
        {"type": "minecraft:item", "name": "avp:unitree_tendons", "conditions": [SHEARS_OR_SILK]},
        {"type": "minecraft:item", "name": "minecraft:stick", "functions": [
            {"function": "minecraft:set_count", "count": {"type": "minecraft:uniform", "min": 2, "max": 3}},
            {"function": "minecraft:explosion_decay"}]},
    ]}]}]})
name("unitree_tendons", "unitree.tendons")

for i, old in (("unitree_sapling", "unitree.sapling"), ("stalagmite", "stalagmite")):
    t = tex(old, i)
    write_json(f"{NA}/models/block/{i}.json", {"parent": "minecraft:block/cross", "textures": {"cross": t},
                                               "render_type": "minecraft:cutout"})
    single_state(i)
    flat_item(i, t)
    drops_self(i)
    name(i, old)

# wheat grass keeps its original 3D (OBJ) model through NeoForge's OBJ loader
t = tex("paradisian_wheat_grass")
os.makedirs(f"{NA}/models/block", exist_ok=True)
shutil.copy(f"{LA}/models/block/paradisian_wheat_grass.obj", f"{NA}/models/block/paradisian_wheat_grass.obj")
mtl = open(f"{LA}/models/block/paradisian_wheat_grass.mtl").read().replace("avp:blocks/", "avp:block/")
open(f"{NA}/models/block/paradisian_wheat_grass.mtl", "w").write(mtl.rstrip("\n") + "\n")
write_json(f"{NA}/models/block/paradisian_wheat_grass.json", {
    "loader": "neoforge:obj", "model": "avp:models/block/paradisian_wheat_grass.obj",
    "flip_v": True, "render_type": "minecraft:cutout_mipped", "textures": {"particle": t}})
single_state("paradisian_wheat_grass")
flat_item("paradisian_wheat_grass", t)
drops_self("paradisian_wheat_grass")
name("paradisian_wheat_grass", "paradisian_wheat_grass")

# ---------------------------------------------------------------- tags
A = lambda ids: [f"avp:{i}" for i in ids]
add_to_tag(f"{ND}/minecraft/tags/block/dirt.json", A(DIRT))
add_to_tag(f"{ND}/minecraft/tags/item/dirt.json", A(DIRT))
add_to_tag(f"{ND}/minecraft/tags/block/mineable/shovel.json", A(DIRT))
for kind in ("block", "item"):
    add_to_tag(f"{ND}/minecraft/tags/{kind}/logs_that_burn.json", A(LOGS))
    add_to_tag(f"{ND}/minecraft/tags/{kind}/leaves.json", A(list(PARADISE_LEAVES) + list(UNITREE_LEAVES)))
    add_to_tag(f"{ND}/minecraft/tags/{kind}/saplings.json", ["avp:unitree_sapling"])
# logs_that_burn is part of minecraft:logs, which is what keeps leaves from decaying.
# The old tendons could also hold leaves up, so they count as logs for that check:
add_to_tag(f"{ND}/minecraft/tags/block/logs.json", ["avp:unitree_tendons"])
add_to_tag(f"{ND}/minecraft/tags/block/mineable/axe.json", A(LOGS))
add_to_tag(f"{ND}/minecraft/tags/block/mineable/hoe.json", A(list(PARADISE_LEAVES) + list(UNITREE_LEAVES)))
add_to_tag(f"{ND}/minecraft/tags/block/sword_efficient.json", ["avp:unitree_tendons", "avp:paradisian_wheat_grass"])
add_to_tag(f"{ND}/minecraft/tags/block/mineable/pickaxe.json", ["avp:stalagmite"])

# unidentified wood smelts into charcoal (old CraftingHandler.addSmelting)
write_json(f"{ND}/avp/recipe/charcoal_from_smelting_unitree_wood.json", {
    "type": "minecraft:smelting", "category": "misc", "ingredient": {"item": "avp:unitree_wood"},
    "result": {"id": "minecraft:charcoal"}, "experience": 0.15, "cookingtime": 200})

write_json(f"{NA}/lang/en_us.json", lang)
print("batch A data written")
