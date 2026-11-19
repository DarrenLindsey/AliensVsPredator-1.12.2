"""Batch B of the AvP port: decorative 3D blocks (skulls, biomasks, heads, ferns,
slopes/corners/ridges/pyramids, striped wall, skull pile).

Run from the repo root:  python tools/port_batch_b.py
The Java side is in ModBlocks.java ("Decorative blocks" section), org/avp/block/
and org/avp/client/.
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


def drops_self(i):
    write_json(f"{ND}/avp/loot_table/blocks/{i}.json", {"type": "minecraft:block", "pools": [{
        "rolls": 1, "entries": [{"type": "minecraft:item", "name": f"avp:{i}"}],
        "conditions": [{"condition": "minecraft:survives_explosion"}]}]})


lang = read_json(f"{NA}/lang/en_us.json", {})

# ---------------------------------------------------------------- textures used by models
# creature ("mob") and machine ("tile") textures, used by skulls now and by mobs/machines later
for d in ("mob", "tile"):
    if os.path.exists(f"{NA}/textures/{d}"):
        shutil.rmtree(f"{NA}/textures/{d}")
    shutil.copytree(f"{LA}/textures/{d}", f"{NA}/textures/{d}")

# ---------------------------------------------------------------- simple cubes
for i, name in (("industrialwall2", "Striped Industrial Wall"), ("skulls", "Skull Pile")):
    shutil.copy(f"{LA}/textures/blocks/{i}.png", f"{NA}/textures/block/{i}.png")
    write_json(f"{NA}/models/block/{i}.json", {"parent": "minecraft:block/cube_all", "textures": {"all": f"avp:block/{i}"}})
    write_json(f"{NA}/blockstates/{i}.json", {"variants": {"": {"model": f"avp:block/{i}"}}})
    write_json(f"{NA}/models/item/{i}.json", {"parent": f"avp:block/{i}"})
    drops_self(i)
    lang[f"block.avp.{i}"] = name
add_to_tag(f"{ND}/minecraft/tags/block/mineable/pickaxe.json", ["avp:industrialwall2", "avp:skulls"])

# ---------------------------------------------------------------- trophies & ferns (drawn by ModelBlockRenderer)
TROPHIES = {
    "skull_engineer": "Engineer Helmet",
    "skull_spacejockey": "Space Jockey Skull",
    "skull_xenomorph": "Xenomorph Skull (Gigeresque)",
    "skull_xenomorph_warrior": "Xenomorph Skull",
    "skull_yautja": "Yautja Skull",
    "skull_matriarch": "Matriarch Skull",
    "skull_protomorph": "Protomorph Skull",
    "skull_neomorph": "Neomorph Skull",
    "biomask_classic": "Classic Biomask",
    "biomask_berserker": "Berserker Biomask",
    "biomask_falconer": "Falconer Biomask",
    "biomask_tracker": "Tracker Biomask",
    "head_aethon": "Aethon Head",
    "head_gigeralien": "Giger Alien Head",
}
FERNS = {"paradisian_ground_fern": "Paradisian Ground Fern", "paradisian_tree_fern": "Paradisian Tree Fern"}

BLOCK_DISPLAY = {  # same as minecraft:block/block, so the items look like normal blocks
    "gui": {"rotation": [30, 225, 0], "translation": [0, 0, 0], "scale": [0.625, 0.625, 0.625]},
    "ground": {"rotation": [0, 0, 0], "translation": [0, 3, 0], "scale": [0.25, 0.25, 0.25]},
    "fixed": {"rotation": [0, 0, 0], "translation": [0, 0, 0], "scale": [0.5, 0.5, 0.5]},
    "thirdperson_righthand": {"rotation": [75, 45, 0], "translation": [0, 2.5, 0], "scale": [0.375, 0.375, 0.375]},
    "firstperson_righthand": {"rotation": [0, 45, 0], "translation": [0, 0, 0], "scale": [0.40, 0.40, 0.40]},
    "firstperson_lefthand": {"rotation": [0, 225, 0], "translation": [0, 0, 0], "scale": [0.40, 0.40, 0.40]},
}


# Items: drawn by ModelBlockItemRenderer ("builtin/entity"), shrunk to fit a block and shown like a block.
def model_item(i, particle):
    write_json(f"{NA}/models/item/{i}.json", {"parent": "minecraft:builtin/entity", "display": BLOCK_DISPLAY,
                                              "textures": {"particle": particle}})


# The block model is never drawn (the renderer draws it); it only supplies the break-particle texture.
write_json(f"{NA}/models/block/trophy.json", {"textures": {"particle": "minecraft:block/bone_block_side"}})
write_json(f"{NA}/models/block/fern_plant.json", {"textures": {"particle": "avp:block/paradise_grass"}})
for i, name in TROPHIES.items():
    write_json(f"{NA}/blockstates/{i}.json", {"variants": {"": {"model": "avp:block/trophy"}}})
    model_item(i, "minecraft:block/bone_block_side")
    drops_self(i)
    lang[f"block.avp.{i}"] = name
for i, name in FERNS.items():
    write_json(f"{NA}/blockstates/{i}.json", {"variants": {"": {"model": "avp:block/fern_plant"}}})
    model_item(i, "avp:block/paradise_grass")
    drops_self(i)
    lang[f"block.avp.{i}"] = name
add_to_tag(f"{ND}/minecraft/tags/block/mineable/pickaxe.json", [f"avp:{i}" for i in TROPHIES])
add_to_tag(f"{ND}/minecraft/tags/block/sword_efficient.json", [f"avp:{i}" for i in FERNS])

# ---------------------------------------------------------------- slopes etc. (OBJ models + MimicBakedModel)
SHAPES = {
    "slope": "Slope", "corner": "Corner", "invertedcorner": "Inverted Corner", "ridge": "Ridge",
    "pyramid": "Pyramid", "invertedridge": "Inverted Ridge", "invertedpyramid": "Inverted Pyramid",
}
shutil.copy(f"{LA}/textures/blocks/reflection.png", f"{NA}/textures/block/reflection.png")
mtl = open(f"{LA}/models/block/reflection.mtl").read().replace("avp:blocks/", "avp:block/")
open(f"{NA}/models/block/reflection.mtl", "w").write(mtl.rstrip("\n") + "\n")

Y = {"north": 0, "east": 90, "south": 180, "west": 270}
OPPOSITE = {"north": "south", "south": "north", "east": "west", "west": "east"}
X = {"bottom": 0, "top": 180, "side": 270}
for i, name in SHAPES.items():
    shutil.copy(f"{LA}/models/block/{i}.obj", f"{NA}/models/block/{i}.obj")
    write_json(f"{NA}/models/block/{i}.json", {
        "loader": "neoforge:obj", "model": f"avp:models/block/{i}.obj", "flip_v": True,
        "render_type": "minecraft:cutout_mipped", "textures": {"particle": "avp:block/reflection"}})
    variants = {}
    for facing in Y:
        for align in X:
            # rotation rules from the 1.12.2 CachedModelReflectiveShape.rotation()
            model_facing = facing if align == "top" else OPPOSITE[facing]
            v = {"model": f"avp:block/{i}"}
            if X[align]:
                v["x"] = X[align]
            if Y[model_facing]:
                v["y"] = Y[model_facing]
            variants[f"alignment={align},facing={facing}"] = v
    write_json(f"{NA}/blockstates/{i}.json", {"variants": variants})
    write_json(f"{NA}/models/item/{i}.json", {"parent": f"avp:block/{i}", "display": BLOCK_DISPLAY})
    drops_self(i)
    lang[f"block.avp.{i}"] = name
add_to_tag(f"{ND}/minecraft/tags/block/mineable/pickaxe.json", [f"avp:{i}" for i in SHAPES])

write_json(f"{NA}/lang/en_us.json", lang)
print("batch B data written")
