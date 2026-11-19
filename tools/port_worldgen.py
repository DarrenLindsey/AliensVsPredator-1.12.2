"""Ore generation (step 2 of the plan: "blocks, then ore").

Run from the repo root:  python tools/port_worldgen.py
Writes, for each ore: a deepslate version (model, loot, tags), the configured + placed
feature (data/avp/worldgen/...) and a NeoForge biome modifier that adds it to biomes,
switched on/off by avp-common.toml (condition "avp:ore_enabled", see ConfigCondition).

1.12.2 (WorldGenerator.generateSurface) -> 1.21: same biomes and rarity in spirit; vein sizes and
heights adjusted for the taller 1.18+ world (y -64..320).
"""
import json, os, re, glob
from PIL import Image

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


# name: (ore block, vein size, veins per chunk, min y, max y, biomes, display name)
ORES = {
    "bauxite": ("orebauxite", 7, 12, 16, 128, "#minecraft:is_overworld", "Bauxite Ore"),
    "copper": ("orecopper", 7, 10, -16, 112, "#minecraft:is_overworld", "Copper Ore"),
    "silicon": ("oresilicon", 5, 4, -48, 64, "#minecraft:is_overworld", "Silicon Ore"),
    "monazite": ("oremonazite", 4, 3, -64, 32, "#minecraft:is_overworld", "Monazite Ore"),
    "lithium": ("orelithium", 10, 2, -32, 48, "#avp:has_ore/lithium", "Lithium Ore"),
    "cobalt": ("orecobalt", 6, 2, -48, 64, "#minecraft:is_jungle", "Cobalt Ore"),
}

# a biome list can't mix several tags, so lithium's biomes are gathered into one biome tag
write_json(f"{ND}/avp/tags/worldgen/biome/has_ore/lithium.json",
           {"replace": False, "values": ["#minecraft:is_ocean", "#minecraft:is_beach", "#minecraft:is_river"]})

lang = read_json(f"{NA}/lang/en_us.json", {})
tag_files = glob.glob(f"{ND}/minecraft/tags/block/**/*.json", recursive=True) + glob.glob(f"{ND}/c/tags/**/*.json", recursive=True)

for name, (ore, size, count, lo, hi, biomes, display) in ORES.items():
    deep = f"deepslate_{ore}"

    # -- texture: the ore's coloured specks on vanilla deepslate (an overlay, drawn as a second layer)
    src = Image.open(f"{NA}/textures/block/{ore}.png").convert("RGBA").crop((0, 0, 16, 16))
    overlay = Image.new("RGBA", (16, 16), (0, 0, 0, 0))
    for x in range(16):
        for y in range(16):
            r, g, b, a = src.getpixel((x, y))
            lum = (r + g + b) / 3
            if max(r, g, b) - min(r, g, b) > 12 or lum < 45 or lum > 215:  # not plain grey stone
                overlay.putpixel((x, y), (r, g, b, a))
    overlay.save(f"{NA}/textures/block/{ore}_overlay.png")
    write_json(f"{NA}/models/block/{deep}.json", {
        "parent": "minecraft:block/block", "render_type": "minecraft:cutout",
        "textures": {"particle": "minecraft:block/deepslate", "base": "minecraft:block/deepslate", "ore": f"avp:block/{ore}_overlay"},
        "elements": [
            {"from": [0, 0, 0], "to": [16, 16, 16],
             "faces": {d: {"texture": "#base", "cullface": d} for d in ("north", "south", "east", "west", "up", "down")}},
            {"from": [0, 0, 0], "to": [16, 16, 16],
             "faces": {d: {"texture": "#ore", "cullface": d} for d in ("north", "south", "east", "west", "up", "down")}}]})
    write_json(f"{NA}/blockstates/{deep}.json", {"variants": {"": {"model": f"avp:block/{deep}"}}})
    write_json(f"{NA}/models/item/{deep}.json", {"parent": f"avp:block/{deep}"})
    lang[f"block.avp.{deep}"] = f"Deepslate {display}"

    # -- loot: same drops as the stone version (silk touch gives the deepslate block)
    loot = open(f"{ND}/avp/loot_table/blocks/{ore}.json").read().replace(f'"avp:{ore}"', f'"avp:{deep}"')
    os.makedirs(f"{ND}/avp/loot_table/blocks", exist_ok=True)
    open(f"{ND}/avp/loot_table/blocks/{deep}.json", "w").write(loot)

    # -- tags: wherever the stone ore is (pickaxe, tool tier, c:ores), add the deepslate one too
    for path in tag_files:
        tag = json.load(open(path))
        if f"avp:{ore}" in tag.get("values", []):
            add_to_tag(path, [f"avp:{deep}"])

    # -- the vein (configured feature), where it goes (placed feature) and which biomes (biome modifier)
    write_json(f"{ND}/avp/worldgen/configured_feature/ore_{name}.json", {"type": "minecraft:ore", "config": {
        "size": size, "discard_chance_on_air_exposure": 0.0, "targets": [
            {"target": {"predicate_type": "minecraft:tag_match", "tag": "minecraft:stone_ore_replaceables"}, "state": {"Name": f"avp:{ore}"}},
            {"target": {"predicate_type": "minecraft:tag_match", "tag": "minecraft:deepslate_ore_replaceables"}, "state": {"Name": f"avp:{deep}"}}]}})
    write_json(f"{ND}/avp/worldgen/placed_feature/ore_{name}.json", {"feature": f"avp:ore_{name}", "placement": [
        {"type": "minecraft:count", "count": count},
        {"type": "minecraft:in_square"},
        {"type": "minecraft:height_range", "height": {"type": "minecraft:uniform",
                                                      "min_inclusive": {"absolute": lo}, "max_inclusive": {"absolute": hi}}},
        {"type": "minecraft:biome"}]})
    write_json(f"{ND}/avp/neoforge/biome_modifier/ore_{name}.json", {
        "neoforge:conditions": [{"type": "avp:ore_enabled", "ore": name}],
        "type": "neoforge:add_features", "biomes": biomes, "features": f"avp:ore_{name}", "step": "underground_ores"})

lang.update({
    "avp.configuration.worldgen": "World Generation",
    "avp.configuration.worldgen.button": "World Generation...",
    "avp.configuration.worldgen.tooltip": "Which ores generate in new chunks (restart needed)",
    "avp.configuration.generateOres": "Generate Ores",
    "avp.configuration.generateOres.tooltip": "Generate AvP ores in new chunks. Restart needed.",
})
for name in ORES:
    lang[f"avp.configuration.ore_{name}"] = f"{ORES[name][6]}"
    lang[f"avp.configuration.ore_{name}.tooltip"] = f"Generate {ORES[name][6].lower()} in new chunks."
write_json(f"{NA}/lang/en_us.json", lang)
print("ore generation written for:", ", ".join(ORES))
