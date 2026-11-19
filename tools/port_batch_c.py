"""Batch C1 of the AvP port: lockers, gun locker and supply crates (data files).

Run from the repo root:  python tools/port_batch_c.py
Java: StorageBlock, StorageBlockEntity, StorageMenu, StorageScreen, ModCapabilities.
Menu backgrounds: tools/make_gui_textures.py
"""
import json, os

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


BLOCK_DISPLAY = read_json(f"{NA}/models/item/slope.json", {}).get("display")  # normal block item display
STORAGE = {
    "locker": ("Locker", True),
    "gunlocker": ("Gun Locker", True),
    "supplychuteblock": ("Supply Crate (Weyland-Yutani)", False),
    "supplychuteblock_marines": ("Supply Crate (USCM)", False),
    "supplychuteblock_seegson": ("Supply Crate (Seegson)", False),
}
lang = read_json(f"{NA}/lang/en_us.json", {})
write_json(f"{NA}/models/block/storage.json", {"textures": {"particle": "minecraft:block/iron_block"}})
for i, (name, tall) in STORAGE.items():
    # drawn by ModelBlockRenderer; the block model only supplies break particles
    write_json(f"{NA}/blockstates/{i}.json", {"variants": {"": {"model": "avp:block/storage"}}})
    write_json(f"{NA}/models/item/{i}.json", {"parent": "minecraft:builtin/entity", "display": BLOCK_DISPLAY,
                                              "textures": {"particle": "minecraft:block/iron_block"}})
    entry = {"type": "minecraft:item", "name": f"avp:{i}"}
    pool = {"rolls": 1, "entries": [entry], "conditions": [{"condition": "minecraft:survives_explosion"}]}
    if tall:  # only the bottom half drops the locker (like vanilla doors)
        pool["conditions"].append({"condition": "minecraft:block_state_property", "block": f"avp:{i}",
                                   "properties": {"half": "lower"}})
    write_json(f"{ND}/avp/loot_table/blocks/{i}.json", {"type": "minecraft:block", "pools": [pool]})
    lang[f"block.avp.{i}"] = name
add_to_tag(f"{ND}/minecraft/tags/block/mineable/pickaxe.json", [f"avp:{i}" for i in STORAGE])

# what the gun locker accepts; AvP guns get added here when they're ported
add_to_tag(f"{ND}/avp/tags/item/gun_locker_items.json", ["#c:tools/ranged_weapon"])

lang["avp.configuration.lockersCanHide"] = "Lockers Can Hide Players"
lang["avp.configuration.lockersCanHide.tooltip"] = "Sneak + right-click a locker with an empty hand to hide inside it; sneak to get out."
lang["entity.avp.seat"] = "Seat"
write_json(f"{NA}/lang/en_us.json", lang)
print("batch C1 data written")


# ---------------------------------------------------------------- batch C2a: lights, cryostasis tube, ampule
POWERED = {
    "lightpanel": "Light Panel",
    "ccfltube": "Fluorescent Light",
    "cryostasistube": "Cryostasis Tube",
}
write_json(f"{NA}/models/block/powered.json", {"textures": {"particle": "minecraft:block/iron_block"}})
for i, name in POWERED.items():
    write_json(f"{NA}/blockstates/{i}.json", {"variants": {"": {"model": "avp:block/powered"}}})
    write_json(f"{NA}/models/item/{i}.json", {"parent": "minecraft:builtin/entity", "display": BLOCK_DISPLAY,
                                              "textures": {"particle": "minecraft:block/iron_block"}})
    write_json(f"{ND}/avp/loot_table/blocks/{i}.json", {"type": "minecraft:block", "pools": [{
        "rolls": 1, "entries": [{"type": "minecraft:item", "name": f"avp:{i}"}],
        "conditions": [{"condition": "minecraft:survives_explosion"}]}]})
    lang[f"block.avp.{i}"] = name
add_to_tag(f"{ND}/minecraft/tags/block/mineable/pickaxe.json", [f"avp:{i}" for i in POWERED])

# the ampule is drawn like the trophies (ModelBlockRenderer)
write_json(f"{NA}/blockstates/engineership_ampule.json", {"variants": {"": {"model": "avp:block/trophy"}}})
write_json(f"{NA}/models/item/engineership_ampule.json", {"parent": "minecraft:builtin/entity", "display": BLOCK_DISPLAY,
                                                          "textures": {"particle": "minecraft:block/bone_block_side"}})
write_json(f"{ND}/avp/loot_table/blocks/engineership_ampule.json", {"type": "minecraft:block", "pools": [{
    "rolls": 1, "entries": [{"type": "minecraft:item", "name": "avp:engineership_ampule"}],
    "conditions": [{"condition": "minecraft:survives_explosion"}]}]})
lang["block.avp.engineership_ampule"] = "Engineer Ampule"
add_to_tag(f"{ND}/minecraft/tags/block/mineable/pickaxe.json", ["avp:engineership_ampule"])

lang.update({
    "avp.configuration.power": "Power",
    "avp.configuration.power.button": "Power...",
    "avp.configuration.power.tooltip": "How lights and machines get their power",
    "avp.configuration.machinesNeedPower": "Machines Need Power",
    "avp.configuration.machinesNeedPower.tooltip": "Lights and machines only work when powered. Turn off to have them always on.",
    "avp.configuration.redstonePowersMachines": "Redstone Powers Machines",
    "avp.configuration.redstonePowersMachines.tooltip": "A redstone signal also powers lights and machines (no FE needed).",
    "avp.configuration.lightEnergyPerTick": "Light Energy Use (FE/tick)",
    "avp.configuration.lightEnergyPerTick.tooltip": "FE a light panel or fluorescent light uses per tick while on.",
    "avp.configuration.cryostasisTubeEnergyPerTick": "Cryostasis Tube Energy Use (FE/tick)",
    "avp.configuration.cryostasisTubeEnergyPerTick.tooltip": "FE a cryostasis tube uses per tick to keep its contents frozen.",
})
write_json(f"{NA}/lang/en_us.json", lang)
print("batch C2a data written")


# ---------------------------------------------------------------- batch C2b: medpod
i = "medpod"
write_json(f"{NA}/blockstates/{i}.json", {"variants": {"": {"model": "avp:block/powered"}}})
write_json(f"{NA}/models/item/{i}.json", {"parent": "minecraft:builtin/entity", "display": BLOCK_DISPLAY,
                                          "textures": {"particle": "minecraft:block/iron_block"}})
write_json(f"{ND}/avp/loot_table/blocks/{i}.json", {"type": "minecraft:block", "pools": [{
    "rolls": 1, "entries": [{"type": "minecraft:item", "name": f"avp:{i}"}],
    "conditions": [{"condition": "minecraft:survives_explosion"}]}]})
add_to_tag(f"{ND}/minecraft/tags/block/mineable/pickaxe.json", [f"avp:{i}"])
lang.update({
    "block.avp.medpod": "Medpod",
    "message.avp.medpod.no_power": "The medpod has no power.",
    "avp.configuration.medpodEnergyPerTick": "Medpod Energy Use (FE/tick)",
    "avp.configuration.medpodEnergyPerTick.tooltip": "FE a medpod uses per tick while powered.",
    "avp.configuration.medpod": "Medpod",
    "avp.configuration.medpod.button": "Medpod...",
    "avp.configuration.medpod.tooltip": "How the medpod treats whoever is inside",
    "avp.configuration.medpodHealInterval": "Heal Interval (ticks)",
    "avp.configuration.medpodHealInterval.tooltip": "Ticks between heals while inside a closed, powered medpod (20 ticks = 1 second).",
    "avp.configuration.medpodHealAmount": "Heal Amount",
    "avp.configuration.medpodHealAmount.tooltip": "Health restored each time (2 = one heart).",
    "avp.configuration.medpodCuresEffects": "Cures Harmful Effects",
    "avp.configuration.medpodCuresEffects.tooltip": "The medpod also removes harmful effects like poison and wither.",
    "avp.configuration.medpodReentryCooldown": "Re-entry Delay (ticks)",
    "avp.configuration.medpodReentryCooldown.tooltip": "After getting out, the medpod won't take you back in for this many ticks (20 = 1 second).",
    "avp.configuration.medpodEjectStrength": "Exit Push Strength",
    "avp.configuration.medpodEjectStrength.tooltip": "How hard you're pushed out of the medpod when you get out (0 = no push).",
    "avp.configuration.medpodBoost": "Golden Apple Boost",
    "avp.configuration.medpodBoost.tooltip": "Getting out after a treatment gives a short golden-apple-style boost (Regeneration II + Absorption).",
    "avp.configuration.medpodBoostMinTicks": "Treatment Time for Boost (ticks)",
    "avp.configuration.medpodBoostMinTicks.tooltip": "How long you must stay in a closed, powered medpod to get the boost (20 ticks = 1 second).",
    "avp.configuration.medpodRegenerationTicks": "Regeneration Length (ticks)",
    "avp.configuration.medpodRegenerationTicks.tooltip": "How long the Regeneration II lasts (golden apple: 100 = 5 seconds).",
    "avp.configuration.medpodAbsorptionTicks": "Absorption Length (ticks)",
    "avp.configuration.medpodAbsorptionTicks.tooltip": "How long the Absorption lasts (golden apple: 2400 = 2 minutes).",
})
write_json(f"{NA}/lang/en_us.json", lang)
print("batch C2b (medpod) data written")


# ---------------------------------------------------------------- batch C2b: blast doors, maintenance jack
import shutil
LA = "legacy-1.12.2/src/main/resources/assets/avp"
DOORS = {"blastdoor": "Blast Door", "blastdoor_sevastopol": "Blast Door (Sevastopol)"}
for i, name in DOORS.items():
    write_json(f"{NA}/blockstates/{i}.json", {"variants": {"": {"model": "avp:block/powered"}}})
    write_json(f"{NA}/models/item/{i}.json", {"parent": "minecraft:builtin/entity", "display": BLOCK_DISPLAY,
                                              "textures": {"particle": "minecraft:block/iron_block"}})
    # only the controller piece (part 0) drops the door
    write_json(f"{ND}/avp/loot_table/blocks/{i}.json", {"type": "minecraft:block", "pools": [{
        "rolls": 1, "entries": [{"type": "minecraft:item", "name": f"avp:{i}"}],
        "conditions": [{"condition": "minecraft:survives_explosion"},
                       {"condition": "minecraft:block_state_property", "block": f"avp:{i}", "properties": {"part": "0"}}]}]})
    lang[f"block.avp.{i}"] = name
add_to_tag(f"{ND}/minecraft/tags/block/mineable/pickaxe.json", [f"avp:{i}" for i in DOORS])
add_to_tag(f"{ND}/minecraft/tags/block/needs_iron_tool.json", [f"avp:{i}" for i in DOORS])

# maintenance jack item
shutil.copy(f"{LA}/textures/items/tool.maintenancejack.png", f"{NA}/textures/item/tool_maintenancejack.png")
write_json(f"{NA}/models/item/tool_maintenancejack.json", {"parent": "minecraft:item/handheld",
                                                           "textures": {"layer0": "avp:item/tool_maintenancejack"}})
lang["item.avp.tool_maintenancejack"] = "Maintenance Jack"
lang["item.avp.tool_securitytuner"] = "Security Access Tuner"

# sounds: copy the blast door .ogg files and their sounds.json entries
old_sounds = json.load(open(f"{LA}/sounds.json"))
sounds = read_json(f"{NA}/sounds.json", {})
for key, entry in old_sounds.items():
    if "blastdoor" in key:
        sounds[key] = entry
        for s in entry["sounds"]:
            path = (s if isinstance(s, str) else s["name"]).split(":")[1]
            os.makedirs(os.path.dirname(f"{NA}/sounds/{path}.ogg"), exist_ok=True)
            shutil.copy(f"{LA}/sounds/{path}.ogg", f"{NA}/sounds/{path}.ogg")
write_json(f"{NA}/sounds.json", sounds)

lang.update({
    "message.avp.blastdoor.no_power": "The blast door has no power.",
    "message.avp.blastdoor.opened": "Blast door opened.",
    "message.avp.blastdoor.closed": "Blast door closed.",
    "message.avp.blastdoor.unlocked": "Access granted.",
    "message.avp.blastdoor.denied": "Access denied.",
    "message.avp.blastdoor.wrong_tuner": "This tuner isn't bound to this door.",
    "message.avp.blastdoor.jack_only_unpowered": "The Maintenance Jack only works on closed doors with no power.",
    "message.avp.blastdoor.cannot_pry": "Blast doors can't be pried open on this world.",
    "message.avp.blastdoor.prying": "The blast door is %s%% open.",
    "message.avp.blastdoor.password_set": "Password set.",
    "message.avp.blastdoor.password_removed": "Password removed.",
    "message.avp.blastdoor.autolock_on": "Auto-Lock enabled.",
    "message.avp.blastdoor.autolock_off": "Auto-Lock disabled.",
    "message.avp.blastdoor.bound": "Tuner bound to %s.",
    "screen.avp.blastdoor.title": "Blast Door %s",
    "screen.avp.blastdoor.password": "Password",
    "screen.avp.blastdoor.password_hint": "Enter password, then press Enter",
    "screen.avp.blastdoor.new_password_hint": "New password (empty = none), Enter to set",
    "screen.avp.blastdoor.locked": "LOCKED",
    "screen.avp.blastdoor.unlocked": "UNLOCKED",
    "screen.avp.blastdoor.admin": "ADMIN",
    "screen.avp.blastdoor.autolock_enable": "Enable Auto-Lock",
    "screen.avp.blastdoor.autolock_disable": "Disable Auto-Lock",
    "screen.avp.blastdoor.bind": "Bind Tuner to This Door",
    "screen.avp.blastdoor.admin_help": "Only bound tuners can manage a bound door.",
    "tooltip.avp.securitytuner.unbound": "Not bound to a door",
    "tooltip.avp.securitytuner.bound": "Bound to %s",
    "tooltip.avp.maintenancejack": "Forces open blast doors that have no power.",
    "avp.configuration.blastDoorEnergyPerTick": "Blast Door Energy Use (FE/tick)",
    "avp.configuration.blastDoorEnergyPerTick.tooltip": "FE a blast door uses per tick while powered.",
    "avp.configuration.blastDoors": "Blast Doors",
    "avp.configuration.blastDoors.button": "Blast Doors...",
    "avp.configuration.blastDoors.tooltip": "Locks and forcing doors open",
    "avp.configuration.blastDoorAutolockTicks": "Auto-Lock Delay (ticks)",
    "avp.configuration.blastDoorAutolockTicks.tooltip": "With Auto-Lock on, an unlocked door closes and locks again after this many ticks (20 = 1 second).",
    "avp.configuration.blastDoorsCanBePried": "Doors Can Be Pried Open",
    "avp.configuration.blastDoorsCanBePried.tooltip": "Unpowered blast doors can be forced partly open with a Maintenance Jack.",
    "avp.configuration.doorSensorRange": "Door Sensor Range",
    "avp.configuration.doorSensorRange.tooltip": "How far (in blocks) a door's sensors reach: Auto-Open and Threat Detection.",
    "avp.configuration.doorAutoCloseTicks": "Auto-Close Delay (ticks)",
    "avp.configuration.doorAutoCloseTicks.tooltip": "Auto-Open closes the door this many ticks after the last player left (20 = 1 second).",
    "screen.avp.blastdoor.auto_open": "Auto-Open: %s",
    "screen.avp.blastdoor.threat": "Threat Detection: %s",
    "message.avp.blastdoor.lockdown": "LOCKDOWN - threat detected.",
    "message.avp.blastdoor.autoopen_on": "Auto-Open enabled.",
    "message.avp.blastdoor.autoopen_off": "Auto-Open disabled.",
    "message.avp.blastdoor.threat_on": "Threat Detection enabled.",
    "message.avp.blastdoor.threat_off": "Threat Detection disabled.",
})
# creatures that trip Threat Detection besides vanilla hostile mobs (AvP creatures get added here)
add_to_tag(f"{ND}/avp/tags/entity_type/door_threats.json", [])
write_json(f"{NA}/lang/en_us.json", lang)
print("batch C2b (blast doors) data written")
