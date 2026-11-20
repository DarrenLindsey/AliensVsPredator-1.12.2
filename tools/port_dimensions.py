"""Batch E2: Varda and Acheron (step 5 of the plan) - data-driven dimensions.

Run from the repo root:  python tools/port_dimensions.py
Needs vanilla's overworld noise settings (extracted to tools/vanilla_ref/ from the
"client-extra-aka-minecraft-resources" jar in build/moddev/artifacts).
Java: org.avp.world.dimension.ModDimensions (keys) + DimensionPortalBlock.

Varda (1.12.2 BiomeVarda): unidentified trees, ferns, stalagmites, black goo lakes, yellow water,
  Varda's wildlife. Two biomes: forest and badlands.
Acheron (LV-426, 1.12.2 BiomeAcheron): barren and dark - always night, storm-black skies - and
  crawling with Xenomorphs.
"""
import copy as cp, json, os

ND = "src/main/resources/data"
NA = "src/main/resources/assets/avp"
LA = "legacy-1.12.2/src/main/resources/assets/avp"
VREF = "tools/vanilla_ref/minecraft"


def write_json(path, obj):
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, "w") as f:
        json.dump(obj, f, indent=2)
        f.write("\n")


def read_json(path, default=None):
    return json.load(open(path)) if os.path.exists(path) else default


def state(name, **props):
    s = {"Name": name}
    if props:
        s["Properties"] = {k: str(v).lower() for k, v in props.items()}
    return s


def feature(name, ftype, config):
    write_json(f"{ND}/avp/worldgen/configured_feature/{name}.json", {"type": ftype, "config": config})


def placed(name, feat, placement):
    write_json(f"{ND}/avp/worldgen/placed_feature/{name}.json", {"feature": feat, "placement": placement})


SURFACE = [{"type": "minecraft:in_square"}, {"type": "minecraft:heightmap", "heightmap": "MOTION_BLOCKING"}, {"type": "minecraft:biome"}]

# ---------------------------------------------------------------- shared features
for name, height, rand in (("unitree", 6, 3), ("unitree_tall", 11, 6)):
    feature(name, "minecraft:tree", {
        "decorators": [], "dirt_provider": {"type": "minecraft:simple_state_provider", "state": state("avp:unidirt")},
        "foliage_placer": {"type": "minecraft:blob_foliage_placer", "height": 3, "offset": 0, "radius": 2 if name == "unitree" else 3},
        "foliage_provider": {"type": "minecraft:weighted_state_provider", "entries": [
            {"weight": 3, "data": state("avp:unitree_leaves", distance=7, persistent=False, waterlogged=False)},
            {"weight": 2, "data": state("avp:unitree_leaves_m", distance=7, persistent=False, waterlogged=False)},
            {"weight": 1, "data": state("avp:unitree_leaves_b", distance=7, persistent=False, waterlogged=False)}]},
        "force_dirt": True, "ignore_vines": True,
        "minimum_size": {"type": "minecraft:two_layers_feature_size", "limit": 1, "lower_size": 0, "upper_size": 1},
        "trunk_placer": {"type": "minecraft:straight_trunk_placer", "base_height": height, "height_rand_a": rand, "height_rand_b": 0},
        "trunk_provider": {"type": "minecraft:simple_state_provider", "state": state("avp:unitree_wood", axis="y")}})
placed("varda_trees", "avp:unitree", [{"type": "minecraft:count", "count": {"type": "minecraft:weighted_list", "distribution": [
    {"weight": 9, "data": 3}, {"weight": 1, "data": 6}]}}] + [SURFACE[0], {"type": "minecraft:surface_water_depth_filter", "max_water_depth": 0},
                                                       SURFACE[1], SURFACE[2]])
placed("varda_trees_tall", "avp:unitree_tall", [{"type": "minecraft:rarity_filter", "chance": 3}, SURFACE[0],
                                               {"type": "minecraft:surface_water_depth_filter", "max_water_depth": 0}, SURFACE[1], SURFACE[2]])


def patch(name, block, tries, count):
    feature(name, "minecraft:random_patch", {"tries": tries, "xz_spread": 6, "y_spread": 2, "feature": {
        "feature": {"type": "minecraft:simple_block", "config": {"to_place": {"type": "minecraft:simple_state_provider", "state": state(block)}}},
        "placement": [{"type": "minecraft:block_predicate_filter", "predicate": {"type": "minecraft:all_of", "predicates": [
            {"type": "minecraft:matching_blocks", "blocks": "minecraft:air"},
            {"type": "minecraft:matching_blocks", "offset": [0, -1, 0], "blocks": ["avp:unidirt", "avp:unistone", "avp:unigravel"]}]}}]}})
    placed(name, f"avp:{name}", [{"type": "minecraft:count", "count": count}] + SURFACE)


patch("varda_ground_ferns", "avp:paradisian_ground_fern", 32, 4)
patch("varda_tree_ferns", "avp:paradisian_tree_fern", 12, 1)
patch("varda_stalagmites", "avp:stalagmite", 16, 2)
patch("acheron_stalagmites", "avp:stalagmite", 10, 1)
feature("lake_blackgoo", "minecraft:lake", {"barrier": {"type": "minecraft:simple_state_provider", "state": state("avp:unistone")},
                                            "fluid": {"type": "minecraft:simple_state_provider", "state": state("avp:blackgoo", level=0)}})
placed("varda_lake_blackgoo", "avp:lake_blackgoo", [{"type": "minecraft:rarity_filter", "chance": 40}, {"type": "minecraft:in_square"},
                                                    {"type": "minecraft:heightmap", "heightmap": "WORLD_SURFACE_WG"}, {"type": "minecraft:biome"}])


def ore(name, block, size, count, top):
    feature(name, "minecraft:ore", {"discard_chance_on_air_exposure": 0.0, "size": size, "targets": [
        {"target": {"predicate_type": "minecraft:block_match", "block": "avp:unistone"}, "state": state(block)}]})
    placed(name, f"avp:{name}", [{"type": "minecraft:count", "count": count}, {"type": "minecraft:in_square"},
                                 {"type": "minecraft:height_range", "height": {"type": "minecraft:uniform", "min_inclusive": {"above_bottom": 0},
                                                                               "max_inclusive": {"absolute": top}}}, {"type": "minecraft:biome"}])


ore("unistone_coal", "minecraft:coal_ore", 16, 20, 128)
ore("unistone_iron", "minecraft:iron_ore", 8, 20, 64)
ore("unistone_diamond", "minecraft:diamond_ore", 4, 2, 16)
ore("unistone_lithium", "avp:orelithium", 6, 6, 48)
ore("unistone_gravel", "avp:unigravel", 32, 8, 160)
UNI_ORES = ["avp:unistone_gravel", "avp:unistone_coal", "avp:unistone_iron", "avp:unistone_diamond", "avp:unistone_lithium"]
CARVERS = {"air": ["minecraft:cave", "minecraft:cave_extra_underground", "minecraft:canyon"]}


def biome(name, *, temperature, sky, fog, water, water_fog, grass, features, monsters, creatures=(), water_creatures=(), precipitation=False,
          particle=None, music=None):
    feats = [[] for _ in range(11)]
    for step, fs in features.items():
        feats[step] = fs
    effects = {"fog_color": fog, "sky_color": sky, "water_color": water, "water_fog_color": water_fog, "grass_color": grass,
               "foliage_color": grass, "mood_sound": {"block_search_extent": 8, "offset": 2.0, "sound": "minecraft:ambient.cave", "tick_delay": 6000}}
    if particle:
        effects["particle"] = particle
    write_json(f"{ND}/avp/worldgen/biome/{name}.json", {
        "has_precipitation": precipitation, "temperature": temperature, "downfall": 0.4 if precipitation else 0.0, "effects": effects,
        "carvers": CARVERS, "features": feats, "spawn_costs": {},
        "spawners": {"monster": [{"type": t, "weight": w, "minCount": a, "maxCount": b} for t, w, a, b in monsters],
                     "creature": [{"type": t, "weight": w, "minCount": a, "maxCount": b} for t, w, a, b in creatures],
                     "water_creature": [{"type": t, "weight": w, "minCount": a, "maxCount": b} for t, w, a, b in water_creatures],
                     "ambient": [], "axolotls": [], "misc": [], "underground_water_creature": [], "water_ambient": []}})


VARDA_MOBS = [("avp:scelemur", 30, 2, 4), ("avp:ursuidae", 20, 1, 2), ("avp:hammerpede", 15, 1, 3), ("avp:goo_mutant", 6, 1, 1),
              ("avp:babyhead", 4, 1, 1), ("avp:trilobite", 3, 1, 1), ("avp:yautja_mutant", 2, 1, 1)]
biome("varda_forest", temperature=0.7, sky=0x0b1420, fog=0x020206, water=0xFFFF66, water_fog=0x6b6b2a, grass=0x2f3a2a,
      features={1: ["avp:varda_lake_blackgoo"], 6: UNI_ORES, 9: ["avp:varda_trees", "avp:varda_trees_tall", "avp:varda_ground_ferns",
                                                                 "avp:varda_tree_ferns", "avp:varda_stalagmites"]},
      monsters=VARDA_MOBS, water_creatures=[("avp:deacon_shark", 5, 1, 1)],
      particle={"options": {"type": "minecraft:white_ash"}, "probability": 0.003})
biome("varda_badlands", temperature=1.2, sky=0x1a1408, fog=0x050302, water=0xFFFF66, water_fog=0x6b6b2a, grass=0x3a3020,
      features={1: ["avp:varda_lake_blackgoo"], 6: UNI_ORES, 9: ["avp:varda_ground_ferns", "avp:varda_stalagmites"]},  # same order as the forest (the game requires it)
      monsters=VARDA_MOBS[2:], water_creatures=[("avp:deacon_shark", 5, 1, 1)])
biome("acheron", temperature=0.2, sky=0x05070a, fog=0x000308, water=0x2a3540, water_fog=0x050a10, grass=0x303030,
      features={6: UNI_ORES, 9: ["avp:acheron_stalagmites"]}, precipitation=True,
      monsters=[("avp:drone", 30, 1, 3), ("avp:facehugger", 20, 1, 2), ("avp:warrior", 10, 1, 2), ("avp:runner_drone", 6, 1, 3),
                ("avp:spitter", 3, 1, 1), ("avp:boiler", 2, 1, 1)],
      particle={"options": {"type": "minecraft:ash"}, "probability": 0.01})

# ---------------------------------------------------------------- terrain: vanilla's overworld shape, alien rock and soil
base = read_json(f"{VREF}/worldgen/noise_settings/overworld.json")


def noise_settings(name, sea_level, aquifers, surface_top, surface_under):
    n = cp.deepcopy(base)
    n["default_block"] = state("avp:unistone")
    n["sea_level"] = sea_level
    n["aquifers_enabled"] = aquifers
    n["ore_veins_enabled"] = False
    n["spawn_target"] = []
    n["surface_rule"] = {"type": "minecraft:sequence", "sequence": [
        {"type": "minecraft:condition", "if_true": {"type": "minecraft:vertical_gradient", "random_name": "minecraft:bedrock_floor",
                                                    "true_at_and_below": {"above_bottom": 0}, "false_at_and_above": {"above_bottom": 5}},
         "then_run": {"type": "minecraft:block", "result_state": state("minecraft:bedrock")}},
        {"type": "minecraft:condition", "if_true": {"type": "minecraft:above_preliminary_surface"}, "then_run": {"type": "minecraft:sequence", "sequence": [
            {"type": "minecraft:condition", "if_true": {"type": "minecraft:stone_depth", "offset": 0, "surface_type": "floor", "add_surface_depth": False,
                                                        "secondary_depth_range": 0},
             "then_run": {"type": "minecraft:sequence", "sequence": [
                 {"type": "minecraft:condition", "if_true": {"type": "minecraft:water", "offset": -1, "surface_depth_multiplier": 0, "add_stone_depth": False},
                  "then_run": {"type": "minecraft:block", "result_state": state(surface_top)}},
                 {"type": "minecraft:block", "result_state": state("avp:unigravel")}]}},
            {"type": "minecraft:condition", "if_true": {"type": "minecraft:stone_depth", "offset": 0, "surface_type": "floor", "add_surface_depth": True,
                                                        "secondary_depth_range": 0},
             "then_run": {"type": "minecraft:block", "result_state": state(surface_under)}}]}}]}
    write_json(f"{ND}/avp/worldgen/noise_settings/{name}.json", n)


noise_settings("varda", 63, True, "avp:unidirt", "avp:unidirt")
noise_settings("acheron", 40, False, "avp:unidirt", "avp:unistone")

DT = read_json(f"{VREF}/dimension_type/overworld.json")
varda_type = dict(DT, has_raids=False, bed_works=False, ambient_light=0.02, effects="minecraft:overworld",
                  monster_spawn_light_level={"type": "minecraft:uniform", "min_inclusive": 0, "max_inclusive": 9})
acheron_type = dict(DT, has_raids=False, bed_works=False, ambient_light=0.0, fixed_time=18000, effects="minecraft:overworld",
                    monster_spawn_light_level={"type": "minecraft:uniform", "min_inclusive": 0, "max_inclusive": 11})
write_json(f"{ND}/avp/dimension_type/varda.json", varda_type)
write_json(f"{ND}/avp/dimension_type/acheron.json", acheron_type)


def point(temp, humid, cont, eros, weird, offset=0.0):
    return {"temperature": temp, "humidity": humid, "continentalness": cont, "erosion": eros, "weirdness": weird, "depth": 0.0, "offset": offset}


write_json(f"{ND}/avp/dimension/varda.json", {"type": "avp:varda", "generator": {"type": "minecraft:noise", "settings": "avp:varda",
    "biome_source": {"type": "minecraft:multi_noise", "biomes": [
        {"biome": "avp:varda_forest", "parameters": point([-1.0, 0.3], [-0.2, 1.0], [-1.2, 1.0], [-1.0, 1.0], [-1.0, 1.0])},
        {"biome": "avp:varda_badlands", "parameters": point([0.3, 1.0], [-1.0, 1.0], [-1.2, 1.0], [-1.0, 1.0], [-1.0, 1.0])},
        {"biome": "avp:varda_badlands", "parameters": point([-1.0, 0.3], [-1.0, -0.2], [-1.2, 1.0], [-1.0, 1.0], [-1.0, 1.0])}]}}})
write_json(f"{ND}/avp/dimension/acheron.json", {"type": "avp:acheron", "generator": {"type": "minecraft:noise", "settings": "avp:acheron",
    "biome_source": {"type": "minecraft:fixed", "biome": "avp:acheron"}}})

# ---------------------------------------------------------------- portals
from shutil import copy as fcopy
lang = read_json(f"{NA}/lang/en_us.json", {})
for dim, name in (("varda", "Varda"), ("acheron", "Acheron")):
    os.makedirs(f"{NA}/textures/block", exist_ok=True)
    fcopy(f"{LA}/textures/blocks/portal.{dim}.png", f"{NA}/textures/block/portal_{dim}.png")
    if os.path.exists(f"{LA}/textures/blocks/portal.{dim}.png.mcmeta"):
        fcopy(f"{LA}/textures/blocks/portal.{dim}.png.mcmeta", f"{NA}/textures/block/portal_{dim}.png.mcmeta")
    write_json(f"{NA}/blockstates/portal_{dim}.json", {"variants": {"": {"model": f"avp:block/portal_{dim}"}}})
    write_json(f"{NA}/models/block/portal_{dim}.json", {"parent": "minecraft:block/cube_all", "render_type": "minecraft:translucent",
                                                       "textures": {"all": f"avp:block/portal_{dim}"}})
    write_json(f"{NA}/models/item/portal_{dim}.json", {"parent": f"avp:block/portal_{dim}"})
    lang[f"block.avp.portal_{dim}"] = f"{name} Portal"
    lang[f"biome.avp.{dim}"] = name
lang.update({
    "tooltip.avp.portal_varda": "Engineer technology. Step in to travel to Varda - and back.",
    "tooltip.avp.portal_acheron": "Engineer technology. Step in to travel to Acheron (LV-426) - and back.",
    "biome.avp.varda_forest": "Varda Forest", "biome.avp.varda_badlands": "Varda Badlands",
    "message.avp.portal_arrived": "You arrive on %s.",
})
write_json(f"{NA}/lang/en_us.json", lang)
print("batch E2 (dimensions) data written")
