"""Creatures (step 3 of the plan): data for each ported creature.

Run from the repo root:  python tools/port_creatures.py
Java: org.avp.entity.* (entities), org.avp.client.model.*EntityModel (animation),
org.avp.client.render.*Renderer, ModEntities / ModEntityEvents.

For each creature: texture, sounds, spawn egg model, loot table, natural spawning
(NeoForge biome modifier with an "avp:spawn_enabled" condition -> avp-common.toml), tags.
"""
import json, os, shutil

NA = "src/main/resources/assets/avp"
ND = "src/main/resources/data"
LA = "legacy-1.12.2/src/main/resources/assets/avp"


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


def copy(src, dst):
    os.makedirs(os.path.dirname(dst), exist_ok=True)
    shutil.copy(src, dst)


def sounds_for(creature, events):
    """events: {event name: [legacy files under sounds/mobs/<creature>/]}"""
    sounds = read_json(f"{NA}/sounds.json", {})
    for event, files in events.items():
        for f in files:
            copy(f"{LA}/sounds/mobs/{creature}/{f}.ogg", f"{NA}/sounds/mobs/{creature}/{f}.ogg")
        sounds[f"mobs.{creature}.{event}"] = {"subtitle": f"subtitles.avp.{creature}.{event}",
                                              "sounds": [f"avp:mobs/{creature}/{f}" for f in files]}
    write_json(f"{NA}/sounds.json", sounds)


def spawns(creature, biome_tag_values, weight, group):
    write_json(f"{ND}/avp/tags/worldgen/biome/spawns/{creature}.json", {"replace": False, "values": biome_tag_values})
    write_json(f"{ND}/avp/neoforge/biome_modifier/spawn_{creature}.json", {
        "neoforge:conditions": [{"type": "avp:spawn_enabled", "creature": creature}],
        "type": "neoforge:add_spawns", "biomes": f"#avp:spawns/{creature}",
        "spawners": [{"type": f"avp:{creature}", "weight": weight, "minCount": group[0], "maxCount": group[1]}]})


lang = read_json(f"{NA}/lang/en_us.json", {})

# ================================================================ shared: damage types, host rules
write_json(f"{ND}/avp/damage_type/acid.json", {"message_id": "avp.acid", "exhaustion": 0.1, "scaling": "never"})
write_json(f"{ND}/avp/damage_type/chestburster.json", {"message_id": "avp.chestburster", "exhaustion": 0.0, "scaling": "never"})
add_to_tag(f"{ND}/minecraft/tags/damage_type/bypasses_armor.json", ["avp:chestburster", "avp:acid"])
add_to_tag(f"{ND}/minecraft/tags/damage_type/bypasses_invulnerability.json", ["avp:chestburster"])
add_to_tag(f"{ND}/minecraft/tags/damage_type/bypasses_effects.json", ["avp:chestburster"])

# 1.12.2 IParasitoid.getDefaultEntityBlacklist, in 1.21 names (+ newer mobs that obviously can't host)
add_to_tag(f"{ND}/avp/tags/entity_type/cannot_host.json", [
    "#avp:xenomorphs", "minecraft:armor_stand", "minecraft:snow_golem", "minecraft:iron_golem",
    "#minecraft:skeletons", "#minecraft:zombies", "minecraft:spider", "minecraft:cave_spider", "minecraft:silverfish",
    "minecraft:ghast", "minecraft:blaze", "minecraft:slime", "minecraft:magma_cube", "minecraft:wither",
    "minecraft:shulker", "minecraft:endermite", "minecraft:enderman", "minecraft:rabbit", "minecraft:chicken",
    "minecraft:ocelot", "minecraft:parrot", "minecraft:bat", "minecraft:vex", "minecraft:allay", "minecraft:bee",
    "minecraft:breeze", "minecraft:armadillo", "minecraft:frog", "minecraft:tadpole", "minecraft:axolotl",
    "minecraft:cod", "minecraft:salmon", "minecraft:tropical_fish", "minecraft:pufferfish", "minecraft:phantom"])
add_to_tag(f"{ND}/avp/tags/item/facehugger_proof.json", [])  # e.g. the Predator biomask, when ported
lang.update({
    "death.attack.avp.acid": "%1$s was burned by acid blood",
    "death.attack.avp.acid.player": "%1$s was burned by acid blood while fighting %2$s",
    "death.attack.avp.chestburster": "%1$s had something burst out of their chest",
    "death.attack.avp.chestburster.player": "%1$s had something burst out of their chest",
    "message.avp.medpod.embryo_removed": "Surgery complete: foreign organism removed.",
    "message.avp.medpod.embryo_too_late": "Foreign organism too developed for removal.",
})
sounds = read_json(f"{NA}/sounds.json", {})
copy(f"{LA}/sounds/mobs/chestburster/burst.ogg", f"{NA}/sounds/mobs/chestburster/burst.ogg")
sounds["mobs.chestburster.burst"] = {"subtitle": "subtitles.avp.chestburster.burst", "sounds": ["avp:mobs/chestburster/burst"]}
write_json(f"{NA}/sounds.json", sounds)
lang["subtitles.avp.chestburster.burst"] = "Chestburster bursts out"

# ================================================================ Facehugger
copy(f"{LA}/textures/mob/facehugger.png", f"{NA}/textures/entity/facehugger.png")
copy(f"{LA}/textures/misc/facehugger.png", f"{NA}/textures/misc/facehugger_overlay.png")
sounds_for("facehugger", {"living": ["living0", "living1", "living2"], "hurt": ["hurt0"],
                          "death": ["death0", "death1", "death2"], "implant": ["implant0", "implant1", "implant2"]})
write_json(f"{NA}/models/item/facehugger_spawn_egg.json", {"parent": "minecraft:item/template_spawn_egg"})
write_json(f"{ND}/avp/loot_table/entities/facehugger.json", {"type": "minecraft:entity", "pools": [{
    "rolls": 1, "entries": [{"type": "minecraft:item", "name": "avp:royaljelly",
                             "functions": [{"function": "minecraft:set_count", "count": {"type": "minecraft:uniform", "min": 0, "max": 1}},
                                           {"function": "minecraft:enchanted_count_increase", "enchantment": "minecraft:looting",
                                            "count": {"type": "minecraft:uniform", "min": 0, "max": 1}}]}],
    "conditions": [{"condition": "minecraft:killed_by_player"}]}]})
spawns("facehugger", ["#minecraft:is_jungle", "minecraft:swamp", "minecraft:mangrove_swamp", "minecraft:dark_forest"], 3, (1, 2))
add_to_tag(f"{ND}/avp/tags/entity_type/xenomorphs.json", ["avp:facehugger"])
add_to_tag(f"{ND}/avp/tags/entity_type/door_threats.json", ["#avp:xenomorphs"])
lang.update({
    "entity.avp.facehugger": "Facehugger",
    "item.avp.facehugger_spawn_egg": "Facehugger Spawn Egg",
    "subtitles.avp.facehugger.living": "Facehugger skitters",
    "subtitles.avp.facehugger.hurt": "Facehugger hurts",
    "subtitles.avp.facehugger.death": "Facehugger dies",
    "subtitles.avp.facehugger.implant": "Facehugger latches on",
    "avp.configuration.parasites": "Parasites",
    "avp.configuration.parasites.button": "Parasites...",
    "avp.configuration.parasites.tooltip": "Facehuggers and embryos",
    "avp.configuration.facehuggerAttachTicks": "Time on the Face (ticks)",
    "avp.configuration.facehuggerAttachTicks.tooltip": "How long a facehugger stays on its host (20 ticks = 1 second).",
    "avp.configuration.facehuggerLifetimeAfter": "Spent Facehugger Lifetime (ticks)",
    "avp.configuration.facehuggerLifetimeAfter.tooltip": "How long a spent facehugger lies on its back before dying.",
    "avp.configuration.facehuggerHelmetMelt": "Helmet Acid Damage",
    "avp.configuration.facehuggerHelmetMelt.tooltip": "Durability a lunge takes off a helmet; it only latches on once the helmet breaks.",
    "avp.configuration.acidBloodDamage": "Acid Blood Damage",
    "avp.configuration.acidBloodDamage.tooltip": "Damage to the host when an attached facehugger is hurt (0 = off).",
    "avp.configuration.facehuggersTargetPlayers": "Facehuggers Target Players",
    "avp.configuration.facehuggersTargetPlayers.tooltip": "Facehuggers go after players (never creative or spectator).",
    "avp.configuration.embryoGestationTicks": "Embryo Gestation (ticks)",
    "avp.configuration.embryoGestationTicks.tooltip": "How long an embryo grows before it bursts out (20 ticks = 1 second).",
    "avp.configuration.embryoSymptoms": "Embryo Symptoms",
    "avp.configuration.embryoSymptoms.tooltip": "Hosts feel sick (nausea, weakness, hunger) near the end.",
    "avp.configuration.medpodRemovesEmbryo": "Medpod Removes Embryos",
    "avp.configuration.medpodRemovesEmbryo.tooltip": "A powered, closed medpod removes an embryo unless it's nearly grown.",
    "avp.configuration.spawning": "Creature Spawning",
    "avp.configuration.spawning.button": "Creature Spawning...",
    "avp.configuration.spawning.tooltip": "Which creatures spawn naturally (restart needed)",
    "avp.configuration.naturalSpawns": "Natural Spawns",
    "avp.configuration.naturalSpawns.tooltip": "AvP creatures spawn naturally. Restart needed.",
    "avp.configuration.spawn_facehugger": "Facehuggers",
    "avp.configuration.spawn_facehugger.tooltip": "Facehuggers spawn in jungles, swamps and dark forests (in the dark).",
    "tooltip.avp.facehugger_spawn_egg": "Xenomorph parasitoid, first stage.\n\"It's got a wonderful defence mechanism.\"",
})
write_json(f"{NA}/lang/en_us.json", lang)
print("creature data written: facehugger")

# ================================================================ Chestburster
lang = read_json(f"{NA}/lang/en_us.json", {})
copy(f"{LA}/textures/mob/chestburster.png", f"{NA}/textures/entity/chestburster.png")
sounds_for("chestburster", {"burst": ["burst"], "hurt": ["hurt"], "death": ["death"], "attack": ["attack"], "grow": ["grow"]})
write_json(f"{NA}/models/item/chestburster_spawn_egg.json", {"parent": "minecraft:item/template_spawn_egg"})
write_json(f"{ND}/avp/loot_table/entities/chestburster.json", {"type": "minecraft:entity", "pools": [{
    "rolls": 1, "entries": [{"type": "minecraft:item", "name": "avp:royaljelly",
                             "functions": [{"function": "minecraft:set_count", "count": {"type": "minecraft:uniform", "min": 0, "max": 2}},
                                           {"function": "minecraft:enchanted_count_increase", "enchantment": "minecraft:looting",
                                            "count": {"type": "minecraft:uniform", "min": 0, "max": 1}}]}],
    "conditions": [{"condition": "minecraft:killed_by_player"}]}]})
add_to_tag(f"{ND}/avp/tags/entity_type/xenomorphs.json", ["avp:chestburster"])
lang.update({
    "entity.avp.chestburster": "Chestburster",
    "item.avp.chestburster_spawn_egg": "Chestburster Spawn Egg",
    "subtitles.avp.chestburster.hurt": "Chestburster shrieks",
    "subtitles.avp.chestburster.death": "Chestburster dies",
    "subtitles.avp.chestburster.attack": "Chestburster bites",
    "subtitles.avp.chestburster.grow": "Chestburster grows",
    "avp.configuration.chestbursterMatureTicks": "Chestburster Growing Time (ticks)",
    "avp.configuration.chestbursterMatureTicks.tooltip": "How long a chestburster takes to grow into a Drone (64 royal jelly does it at once).",
    "tooltip.avp.chestburster_spawn_egg": "Xenomorph, second stage.\nGrows into a Drone (when the Drone is ported).",
})
write_json(f"{NA}/lang/en_us.json", lang)
print("creature data written: chestburster")
