"""Batch F: guns, armour and Predator gear (step 4 of the plan).

Run from the repo root:  python tools/port_batch_f.py
Java: org.avp.item.firearm (Firearm/Firearms/FirearmItem/AmmoItem), client FirearmRenderer/FirearmOverlay.
Assembler schematics for these are written by tools/port_batch_d.py.
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


lang = read_json(f"{NA}/lang/en_us.json", {})
sounds = read_json(f"{NA}/sounds.json", {})

# ================================================================ F1: firearms
# 3D items: drawn by FirearmRenderer, which does all the positioning itself (identity display transforms)
IDENTITY = {k: {"rotation": [0, 0, 0], "translation": [0, 0, 0], "scale": [1, 1, 1]}
            for k in ("thirdperson_righthand", "thirdperson_lefthand", "firstperson_righthand", "firstperson_lefthand",
                      "gui", "head", "ground", "fixed")}
GUNS = {  # id: (name, model texture, lore)
    "gun_pistol": ("88 Mod 4 Pistol", "88mod4.png", "Standard-issue USCM sidearm. 12 rounds."),
    "gun_m4": ("M4 Carbine", "m4.png", "An old Earth-pattern carbine. 24 rounds."),
    "gun_ak47": ("AK-47", "ak-47.png", "Rugged, reliable, antique. 32 rounds."),
    "gun_m41a": ("M41A Pulse Rifle", "pulserifle.png", "10mm caseless, 99 rounds, fully automatic.\n\"Check it out, I am the ultimate badass!\""),
    "gun_m56sg": ("M56 Smartgun", "m56sg.png", "500 rounds; its targeting system steers rounds onto threats near the crosshair."),
    "gun_sniper": ("Sniper Rifle", "sniper.png", "Bolt action, 6 rounds, hits hard at long range."),
}
for gid, (name, tex, lore) in GUNS.items():
    copy(f"{LA}/textures/items/models/{tex}", f"{NA}/textures/item/models/{tex}")
    write_json(f"{NA}/models/item/{gid}.json", {"parent": "minecraft:builtin/entity", "gui_light": "front", "display": IDENTITY,
                                               "textures": {"particle": f"avp:item/models/{tex[:-4]}"}})
    lang[f"item.avp.{gid}"] = name
    lang[f"tooltip.avp.{gid}"] = lore
MAGS = {  # id: (legacy icon, name)
    "ammo_pistol": ("ammo.pistol.png", "Pistol Magazine"),
    "ammo_ar": ("ammo.ar.png", "Rifle Magazine"),
    "ammo_smg": ("ammo.smg.png", "Smartgun Ammo Drum"),
    "ammo_sniper": ("ammo.sniper.png", "Sniper Magazine"),
}
for mid, (icon, name) in MAGS.items():
    copy(f"{LA}/textures/items/{icon}", f"{NA}/textures/item/{mid}.png")
    write_json(f"{NA}/models/item/{mid}.json", {"parent": "minecraft:item/generated", "textures": {"layer0": f"avp:item/{mid}"}})
    lang[f"item.avp.{mid}"] = name
lang["tooltip.avp.ammo_ar"] = "Fits the M41A, M4 and AK-47."

WEAPON_SOUNDS = {  # event: (legacy folder, files)
    "weapons.pistol": ("pistol", ["shoot0", "shoot1"]), "weapons.sniper": ("sniper", ["shoot0"]),
    "weapons.ak47": ("ak47", ["shoot0"]), "weapons.m4": ("m4", ["shoot0"]),
    "weapons.m56sg": ("smartgun", ["shoot0", "shoot1", "shoot2"]),
}
for event, (folder, files) in WEAPON_SOUNDS.items():
    for f in files:
        copy(f"{LA}/sounds/items/weapons/{folder}/{f}.ogg", f"{NA}/sounds/items/weapons/{folder}/{f}.ogg")
    sounds[event] = {"subtitle": f"subtitles.avp.{event}", "sounds": [f"avp:items/weapons/{folder}/{f}" for f in files]}
    lang[f"subtitles.avp.{event}"] = "Gunshot"
copy(f"{LA}/sounds/items/weapons/cooldown.ogg", f"{NA}/sounds/items/weapons/cooldown.ogg")
sounds["weapons.reload"] = {"subtitle": "subtitles.avp.weapons.reload", "sounds": ["avp:items/weapons/cooldown"]}
lang["subtitles.avp.weapons.reload"] = "Gun reloads"
lang["subtitles.avp.weapons.m56sg"] = "Smartgun fires"
lang["subtitles.avp.weapons.pistol"] = "Pistol fires"

write_json(f"{ND}/avp/damage_type/bullet.json", {"message_id": "avp.bullet", "exhaustion": 0.1, "scaling": "never"})
add_to_tag(f"{ND}/minecraft/tags/damage_type/is_projectile.json", ["avp:bullet"])
add_to_tag(f"{ND}/minecraft/tags/damage_type/no_knockback.json", ["avp:bullet"])
lang.update({
    "death.attack.avp.bullet": "%1$s was shot",
    "death.attack.avp.bullet.player": "%1$s was shot by %2$s",
    "death.attack.avp.bullet.item": "%1$s was shot by %2$s using %3$s",
    "tooltip.avp.firearm.ammo": "Rounds: %s / %s",
    "tooltip.avp.firearm.stats": "%s damage per round, %s rounds/min, takes: %s",
    "tooltip.avp.firearm.controls": "Hold use to fire - sneak-use to reload",
    "message.avp.no_ammo": "Out of ammo - you need a %s",
    "hud.avp.reloading": "RELOADING %s%%",
})
write_json(f"{NA}/sounds.json", sounds)
write_json(f"{NA}/lang/en_us.json", lang)
print("batch F1 (firearms) data written")

# ================================================================ F2: Colonial Marine kit + all armour sets
lang = read_json(f"{NA}/lang/en_us.json", {})
sounds = read_json(f"{NA}/sounds.json", {})


def modelled(iid, tex, name, lore=None):
    """An item drawn with its 1.12.2 model (FirearmRenderer)."""
    copy(f"{LA}/textures/items/models/{tex}", f"{NA}/textures/item/models/{tex}")
    write_json(f"{NA}/models/item/{iid}.json", {"parent": "minecraft:builtin/entity", "gui_light": "front", "display": IDENTITY,
                                               "textures": {"particle": f"avp:item/models/{tex[:-4]}"}})
    lang[f"item.avp.{iid}"] = name
    if lore:
        lang[f"tooltip.avp.{iid}"] = lore


def flat(iid, icon, name, lore=None):
    """A normal flat item with its 1.12.2 icon."""
    copy(f"{LA}/textures/items/{icon}", f"{NA}/textures/item/{iid}.png")
    write_json(f"{NA}/models/item/{iid}.json", {"parent": "minecraft:item/generated", "textures": {"layer0": f"avp:item/{iid}"}})
    lang[f"item.avp.{iid}"] = name
    if lore:
        lang[f"tooltip.avp.{iid}"] = lore


flat("grenade_m40", "grenade.m40.png", "M40 Grenade", "Throw it, or press G with an M41A to fire it from the grenade launcher.")
flat("grenade_incendiary", "grenade.incindiary.png", "M40 Incendiary Grenade", "Sets everything around it alight.")
flat("ammo_flamethrower", "ammo.flamethrower.png", "Propane Tank", "Fuel for flamethrowers.")
modelled("gun_m240icu", "m240icu.png", "M240 Incinerator Unit", "\"Get away from her, you...\" - burns creatures and hive resin.")
modelled("gun_flamethrower_sevastopol", "flamethrower.sevastopol.png", "Sevastopol Flamethrower",
         "A maintenance flamethrower from Sevastopol Station. Shorter reach.")
modelled("motiontracker", "motiontracker.png", "M314 Motion Tracker",
         "Hold it to see anything moving in front of you, out to 30 metres.\n\"They're right on top of us!\"")
copy(f"{LA}/sounds/items/weapons/flamethrower/shoot0.ogg", f"{NA}/sounds/items/weapons/flamethrower/shoot0.ogg")
sounds["weapons.flamethrower"] = {"subtitle": "subtitles.avp.weapons.flamethrower", "sounds": ["avp:items/weapons/flamethrower/shoot0"]}
for s in ("ping", "pong"):
    copy(f"{LA}/sounds/items/motiontracker/{s}.ogg", f"{NA}/sounds/items/motiontracker/{s}.ogg")
    sounds[f"motiontracker.{s}"] = {"subtitle": "subtitles.avp.motiontracker", "sounds": [f"avp:items/motiontracker/{s}"]}
lang["subtitles.avp.weapons.flamethrower"] = "Flamethrower roars"
lang["subtitles.avp.motiontracker"] = "Motion tracker pings"

ARMOUR = {  # set: (legacy texture, legacy icon suffix, display name)
    "marine": ("marine", "tactical", "Colonial Marine"),
    "celtic": ("celtic", "celtic", "Yautja"),
    "mk50": ("mk50", "mk50", "MK50 Compression Suit"),
    "pressure": ("suit", "pressure", "Pressure Suit"),
    "xeno": ("xeno", "xeno", "Xenomorph Chitin"),
}
PIECES = [("helm", "helm", "Helmet"), ("body", "body", "Chestpiece"), ("legs", "legwear", "Leggings"), ("boots", "boots", "Boots")]
for key, (tex, icon, name) in ARMOUR.items():
    copy(f"{LA}/textures/armor/{tex}_1.png", f"{NA}/textures/models/armor/{key}_layer_1.png")
    copy(f"{LA}/textures/armor/{tex}_2.png", f"{NA}/textures/models/armor/{key}_layer_2.png")
    for new, old, piece in PIECES:
        flat(f"{new}_{key}", f"{old}.{icon}.png", f"{name} {piece}" if key != "celtic" or new != "helm" else "Yautja Biomask")
lang["tooltip.avp.helm_marine"] = "M10 pattern helmet. Its lamp lights up where you look in the dark."
lang["tooltip.avp.helm_celtic"] = "A biomask: see in the dark, and no facehugger can get at your face."
lang["tooltip.avp.body_mk50"] = "Full suit: sealed - breathe underwater."
lang["tooltip.avp.body_pressure"] = "Full suit: sealed - breathe underwater."
lang["tooltip.avp.body_xeno"] = "Full set: acid doesn't burn you."
add_to_tag(f"{ND}/avp/tags/item/facehugger_proof.json", ["avp:helm_celtic", "avp:helm_pressure", "avp:helm_mk50"])
for tag, slot in (("head_armor", "helm"), ("chest_armor", "body"), ("leg_armor", "legs"), ("foot_armor", "boots")):
    add_to_tag(f"{ND}/minecraft/tags/item/{tag}.json", [f"avp:{slot}_{k}" for k in ARMOUR])
    add_to_tag(f"{ND}/minecraft/tags/item/trimmable_armor.json", [f"avp:{slot}_{k}" for k in ARMOUR])

lang.update({
    "key.categories.avp": "Aliens vs Predator",
    "key.avp.grenade_launcher": "M41A Grenade Launcher",
    "key.avp.vision_mode": "Biomask Vision Mode",
    "key.avp.cloak": "Wristbracer Cloak",
    "key.avp.self_destruct": "Wristbracer Self-Destruct",
    "message.avp.no_grenades": "No grenades to load",
    "message.avp.no_fuel": "No propane tank",
    "tooltip.avp.flamethrower": "Hold use to spray fire. Runs on propane tanks.",
    "avp.configuration.grenadesBreakBlocks": "Grenades Break Blocks",
    "avp.configuration.grenadesBreakBlocks.tooltip": "Grenades blow up blocks (off: they only hurt creatures).",
    "avp.configuration.flamethrowerIgnites": "Flamethrowers Start Fires",
    "avp.configuration.flamethrowerIgnites.tooltip": "Flames set blocks alight where they land (they always burn hive resin).",
    "avp.configuration.helmetLamp": "Marine Helmet Lamp",
    "avp.configuration.helmetLamp.tooltip": "The Colonial Marine helmet lights up the dark where you look.",
})
write_json(f"{NA}/sounds.json", sounds)
write_json(f"{NA}/lang/en_us.json", lang)
print("batch F2 (Marine kit, armour) data written")
