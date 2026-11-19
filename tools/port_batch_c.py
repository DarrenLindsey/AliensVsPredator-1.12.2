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
