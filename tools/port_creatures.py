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

# ================================================================ Ovamorph
lang = read_json(f"{NA}/lang/en_us.json", {})
copy(f"{LA}/textures/mob/alienegg.png", f"{NA}/textures/entity/ovamorph.png")
sounds_for("ovamorph", {"living": ["living0", "living1"], "open": ["open"]})
write_json(f"{NA}/models/item/ovamorph_spawn_egg.json", {"parent": "minecraft:item/template_spawn_egg"})
write_json(f"{ND}/avp/loot_table/entities/ovamorph.json", {"type": "minecraft:entity", "pools": [{
    "rolls": 1, "entries": [{"type": "minecraft:item", "name": "avp:royaljelly",
                             "functions": [{"function": "minecraft:set_count", "count": {"type": "minecraft:uniform", "min": 1, "max": 3}}]}]}]})
add_to_tag(f"{ND}/avp/tags/entity_type/xenomorphs.json", ["avp:ovamorph"])
lang.update({
    "entity.avp.ovamorph": "Ovamorph",
    "item.avp.ovamorph_spawn_egg": "Ovamorph Spawn Egg",
    "subtitles.avp.ovamorph.living": "Ovamorph pulses",
    "subtitles.avp.ovamorph.open": "Ovamorph opens",
    "avp.configuration.emptyOvamorphLifetime": "Empty Egg Lifetime (ticks)",
    "avp.configuration.emptyOvamorphLifetime.tooltip": "How long a hatched egg stays before it withers away.",
    "tooltip.avp.ovamorph_spawn_egg": "Xenomorph egg. Opens when a host comes near.\n\"Kane, don't touch it!\"",
})
write_json(f"{NA}/lang/en_us.json", lang)
print("creature data written: ovamorph")

# ================================================================ specimen capture (Special Order 937)
from PIL import Image, ImageDraw
lang = read_json(f"{NA}/lang/en_us.json", {})


def canister(path, with_egg):
    img = Image.new("RGBA", (16, 16), (0, 0, 0, 0))
    d = ImageDraw.Draw(img)
    d.rectangle([3, 1, 12, 2], fill=(70, 72, 78, 255))                     # lid
    d.rectangle([3, 13, 12, 14], fill=(70, 72, 78, 255))                   # base
    d.rectangle([4, 3, 11, 12], fill=(150, 200, 220, 110), outline=(200, 210, 220, 255))  # glass
    d.line([(5, 4), (5, 11)], fill=(230, 245, 255, 160))                   # glint
    if with_egg:
        d.ellipse([5, 5, 10, 12], fill=(92, 80, 60, 255), outline=(52, 44, 34, 255))
        d.line([(7, 5), (7, 8)], fill=(40, 34, 26, 255))
        d.line([(8, 5), (8, 8)], fill=(40, 34, 26, 255))
    d.rectangle([4, 7, 11, 7], fill=(214, 180, 40, 255))                  # W-Y yellow band
    img.save(path)


canister(f"{NA}/textures/item/specimen_container.png", False)
canister(f"{NA}/textures/item/contained_ovamorph.png", True)
for i in ("specimen_container", "contained_ovamorph"):
    write_json(f"{NA}/models/item/{i}.json", {"parent": "minecraft:item/generated", "textures": {"layer0": f"avp:item/{i}"}})
write_json(f"{ND}/avp/recipe/assembly/specimen_container.json", {"type": "avp:assembly", "ingredients": [
    {"item": "avp:industrialglass", "count": 2}, {"item": "avp:polycarbonate", "count": 2},
    {"tag": "c:ingots/aluminum", "count": 2}, {"item": "avp:cryo_coolant", "count": 1}],
    "result": {"id": "avp:specimen_container", "count": 1}})
lang.update({
    "item.avp.specimen_container": "Specimen Containment Unit",
    "item.avp.contained_ovamorph": "Contained Ovamorph",
    "tooltip.avp.specimen_container": "Weyland-Yutani biological containment unit.\nUse on a shut Ovamorph to capture it.\n\"Priority one: ensure return of organism for analysis.\"",
    "tooltip.avp.contained_ovamorph": "A live Ovamorph, sealed and dormant.\nUse on a block to release it, or store it in a cryostasis tube.",
    "message.avp.specimen.captured": "Specimen secured.",
    "message.avp.specimen.opening": "It's opening - too late to contain it!",
    "message.avp.specimen.empty_egg": "Nothing left inside to contain.",
    "message.avp.cryotube.stored": "Specimen placed in cryostasis.",
    "message.avp.cryotube.occupied": "This tube already holds a specimen.",
    "message.avp.cryotube.broken": "The glass is shattered - repair it with Industrial Glass.",
    "message.avp.cryotube.empty": "Empty. Use a contained specimen on it to store it here.",
    "message.avp.cryotube.frozen": "Specimen in stasis. Royal jelly is collected over time.",
    "message.avp.cryotube.thawing": "WARNING: no power - the specimen is thawing!",
    "tooltip.avp.cryostasistube": "Hypersleep chamber. Can hold a contained specimen in stasis.\nKeep it powered.",
    "avp.configuration.cryotubeHarvestTicks": "Specimen Jelly Interval (ticks)",
    "avp.configuration.cryotubeHarvestTicks.tooltip": "A frozen specimen in a powered tube gives one royal jelly this often.",
    "avp.configuration.cryotubeThawTicks": "Thaw Time Without Power (ticks)",
    "avp.configuration.cryotubeThawTicks.tooltip": "How long a tube can lose power before the specimen breaks out.",
})
write_json(f"{NA}/lang/en_us.json", lang)
print("specimen capture data written")

# ================================================================ Drone
lang = read_json(f"{NA}/lang/en_us.json", {})
copy(f"{LA}/textures/mob/drone_advanced.png", f"{NA}/textures/entity/drone.png")
sounds_for("alien", {"living": ["living0", "living1", "living2", "living3"], "hurt": ["hurt0", "hurt1"],
                     "death": ["death0", "death1"], "attack": ["attack0", "attack1", "attack2", "attack3"]})
write_json(f"{NA}/models/item/drone_spawn_egg.json", {"parent": "minecraft:item/template_spawn_egg"})
write_json(f"{ND}/avp/loot_table/entities/drone.json", {"type": "minecraft:entity", "pools": [{
    "rolls": 1, "entries": [{"type": "minecraft:item", "name": "avp:royaljelly",
                             "functions": [{"function": "minecraft:set_count", "count": {"type": "minecraft:uniform", "min": 1, "max": 3}},
                                           {"function": "minecraft:enchanted_count_increase", "enchantment": "minecraft:looting",
                                            "count": {"type": "minecraft:uniform", "min": 0, "max": 1}}]}],
    "conditions": [{"condition": "minecraft:killed_by_player"}]}]})
spawns("drone", ["#minecraft:is_jungle", "minecraft:swamp", "minecraft:mangrove_swamp", "minecraft:dark_forest"], 8, (1, 2))
add_to_tag(f"{ND}/avp/tags/entity_type/xenomorphs.json", ["avp:drone"])
lang.update({
    "entity.avp.drone": "Drone",
    "item.avp.drone_spawn_egg": "Drone Spawn Egg",
    "subtitles.avp.alien.living": "Xenomorph hisses",
    "subtitles.avp.alien.hurt": "Xenomorph shrieks",
    "subtitles.avp.alien.death": "Xenomorph dies",
    "subtitles.avp.alien.attack": "Xenomorph strikes",
    "avp.configuration.creatures": "Creatures",
    "avp.configuration.creatures.button": "Creatures...",
    "avp.configuration.creatures.tooltip": "Size, acid blood and growing up",
    "avp.configuration.filmAccurateSizes": "Film-Accurate Sizes",
    "avp.configuration.filmAccurateSizes.tooltip": "New creatures spawn at their film size (bigger adult Xenomorphs) instead of the classic size.",
    "avp.configuration.xenoAcidSplashDamage": "Acid Blood Splash Damage",
    "avp.configuration.xenoAcidSplashDamage.tooltip": "Damage to whoever hits a Xenomorph up close (0 = off).",
    "avp.configuration.xenoAcidWeaponDamage": "Acid Blood Weapon Damage",
    "avp.configuration.xenoAcidWeaponDamage.tooltip": "Durability acid blood eats off the weapon that hit a Xenomorph (0 = off).",
    "avp.configuration.droneMatureTicks": "Drone Maturing Time (ticks)",
    "avp.configuration.droneMatureTicks.tooltip": "How long a Drone takes to become a Warrior (once the Warrior is ported).",
    "avp.configuration.spawn_drone": "Drones",
    "avp.configuration.spawn_drone.tooltip": "Drones spawn in jungles, swamps and dark forests (in the dark).",
    "tooltip.avp.drone_spawn_egg": "Xenomorph XX121, adult drone.\n\"I admire its purity.\"",
})
write_json(f"{NA}/lang/en_us.json", lang)
print("creature data written: drone")

# ================================================================ Xenomorph hunger
lang = read_json(f"{NA}/lang/en_us.json", {})
lang.update({
    "avp.configuration.xenoHungerDrainTicks": "Hunger Drain (ticks per point)",
    "avp.configuration.xenoHungerDrainTicks.tooltip": "Xenomorphs have a hidden hunger meter (0-100) that drops by 1 this often.",
    "avp.configuration.xenoHungryBelow": "Hungry Below",
    "avp.configuration.xenoHungryBelow.tooltip": "Below this they hunt animals and creatures for food; above it they leave them alone unless attacked.",
    "avp.configuration.xenosAlwaysHuntPlayers": "Always Hunt Players",
    "avp.configuration.xenosAlwaysHuntPlayers.tooltip": "Hunt players even when full (people are hosts and threats, not food). Off: only when hungry or attacked.",
    "avp.configuration.xenosStarve": "Xenomorphs Can Starve",
    "avp.configuration.xenosStarve.tooltip": "With an empty hunger meter they slowly lose health.",
})
write_json(f"{NA}/lang/en_us.json", lang)
print("xenomorph hunger lang written")

# ================================================================ film-style Xenomorph behaviour
lang = read_json(f"{NA}/lang/en_us.json", {})
lang.update({
    "avp.configuration.behaviour": "Xenomorph Behaviour",
    "avp.configuration.behaviour.button": "Xenomorph Behaviour...",
    "avp.configuration.behaviour.tooltip": "How smart the Xenomorphs are",
    "avp.configuration.smartXenomorphs": "Film-Style Behaviour",
    "avp.configuration.smartXenomorphs.tooltip": "Senses, investigating, stalking, flanking, retreating and learning. Off: simple 1.12.2 AI.",
    "avp.configuration.xenoHearingRange": "Hearing Range (blocks)",
    "avp.configuration.xenoHearingRange.tooltip": "Sprinting or fighting is heard this far; walking at half, sneaking at a quarter.",
    "avp.configuration.xenoSightRange": "Sight Range (blocks)",
    "avp.configuration.xenoSightRange.tooltip": "How far they spot a player in plain view; sneaking players at a third.",
    "avp.configuration.xenosRetreatWhenHurt": "Retreat When Hurt",
    "avp.configuration.xenosRetreatWhenHurt.tooltip": "Badly hurt or burning Xenomorphs pull back into the dark.",
    "avp.configuration.xenosRegenerate": "Regenerate",
    "avp.configuration.xenosRegenerate.tooltip": "Out of combat, Xenomorphs slowly heal.",
    "avp.configuration.xenosBreakLights": "Smash Lights",
    "avp.configuration.xenosBreakLights.tooltip": "They sometimes smash torches and lanterns to make it darker.",
    "avp.configuration.xenosBreakDoors": "Smash Wooden Doors",
    "avp.configuration.xenosBreakDoors.tooltip": "They can smash through wooden doors (iron and blast doors stop them).",
    "avp.configuration.xenosLearnTurrets": "Learn Turret Positions",
    "avp.configuration.xenosLearnTurrets.tooltip": "Shot by a sentry turret, they remember it, warn the others and keep out of its range.",
})
write_json(f"{NA}/lang/en_us.json", lang)
print("xenomorph behaviour lang written")

# ================================================================ acid breach
lang = read_json(f"{NA}/lang/en_us.json", {})
add_to_tag(f"{ND}/avp/tags/block/acid_meltable.json", ["minecraft:iron_door", "minecraft:iron_trapdoor", "minecraft:iron_bars"])
lang.update({
    "avp.configuration.xenosAcidBreach": "Acid Breach",
    "avp.configuration.xenosAcidBreach.tooltip": "Stuck behind an iron door, they wound a packmate so its acid blood melts through (Alien Resurrection).",
    "avp.configuration.acidMeltAmount": "Acid Needed to Melt a Door",
    "avp.configuration.acidMeltAmount.tooltip": "Damage dealt to bleeding Xenomorphs next to a door before it melts.",
    "avp.configuration.acidBreachInterval": "Time Between Wounds (ticks)",
    "avp.configuration.acidBreachInterval.tooltip": "How often a Xenomorph wounds its packmate while melting a door.",
})
write_json(f"{NA}/lang/en_us.json", lang)
print("acid breach data written")

# ================================================================ glass, crawling, debug labels
lang = read_json(f"{NA}/lang/en_us.json", {})
add_to_tag(f"{ND}/avp/tags/block/xeno_breakable.json", ["#c:glass_blocks", "#c:glass_panes", "minecraft:glass", "minecraft:glass_pane",
                                                        "avp:industrialglass"])
lang.update({
    "avp.configuration.xenosBreakGlass": "Smash Glass",
    "avp.configuration.xenosBreakGlass.tooltip": "They smash glass blocks and panes between them and their prey.",
    "avp.configuration.xenosCrawl": "Crawl Through Gaps",
    "avp.configuration.xenosCrawl.tooltip": "They drop to a crawl to squeeze through vents and 1-block-high gaps.",
    "avp.configuration.xenoDebugLabels": "Debug Labels",
    "avp.configuration.xenoDebugLabels.tooltip": "Show what each Xenomorph is doing above its head (for testing).",
})
write_json(f"{NA}/lang/en_us.json", lang)
print("glass/crawl data written")

# ================================================================ digging and packs
lang = read_json(f"{NA}/lang/en_us.json", {})
add_to_tag(f"{ND}/avp/tags/block/xeno_unbreakable.json", ["#minecraft:dragon_immune", "#minecraft:wither_immune",
                                                          "minecraft:obsidian", "minecraft:crying_obsidian", "minecraft:reinforced_deepslate",
                                                          "avp:blastdoor", "avp:blastdoor_sevastopol"])
lang.update({
    "avp.configuration.xenosDigThroughBlocks": "Tear Through Blocks",
    "avp.configuration.xenosDigThroughBlocks.tooltip": "With no other way to their prey, they tear through the blocks in the way.",
    "avp.configuration.xenoDigMaxHardness": "Hardest Block They Break",
    "avp.configuration.xenoDigMaxHardness.tooltip": "Stone 1.5, iron block 5, obsidian 50. Blocks in avp:xeno_unbreakable are always safe.",
    "avp.configuration.xenoDigSpeed": "Digging Speed",
    "avp.configuration.xenoDigSpeed.tooltip": "1.0 = about a stone pickaxe on stone.",
    "avp.configuration.xenoPacks": "Packs",
    "avp.configuration.xenoPacks.tooltip": "Nearby Xenomorphs form packs: a leader, shared targets and knowledge, bolder in numbers.",
    "avp.configuration.xenoPackRange": "Pack Range (blocks)",
    "avp.configuration.xenoPackRange.tooltip": "How close they must be to join or alert a pack.",
})
write_json(f"{NA}/lang/en_us.json", lang)
print("digging/pack data written")

# ================================================================ the rest of the creatures (catalog: org.avp.entity.creature.Creatures)
import re
lang = read_json(f"{NA}/lang/en_us.json", {})
src = open("src/main/java/org/avp/entity/creature/Creatures.java").read()
catalog = []  # (id, family, texture, lore)
for m in re.finditer(r'(?:add|beast)\(CreatureSpec\.of\("(\w+)", (\w+)\)(.*?)\);\n', src, re.S):
    catalog.append((m.group(1), m.group(2), re.search(r'\.look\("\w+", "([\w.]+)"', m.group(3)).group(1),
                    re.search(r'\.lore\("(.*?)"\)', m.group(3)).group(1)))
for m in re.finditer(r'burster\("(\w+)", "\w+", "\w+", "([\w.]+)", [\d.F]+, 0x\w+, "(.*?)"\);', src):
    catalog.append((m.group(1), "BURSTER", m.group(2), m.group(3)))

NAMES = {"matriarch": "Xenomorph Queen", "chestburster_queen": "Queen Chestburster", "chestburster_predalien": "Predalien Chestburster",
         "chestburster_runner": "Runner Chestburster", "runner_drone": "Runner Drone", "runner_warrior": "Runner Warrior",
         "bat_xenomorph": "Bat Xenomorph", "deacon_adult": "Adult Deacon", "royal_facehugger": "Royal Facehugger",
         "draco_ovamorph": "Dracomorph Egg", "ovamorph_giger": "Giger Ovamorph", "yautja_warrior": "Yautja Warrior",
         "yautja_berserker": "Yautja Berserker", "marine": "Colonial Marine", "combat_synthetic": "Combat Synthetic",
         "space_jockey": "Space Jockey", "goo_mutant": "Goo Mutant", "deacon_shark": "Deacon Shark", "yautja_mutant": "Mutant Yautja",
         "predator_hound": "Predator Hound", "scelemur": "Scelemur", "ursuidae": "Ursuidae"}

for cid, family, tex, lore in catalog:
    copy(f"{LA}/textures/mob/{tex}", f"{NA}/textures/entity/{tex}")
    write_json(f"{NA}/models/item/{cid}_spawn_egg.json", {"parent": "minecraft:item/template_spawn_egg"})
    name = NAMES.get(cid, cid.replace("_", " ").title())
    lang[f"entity.avp.{cid}"] = name
    lang[f"item.avp.{cid}_spawn_egg"] = f"{name} Spawn Egg"
    lang[f"tooltip.avp.{cid}_spawn_egg"] = lore
    if family == "XENOMORPH":
        big = cid in ("matriarch", "dracomorph", "ultramorph")
        write_json(f"{ND}/avp/loot_table/entities/{cid}.json", {"type": "minecraft:entity", "pools": [{
            "rolls": 1, "entries": [{"type": "minecraft:item", "name": "avp:royaljelly",
                                     "functions": [{"function": "minecraft:set_count", "count": {"type": "minecraft:uniform",
                                                                                                "min": 4 if big else 1, "max": 8 if big else 3}},
                                                   {"function": "minecraft:enchanted_count_increase", "enchantment": "minecraft:looting",
                                                    "count": {"type": "minecraft:uniform", "min": 0, "max": 1}}]}],
            "conditions": [{"condition": "minecraft:killed_by_player"}]}]})
    if family in ("XENOMORPH", "BURSTER", "PARASITE", "EGG"):
        add_to_tag(f"{ND}/avp/tags/entity_type/xenomorphs.json", [f"avp:{cid}"])
    if family == "YAUTJA" or cid == "predator_hound":  # the Mutant Yautja is their enemy, not one of them
        add_to_tag(f"{ND}/avp/tags/entity_type/predators.json", [f"avp:{cid}"])
write_json(f"{ND}/avp/loot_table/entities/ursuidae.json", {"type": "minecraft:entity", "pools": [{
    "rolls": 1, "entries": [{"type": "minecraft:item", "name": "minecraft:leather",
                             "functions": [{"function": "minecraft:set_count", "count": {"type": "minecraft:uniform", "min": 0, "max": 2}}]}]},
    {"rolls": 1, "entries": [{"type": "minecraft:item", "name": "minecraft:porkchop",
                              "functions": [{"function": "minecraft:set_count", "count": {"type": "minecraft:uniform", "min": 1, "max": 3}},
                                            {"function": "minecraft:furnace_smelt", "conditions": [{"condition": "minecraft:entity_properties",
                                             "entity": "this", "predicate": {"flags": {"is_on_fire": True}}}]}]}]}]})

# hosts: bats and ocelots now carry embryos (Bat Xenomorph, Pantheramorph); synthetics never do
tag = read_json(f"{ND}/avp/tags/entity_type/cannot_host.json", {"replace": False, "values": []})
tag["values"] = [v for v in tag["values"] if v not in ("minecraft:bat", "minecraft:ocelot")]
write_json(f"{ND}/avp/tags/entity_type/cannot_host.json", tag)
add_to_tag(f"{ND}/avp/tags/entity_type/cannot_host.json", ["avp:combat_synthetic", "avp:creature_projectile"])
add_to_tag(f"{ND}/avp/tags/entity_type/door_threats.json", ["#avp:predators"])

# sounds
sounds_for("praetorian", {"living": ["living0", "living1", "living2"], "hurt": ["hurt0", "hurt1", "hurt2"], "death": ["death0", "death1"]})
sounds_for("queen", {"living": ["living0", "living1", "living2", "living3"], "hurt": ["hurt0", "hurt1", "hurt2", "hurt3"],
                     "death": ["hurt0", "hurt1", "hurt2", "hurt3"]})
sounds_for("predalien", {"living": ["living0"], "death": ["death0"], "attack": ["attack0", "attack1"]})
sounds_for("predator", {"living": ["living0", "living1", "living2", "living3"], "hurt": ["hurt0", "hurt1"], "death": ["death0", "death1"]})
sounds = read_json(f"{NA}/sounds.json", {})
sounds["mobs.predalien.hurt"] = {"subtitle": "subtitles.avp.predalien.hurt",
                                 "sounds": ["avp:mobs/alien/hurt0", "avp:mobs/alien/hurt1", "avp:mobs/predator/hurt0", "avp:mobs/predator/hurt1"]}
write_json(f"{NA}/sounds.json", sounds)
sounds_for("prequelmorph", {"living": ["living0", "living1", "living2", "living3"], "hurt": ["hurt0", "hurt1"], "death": ["death0", "death1"],
                            "attack": ["attack0", "attack1", "attack2", "attack3"]})
sounds_for("marine", {"hurt": ["hurt"], "death": ["death"]})
sounds = read_json(f"{NA}/sounds.json", {})
for f in ["shoot0", "shoot1", "shoot2", "shoot3"]:
    copy(f"{LA}/sounds/items/weapons/pulserifle/{f}.ogg", f"{NA}/sounds/items/weapons/pulserifle/{f}.ogg")
sounds["weapons.pulserifle"] = {"subtitle": "subtitles.avp.pulserifle", "sounds": [f"avp:items/weapons/pulserifle/shoot{i}" for i in range(4)]}
write_json(f"{NA}/sounds.json", sounds)
for s, label in [("praetorian", "Praetorian"), ("queen", "Xenomorph Queen"), ("predalien", "Predalien"), ("predator", "Yautja"),
                 ("prequelmorph", "Deacon"), ("marine", "Marine")]:
    for ev, verb in [("living", "growls" if s == "predator" else "hisses"), ("hurt", "hurts"), ("death", "dies"), ("attack", "strikes")]:
        lang[f"subtitles.avp.{s}.{ev}"] = f"{label} {verb}"
lang["subtitles.avp.pulserifle"] = "Pulse rifle fires"
lang["message.avp.implanted"] = "Something has been planted inside you..."

# natural spawns (ModEntityEvents.NATURAL; switches in avp-common.toml)
spawns("warrior", ["#minecraft:is_jungle", "minecraft:swamp", "minecraft:mangrove_swamp", "minecraft:dark_forest"], 4, (1, 1))
spawns("runner_drone", ["#minecraft:is_savanna", "#minecraft:is_badlands"], 5, (1, 3))
spawns("nauticomorph", ["#minecraft:is_ocean"], 3, (1, 1))
spawns("yautja_warrior", ["#minecraft:is_jungle"], 1, (1, 1))
spawns("marine", ["minecraft:plains"], 2, (2, 4))
spawns("engineer", ["minecraft:desert", "#minecraft:is_badlands"], 1, (1, 1))
spawns("trilobite", ["minecraft:deep_dark"], 2, (1, 1))
spawns("deacon_shark", ["#minecraft:is_deep_ocean"], 3, (1, 1))
spawns("scelemur", ["#minecraft:is_jungle"], 6, (2, 4))
spawns("ursuidae", ["#minecraft:is_taiga", "minecraft:forest"], 4, (1, 2))
spawns("hammerpede", ["minecraft:swamp", "minecraft:lush_caves"], 4, (1, 3))
spawns("goo_mutant", ["minecraft:deep_dark", "#minecraft:is_badlands"], 2, (1, 1))
for cid in ["warrior", "runner_drone", "nauticomorph", "yautja_warrior", "marine", "engineer", "trilobite", "deacon_shark",
            "scelemur", "ursuidae", "hammerpede", "goo_mutant"]:
    lang[f"avp.configuration.spawn_{cid}"] = NAMES.get(cid, cid.replace("_", " ").title()) + "s"
lang.update({
    "avp.configuration.queenEggTicks": "Queen Egg Laying (ticks)",
    "avp.configuration.queenEggTicks.tooltip": "A well-fed Queen lays an egg this often while she isn't fighting (up to 12 nearby).",
    "avp.configuration.yautjaHonourCode": "Yautja Honour Code",
    "avp.configuration.yautjaHonourCode.tooltip": "Yautja only hunt worthy prey: Xenomorphs, dangerous monsters and armed or armoured players.",
    "avp.configuration.yautjaCloak": "Yautja Cloaking",
    "avp.configuration.yautjaCloak.tooltip": "Yautja cloak while stalking; other creatures lose track of them.",
    "avp.configuration.marinesAlliedToPlayers": "Marines Fight For Players",
    "avp.configuration.marinesAlliedToPlayers.tooltip": "Marines and synthetics follow players and only turn on one that attacks them.",
    "avp.configuration.marineRifleDamage": "Pulse Rifle Damage",
    "avp.configuration.marineRifleDamage.tooltip": "Damage of each round a Marine fires (3-round bursts).",
})
write_json(f"{NA}/lang/en_us.json", lang)
print(f"catalog creature data written: {len(catalog)} creatures")

# ================================================================ hives
import random
from PIL import Image, ImageFilter
lang = read_json(f"{NA}/lang/en_us.json", {})
# resin coating texture: the 1.12.2 resin, cut into blobs and drips so the block underneath shows through
src_tex = Image.open(f"{LA}/textures/blocks/hiveresin.png").convert("RGBA")
w, h = src_tex.size
rng = random.Random(1979)
noise = Image.new("L", (w, h))
noise.putdata([rng.randint(0, 255) for _ in range(w * h)])
noise = noise.filter(ImageFilter.GaussianBlur(1.6))
lo, hi = min(noise.getdata()), max(noise.getdata())
drips = [rng.randint(0, w - 1) for _ in range(6)]
out = Image.new("RGBA", (w, h))
for y in range(h):
    for x in range(w):
        n = (noise.getpixel((x, y)) - lo) / max(1, hi - lo)
        edge = min(x, y, w - 1 - x, h - 1 - y) / (w / 2)  # resin gathers at the edges and corners
        keep = n + (0.35 - edge) * 0.8 > 0.55 or any(abs(x - d) <= 0 and y < h * 0.8 for d in drips)
        r, g, b, a = src_tex.getpixel((x, y))
        out.putpixel((x, y), (r, g, b, 255) if keep else (0, 0, 0, 0))
os.makedirs(f"{NA}/textures/block", exist_ok=True)
out.save(f"{NA}/textures/block/natural_resin.png")
write_json(f"{NA}/blockstates/natural_resin.json", {"variants": {"": {"model": "avp:block/natural_resin"}}})
write_json(f"{NA}/models/block/natural_resin.json", {"parent": "minecraft:block/cube_all", "render_type": "minecraft:cutout_mipped",
                                                    "textures": {"all": "avp:block/natural_resin", "particle": "avp:block/hiveresin_standard"}})
write_json(f"{ND}/avp/loot_table/blocks/natural_resin.json", {"type": "minecraft:block", "pools": [{
    "rolls": 1, "entries": [{"type": "minecraft:item", "name": "avp:hiveresin_standard"}],
    "conditions": [{"condition": "minecraft:random_chance", "chance": 0.25}]}]})
add_to_tag(f"{ND}/avp/tags/block/resin_coverable.json", [
    "#minecraft:base_stone_overworld", "#minecraft:base_stone_nether", "minecraft:dirt", "minecraft:coarse_dirt", "minecraft:rooted_dirt",
    "minecraft:mud", "minecraft:clay", "minecraft:gravel", "minecraft:sand", "minecraft:red_sand", "minecraft:sandstone", "minecraft:red_sandstone",
    "minecraft:terracotta", "minecraft:calcite", "minecraft:dripstone_block", "minecraft:cobblestone", "minecraft:mossy_cobblestone",
    "minecraft:cobbled_deepslate", "minecraft:end_stone", "minecraft:soul_soil", "minecraft:soul_sand", "minecraft:magma_block"])
add_to_tag(f"{ND}/minecraft/tags/block/mineable/shovel.json", ["avp:natural_resin"])
lang.update({
    "block.avp.natural_resin": "Hive Resin",
    "message.avp.cocoon_freed": "You cut the host free of the resin.",
    "avp.configuration.hives": "Hives",
    "avp.configuration.hives.tooltip": "Drones found hives deep underground, coat them in resin and bring hosts back alive.",
    "avp.configuration.hiveSpacing": "Hive Spacing (blocks)",
    "avp.configuration.hiveSpacing.tooltip": "Only one hive within this distance; Xenomorphs in range join it instead of starting another.",
    "avp.configuration.hiveRadius": "Hive Radius (blocks)",
    "avp.configuration.hiveRadius.tooltip": "How far a hive with a Queen grows from its core. Without a Queen it stays a small nest.",
    "avp.configuration.resinBuildSpeed": "Resin Building Speed",
    "avp.configuration.resinBuildSpeed.tooltip": "How fast Drones spread resin (0 = never).",
    "avp.configuration.resinCoversBuiltBlocks": "Resin Covers Built Blocks",
    "avp.configuration.resinCoversBuiltBlocks.tooltip": "Resin may coat player-built blocks too. Off: only natural ground (avp:resin_coverable).",
    "avp.configuration.xenosCocoonHosts": "Cocoon Hosts",
    "avp.configuration.xenosCocoonHosts.tooltip": "Drones carry worn-down animals and villagers back to the hive and cocoon them for the eggs.",
    "avp.configuration.royalSuccession": "Royal Succession",
    "avp.configuration.royalSuccession.tooltip": "A hive without a Queen feeds royal jelly from every kill to an heir until it grows into a Queen.",
    "avp.configuration.eggmorphing": "Eggmorphing",
    "avp.configuration.eggmorphing.tooltip": "Cocooned hosts slowly turn into eggs, so a hive can spread without a Queen.",
    "avp.configuration.eggmorphTicks": "Eggmorphing Time (ticks)",
    "avp.configuration.eggmorphTicks.tooltip": "How long a cocooned host takes to become an egg (24000 = one day).",
})
write_json(f"{NA}/lang/en_us.json", lang)
print("hive data written")
