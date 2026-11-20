"""Batch D1 of the AvP port: the power network (data files and two small textures).

Run from the repo root:  python tools/port_batch_d.py
Java: power/PowerNetwork, PowerlineBlock, RedstoneSensorBlock, PowerMachineBlock(+Entity),
PowerlineRenderer, PowerMachineRenderer.
"""
import json, os
from PIL import Image, ImageDraw

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


def drop_self(i):
    write_json(f"{ND}/avp/loot_table/blocks/{i}.json", {"type": "minecraft:block", "pools": [{
        "rolls": 1, "entries": [{"type": "minecraft:item", "name": f"avp:{i}"}],
        "conditions": [{"condition": "minecraft:survives_explosion"}]}]})


BLOCK_DISPLAY = read_json(f"{NA}/models/item/slope.json", {}).get("display")
lang = read_json(f"{NA}/lang/en_us.json", {})

# ---- blocks drawn by a renderer: the block model only gives break particles
DRAWN = {
    "powerline": "Powerline",
    "solarpanel": "Solar Panel",
    "universalgenerator": "Universal Generator",
    "generator": "Repulsion Generator",
    "transformer": "Transformer",
    "transformer_stepdown": "Step-Down Transformer",
    "powercell": "Power Cell",
    "satellitedish": "Satellite Dish",
    "teslacoil": "Tesla Coil",
}
for i, name in DRAWN.items():
    write_json(f"{NA}/blockstates/{i}.json", {"variants": {"": {"model": "avp:block/powered"}}})
    write_json(f"{NA}/models/item/{i}.json", {"parent": "minecraft:builtin/entity", "display": BLOCK_DISPLAY,
                                              "textures": {"particle": "minecraft:block/iron_block"}})
    drop_self(i)
    lang[f"block.avp.{i}"] = name


# ---- redstone sensor and emitter: plain cubes with their own little textures
def panel(path, core, glow):
    img = Image.new("RGBA", (16, 16), (70, 72, 76, 255))
    d = ImageDraw.Draw(img)
    d.rectangle([0, 0, 15, 15], outline=(40, 41, 44, 255))
    d.rectangle([1, 1, 14, 14], outline=(105, 108, 112, 255))
    for x, y in ((2, 2), (13, 2), (2, 13), (13, 13)):  # rivets
        d.point((x, y), fill=(140, 142, 146, 255))
    if core == "lens":
        d.ellipse([4, 4, 11, 11], fill=(25, 25, 28, 255))
        d.ellipse([5, 5, 10, 10], fill=glow)
    else:  # a grille of bars
        d.rectangle([4, 4, 11, 11], fill=(25, 25, 28, 255))
        for y in (5, 7, 9):
            d.line([(5, y), (10, y)], fill=glow)
    os.makedirs(os.path.dirname(path), exist_ok=True)
    img.save(path)


DARK, RED = (90, 15, 15, 255), (255, 40, 30, 255)
for i, name, core, prop in (("redstonesensor", "Redstone Sensor", "lens", "powered"),
                            ("redstoneemitter", "Redstone Emitter", "grille", "lit")):
    for on in (False, True):
        tex = f"{i}_on" if on else i
        panel(f"{NA}/textures/block/{tex}.png", core, RED if on else DARK)
        write_json(f"{NA}/models/block/{tex}.json", {"parent": "minecraft:block/cube_all",
                                                     "textures": {"all": f"avp:block/{tex}"}})
    write_json(f"{NA}/blockstates/{i}.json", {"variants": {
        f"{prop}=false": {"model": f"avp:block/{i}"}, f"{prop}=true": {"model": f"avp:block/{i}_on"}}})
    write_json(f"{NA}/models/item/{i}.json", {"parent": f"avp:block/{i}"})
    drop_self(i)
    lang[f"block.avp.{i}"] = name

ALL = list(DRAWN) + ["redstonesensor", "redstoneemitter"]
add_to_tag(f"{ND}/minecraft/tags/block/mineable/pickaxe.json", [f"avp:{i}" for i in ALL])

# ---- tesla coil: its own damage type (death message) and an extra-targets tag
write_json(f"{ND}/avp/damage_type/electricity.json",
           {"message_id": "avp.electricity", "exhaustion": 0.1, "scaling": "when_caused_by_living_non_player"})
add_to_tag(f"{ND}/avp/tags/entity_type/tesla_coil_targets.json", [])

# ---- recipes (crafting table for now; the Assembler takes over in batch D2)
def shaped(name, pattern, key, count=1):
    keys = {}
    for k, v in key.items():
        keys[k] = {"tag": v[1:]} if v.startswith("#") else {"item": v}
    write_json(f"{ND}/avp/recipe/{name}.json", {"type": "minecraft:crafting_shaped", "category": "redstone",
                                                "pattern": pattern, "key": keys,
                                                "result": {"id": f"avp:{name}", "count": count}})


CU, AL, PC = "#c:ingots/copper", "avp:ingot_aluminum", "avp:polycarbonate"
shaped("powerline", [" P ", "PCP", " P "], {"P": PC, "C": CU}, 4)
shaped("solarpanel", ["PPP", "SSS", "RCR"], {"P": PC, "S": "avp:silicon", "R": "minecraft:redstone", "C": CU})
shaped("transformer", ["CCC", "KIC", "CCC"], {"C": CU, "K": "avp:part_capacitor", "I": "minecraft:iron_block"})
shaped("transformer_stepdown", ["CCC", "CIR", "CCC"], {"C": CU, "R": "avp:part_regulator", "I": "minecraft:iron_block"})
shaped("universalgenerator", ["PDP", "TFS", "AAA"], {"P": PC, "D": "minecraft:diamond", "T": "avp:transformer",
                                                     "S": "avp:transformer_stepdown", "F": "minecraft:furnace", "A": AL})
shaped("generator", ["APA", "CNC", "ATA"], {"A": AL, "P": PC, "C": CU, "N": "avp:neodymium", "T": "avp:transformer"})
shaped("powercell", ["PKP", "KRK", "PKP"], {"P": PC, "K": "avp:part_capacitor", "R": "minecraft:redstone_block"})
shaped("redstonesensor", ["ARA", "LCL", "ARA"], {"A": AL, "R": "minecraft:redstone", "L": "avp:powerline", "C": "minecraft:comparator"})
shaped("redstoneemitter", ["ARA", "LTL", "ARA"], {"A": AL, "R": "minecraft:redstone", "L": "avp:powerline", "T": "minecraft:redstone_torch"})
shaped("satellitedish", ["AAA", "AIA", " B "], {"A": AL, "I": "avp:part_ic", "B": "minecraft:iron_block"})
shaped("teslacoil", ["KCK", "TST", "LBL"], {"K": "avp:part_capacitor", "C": CU, "T": "avp:transformer",
                                           "S": "avp:part_powersupply", "L": "avp:powerline", "B": "minecraft:iron_block"})

lang.update({
    "death.attack.avp.electricity": "%1$s was electrocuted",
    "death.attack.avp.electricity.player": "%1$s was electrocuted while fighting %2$s",
    "message.avp.powerline.info": "Powerline network: %s powerlines, %s blocks connected",
    "message.avp.powerline.too_big": "This network has over %s powerlines - too big to carry power (see settings)",
    "message.avp.power.stored": "%s / %s FE",
    "message.avp.power.making": "making %s FE/t",
    "message.avp.power.burning": "burning: %s FE/t, %ss of fuel left",
    "message.avp.power.no_fuel": "no fuel (right-click with coal, wood, a lava bucket...)",
    "message.avp.power.magnets": "%s/%s magnets, making %s FE/t",
    "message.avp.power.passing": "passing %s of up to %s FE/t",
    "message.avp.power.giving": "giving %s FE/t",
    "message.avp.power.on": "running",
    "message.avp.power.off": "no power",
    "avp.configuration.redstoneEmitterEnergyPerTick": "Redstone Emitter Energy Use (FE/tick)",
    "avp.configuration.redstoneEmitterEnergyPerTick.tooltip": "FE a redstone emitter uses per tick while it gives a redstone signal.",
    "avp.configuration.satelliteDishEnergyPerTick": "Satellite Dish Energy Use (FE/tick)",
    "avp.configuration.satelliteDishEnergyPerTick.tooltip": "FE a satellite dish uses per tick while powered.",
    "avp.configuration.powerNetwork": "Power Network",
    "avp.configuration.powerNetwork.button": "Power Network...",
    "avp.configuration.powerNetwork.tooltip": "Generators, transformers, power cells and powerlines",
    "avp.configuration.solarPanelOutput": "Solar Panel Output (FE/tick)",
    "avp.configuration.solarPanelOutput.tooltip": "FE per tick at noon in clear weather (less morning, evening and in rain; none at night).",
    "avp.configuration.generatorOutput": "Universal Generator Output (FE/tick)",
    "avp.configuration.generatorOutput.tooltip": "FE per tick while it burns fuel.",
    "avp.configuration.generatorFuelMultiplier": "Fuel Burn Time Multiplier",
    "avp.configuration.generatorFuelMultiplier.tooltip": "How long fuel lasts compared to a furnace (1.0 = same as a furnace).",
    "avp.configuration.repulsionOutputPerMagnet": "Repulsion Generator Output per Magnet (FE/tick)",
    "avp.configuration.repulsionOutputPerMagnet.tooltip": "FE per tick for each neodymium magnet inside (up to 8).",
    "avp.configuration.transformerRate": "Transformer Limit (FE/tick)",
    "avp.configuration.transformerRate.tooltip": "Most FE per tick a transformer passes from its back to its front.",
    "avp.configuration.stepdownTransformerRate": "Step-Down Transformer Limit (FE/tick)",
    "avp.configuration.stepdownTransformerRate.tooltip": "Most FE per tick a step-down transformer passes.",
    "avp.configuration.powercellCapacity": "Power Cell Capacity (FE)",
    "avp.configuration.powercellCapacity.tooltip": "How much FE a power cell stores.",
    "avp.configuration.powercellRate": "Power Cell Output (FE/tick)",
    "avp.configuration.powercellRate.tooltip": "Most FE per tick a power cell gives out (and takes in).",
    "avp.configuration.maxCableNetworkSize": "Largest Powerline Network",
    "avp.configuration.maxCableNetworkSize.tooltip": "Networks with more connected powerlines than this carry no power (limits lag).",
    "avp.configuration.teslaCoil": "Tesla Coil",
    "avp.configuration.teslaCoil.button": "Tesla Coil...",
    "avp.configuration.teslaCoil.tooltip": "How tesla coils defend an area",
    "avp.configuration.teslaCoilEnergyPerTick": "Standby Energy Use (FE/tick)",
    "avp.configuration.teslaCoilEnergyPerTick.tooltip": "FE a tesla coil uses per tick while switched on.",
    "avp.configuration.teslaCoilEnergyPerZap": "Energy per Strike (FE)",
    "avp.configuration.teslaCoilEnergyPerZap.tooltip": "Extra FE used each time it strikes a creature.",
    "avp.configuration.teslaCoilDamage": "Strike Damage",
    "avp.configuration.teslaCoilDamage.tooltip": "Damage per strike (2 = one heart).",
    "avp.configuration.teslaCoilRange": "Range (blocks)",
    "avp.configuration.teslaCoilRange.tooltip": "How far a tesla coil can strike.",
    "avp.configuration.teslaCoilCooldown": "Time Between Strikes (ticks)",
    "avp.configuration.teslaCoilCooldown.tooltip": "Ticks between strikes (20 = 1 second).",
    "avp.configuration.teslaCoilTargetsPlayers": "Strikes Players",
    "avp.configuration.teslaCoilTargetsPlayers.tooltip": "Also strike players (never in creative or spectator mode).",
    "avp.configuration.teslaCoilSetsFire": "Strikes Set Fire",
    "avp.configuration.teslaCoilSetsFire.tooltip": "Strikes set the target on fire for a few seconds.",
})
write_json(f"{NA}/lang/en_us.json", lang)
print("batch D1 data written")


# ================================================================ batch D2: Assembler, parts, terminal
import re, shutil

LA = "legacy-1.12.2/src/main/resources/assets/avp"
lang = read_json(f"{NA}/lang/en_us.json", {})


def item_model(i):
    write_json(f"{NA}/models/item/{i}.json", {"parent": "minecraft:item/generated", "textures": {"layer0": f"avp:item/{i}"}})


# ---- parts that existed in 1.12.2 but weren't ported yet (their old textures)
OLD_PARTS = {
    "part_processor": ("part.processor", "Processor"),
    "part_led_display": ("part.led.display", "LED Display"),
    "part_solidstatedrive": ("part.solidstatedrive", "Solid State Drive"),
    "ingot_lithium": ("ingot.lithium", "Lithium Ingot"),
}
for i, (old, name) in OLD_PARTS.items():
    shutil.copy(f"{LA}/textures/items/{old}.png", f"{NA}/textures/item/{i}.png")
    item_model(i)
    lang[f"item.avp.{i}"] = name


# ---- new, lore-friendly parts: small pixel-art textures drawn here
def px(img, pts, color):
    for p in pts:
        img.putpixel(p, color)


def servo():
    img = Image.new("RGBA", (16, 16), (0, 0, 0, 0))
    d = ImageDraw.Draw(img)
    d.rectangle([3, 5, 10, 12], fill=(95, 98, 104, 255), outline=(45, 46, 50, 255))   # motor can
    d.rectangle([4, 6, 9, 7], fill=(140, 144, 150, 255))                               # highlight
    d.rectangle([11, 7, 13, 10], fill=(184, 115, 51, 255), outline=(120, 70, 30, 255))  # copper gear hub
    px(img, [(14, 8), (14, 9), (12, 6), (12, 11)], (184, 115, 51, 255))               # gear teeth
    d.line([(4, 13), (9, 13)], fill=(200, 40, 30, 255))                                # wires
    d.line([(5, 14), (8, 14)], fill=(40, 40, 40, 255))
    return img


def sensor_array():
    img = Image.new("RGBA", (16, 16), (0, 0, 0, 0))
    d = ImageDraw.Draw(img)
    d.rectangle([2, 4, 13, 12], fill=(60, 62, 66, 255), outline=(30, 31, 34, 255))   # housing
    d.rectangle([3, 5, 8, 11], fill=(10, 30, 15, 255))                               # small screen
    d.arc([2, 4, 9, 14], 220, 320, fill=(60, 255, 120, 255))                         # motion-tracker sweep
    px(img, [(5, 7), (7, 9)], (60, 255, 120, 255))                                   # blips
    d.rectangle([10, 6, 12, 10], fill=(150, 150, 155, 255))                          # sensor heads
    px(img, [(11, 7), (11, 9)], (220, 40, 30, 255))
    return img


def coolant():
    img = Image.new("RGBA", (16, 16), (0, 0, 0, 0))
    d = ImageDraw.Draw(img)
    d.rectangle([5, 2, 10, 3], fill=(90, 92, 96, 255))                               # cap
    d.rectangle([4, 4, 11, 14], fill=(200, 205, 210, 255), outline=(110, 114, 120, 255))
    d.rectangle([5, 7, 10, 13], fill=(120, 210, 255, 255))                           # coolant
    d.line([(5, 7), (10, 7)], fill=(200, 245, 255, 255))
    d.rectangle([5, 9, 10, 10], fill=(240, 240, 240, 255))                           # W-Y label band
    px(img, [(6, 9), (8, 9)], (60, 60, 60, 255))
    return img


NEW_PARTS = {
    "part_servo": ("Servo Motor", servo),
    "part_sensor_array": ("Motion Sensor Array", sensor_array),
    "cryo_coolant": ("Hypersleep Coolant", coolant),
}
for i, (name, draw) in NEW_PARTS.items():
    draw().save(f"{NA}/textures/item/{i}.png")
    item_model(i)
    lang[f"item.avp.{i}"] = name

# ---- lithium: smelt the (already ported) ore
for kind, time in (("smelting", 200), ("blasting", 100)):
    write_json(f"{ND}/avp/recipe/ingot_lithium_from_{kind}_orelithium.json", {
        "type": f"minecraft:{kind}", "category": "misc", "ingredient": {"item": "avp:orelithium"},
        "result": {"id": "avp:ingot_lithium"}, "experience": 1.0, "cookingtime": time})
add_to_tag(f"{ND}/c/tags/item/ingots/lithium.json", ["avp:ingot_lithium"])
add_to_tag(f"{ND}/c/tags/item/ingots.json", ["#c:ingots/lithium"])

# ---- Assembler block (cube with its 1.12.2 textures) and terminal
shutil.copy(f"{LA}/textures/blocks/assembler.side.png", f"{NA}/textures/block/assembler_side.png")
shutil.copy(f"{LA}/textures/blocks/assembler.top.png", f"{NA}/textures/block/assembler_top.png")
write_json(f"{NA}/models/block/assembler.json", {"parent": "minecraft:block/cube_column",
                                                 "textures": {"side": "avp:block/assembler_side", "end": "avp:block/assembler_top"}})
write_json(f"{NA}/blockstates/assembler.json", {"variants": {"": {"model": "avp:block/assembler"}}})
write_json(f"{NA}/models/item/assembler.json", {"parent": "avp:block/assembler"})
write_json(f"{NA}/blockstates/terminal.json", {"variants": {"": {"model": "avp:block/powered"}}})
write_json(f"{NA}/models/item/terminal.json", {"parent": "minecraft:builtin/entity", "display": BLOCK_DISPLAY,
                                               "textures": {"particle": "minecraft:block/iron_block"}})
for i in ("assembler", "terminal"):
    drop_self(i)
add_to_tag(f"{ND}/minecraft/tags/block/mineable/pickaxe.json", ["avp:assembler", "avp:terminal"])
lang["block.avp.assembler"] = "Assembler"
lang["block.avp.terminal"] = "Terminal"

# ---- crafting-table recipes from 1.12.2 that needed lithium or the new parts
OLD_ITEM = lambda old: "avp:" + re.sub(r"[^a-z0-9_]", "_", old.split(":")[-1].lower())


def old_ingredient(x):
    x = x[0] if isinstance(x, list) else x
    if "ore" in x:
        return {"polycarbonate": {"item": "avp:polycarbonate"}, "silicon": {"item": "avp:silicon"},
                "lithium": {"tag": "c:ingots/lithium"}, "ingotCopper": {"tag": "c:ingots/copper"},
                "ingotAluminum": {"tag": "c:ingots/aluminum"}}[x["ore"]]
    item = x["item"]
    if item == "transformer":  # a typo in the 1.12.2 power supply recipe
        item = "avp:transformer"
    return {"item": item if item.startswith("minecraft:") else OLD_ITEM(item)}


for old in ("capacitor", "processor", "led_display", "solidstatedrive", "powersupply", "assembler", "tool.securitytuner"):
    r = json.load(open(f"{LA}/recipes/{old}.json"))
    used = set("".join(r["pattern"])) - {" "}
    result = {"id": OLD_ITEM(r["result"]["item"])}
    if r["result"].get("count", 1) > 1:
        result["count"] = r["result"]["count"]
    write_json(f"{ND}/avp/recipe/{result['id'][4:]}.json", {
        "type": "minecraft:crafting_shaped", "category": "misc", "pattern": r["pattern"],
        "key": {k: old_ingredient(v) for k, v in r["key"].items() if k in used}, "result": result})

# ---- Assembler schematics (1.12.2 AssemblyManager), as avp:assembly recipes
SRC = open("legacy-1.12.2/src/main/java/org/avp/CraftingHandler.java").read()
NAMES = {  # 1.12.2 field -> 1.21 ingredient / result id
    "itemIngotCopper": "#c:ingots/copper", "itemIngotAluminum": "#c:ingots/aluminum", "itemIngotLithium": "#c:ingots/lithium",
    "transformer": "avp:transformer", "stepdownTransformer": "avp:transformer_stepdown", "powerline": "avp:powerline",
    "itemCapacitor": "avp:part_capacitor", "itemPowerSupply": "avp:part_powersupply", "itemLedDisplay": "avp:part_led_display",
    "muthurPanel1": "avp:mainframepanel_shimmer", "muthurPanel2": "avp:mainframepanel_flicker", "itemRAM": "avp:part_ram",
    "itemProcessor": "avp:part_processor", "itemSolidStateDrive": "avp:part_solidstatedrive", "itemMotherboard": "avp:part_motherboard",
    "itemPolycarbonate": "avp:polycarbonate", "itemArtifactTech": "avp:artifact_tech", "lightPanel": "avp:lightpanel",
    "industrialglass": "avp:industrialglass", "paddingSquareWhite": "avp:padding_square_white",
    "itemChargePack": "avp:part_powersupply",            # the charge pack comes with the guns; a power supply stands in
    "POTIONITEM": "minecraft:glistering_melon_slice",    # was "any potion"
    "itemDoritos": "avp:food_doritos", "itemIntegratedCircuit": "avp:part_ic", "itemCarbon": "avp:carbon",
    "itemSilicon": "avp:silicon", "itemResistor": "avp:part_resistor", "itemDiode": "avp:part_diode", "itemLed": "avp:part_led",
    "itemVoltageRegulator": "avp:part_regulator", "itemTransistor": "avp:part_transistor", "WOOL": "#minecraft:wool",
    # results
    "teslaCoil": "avp:teslacoil", "terminal": "avp:terminal", "slope": "avp:slope", "corner": "avp:corner",
    "invertedCorner": "avp:invertedcorner", "ridge": "avp:ridge", "pyramid": "avp:pyramid", "invertedRidge": "avp:invertedridge",
    "invertedPyramid": "avp:invertedpyramid", "medpod": "avp:medpod", "universalGenerator": "avp:universalgenerator",
    "cryoTube": "avp:cryostasistube", "itemDoritosCoolRanch": "avp:food_doritos_coolranch",
    # firearms (batch F1): the M41A and the magazines keep their 1.12.2 schematics
    "itemM41A": "avp:gun_m41a", "itemAmmoPistol": "avp:ammo_pistol", "itemAmmoAR": "avp:ammo_ar",
    "itemAmmoSMG": "avp:ammo_smg", "itemAmmoSniper": "avp:ammo_sniper",
    "itemGrenade": "avp:grenade_m40", "itemIncendiaryGrenade": "avp:grenade_incendiary", "itemMotionTracker": "avp:motiontracker",
    "itemM240ICU": "avp:gun_m240icu", "helmMarine": "avp:helm_marine", "plateMarine": "avp:body_marine",
    "legsMarine": "avp:legs_marine", "bootsMarine": "avp:boots_marine",
    "biomaskCeltic": "avp:helm_celtic", "chestplateCeltic": "avp:body_celtic", "legsCeltic": "avp:legs_celtic",
    "bootsCeltic": "avp:boots_celtic", "axeCeltic": "avp:tool_celtic_axe", "pickaxeCeltic": "avp:tool_celtic_pickaxe",
    "hoeCeltic": "avp:tool_celtic_hoe", "shovelCeltic": "avp:tool_celtic_shovel", "swordCeltic": "avp:tool_celtic_sword",
    "itemWristbracer": "avp:wristbracer", "itemWristbracerBlades": "avp:wristbracer_blades", "itemProximityMine": "avp:mine_laser",
    "itemDisc": "avp:smartdisc", "itemShuriken": "avp:shuriken", "itemSpear": "avp:tool_celtic_spear",
    "itemM41A": "avp:gun_m41a", "turret": "avp:turret",
    "DIAMOND_HELMET": "minecraft:diamond_helmet", "DIAMOND_CHESTPLATE": "minecraft:diamond_chestplate",
}
AVP_IDS = set(re.findall(r'\("([a-z0-9_]+)"', open("src/main/java/org/avp/registry/ModBlocks.java").read()
                         + open("src/main/java/org/avp/registry/ModItems.java").read()))
# items registered in loops (firearms: org.avp.item.firearm.Firearms / AmmoType)
AVP_IDS |= set(re.findall(r'item\("([a-z0-9_]+)"', open("src/main/java/org/avp/registry/ModGear.java").read()))
AVP_IDS |= {f"{p}_{k}" for p in ("helm", "body", "legs", "boots") for k in ("marine", "celtic", "mk50", "pressure", "xeno")}
AVP_IDS |= {"gun_pistol", "gun_m4", "gun_ak47", "gun_m41a", "gun_m56sg", "gun_sniper", "ammo_pistol", "ammo_ar", "ammo_smg", "ammo_sniper"}


def new_id(field):
    if field in NAMES:
        return NAMES[field]
    if field.isupper():
        return "minecraft:" + field.lower()
    return None


def ingredient_json(ref, count):
    out = {"tag": ref[1:]} if ref.startswith("#") else {"item": ref}
    out["count"] = count
    return out


assembly_dir = f"{ND}/avp/recipe/assembly"
shutil.rmtree(assembly_dir, ignore_errors=True)
written, pending = [], []
for m in re.finditer(r'Schematic\("([^"]+)",\s*new ItemStack\(\w+\.(\w+)(?:,\s*(\d+))?[^)]*\)\)\s*\{(.*?)\}\s*\);', SRC, re.S):
    name, res, cnt, body = m.groups()
    reqs = [(f.split(".")[-1], int(c or 1)) for f, c in re.findall(r'new ItemStack\(([\w.]+)(?:,\s*(\d+))?(?:,\s*\d+)?\)', body)]
    result = new_id(res) or (("avp:" + re.sub(r"(?<!^)(?=[A-Z])", "_", res[4:]).lower()) if res.startswith("item") else None)
    ids = [new_id(f) for f, _ in reqs]
    ok = result and result[4:] in AVP_IDS and all(i and (i.startswith("#") or i.startswith("minecraft:") or i[4:] in AVP_IDS) for i in ids)
    if not ok:
        pending.append(name)
        continue
    ingredients = [ingredient_json(i, c) for i, (_, c) in zip(ids, reqs)]
    if name == "cryostasisTube":
        ingredients.append(ingredient_json("avp:cryo_coolant", 2))  # new: keeps the sleeper frozen
    if name == "teslacoil":
        ingredients.append(ingredient_json("avp:part_sensor_array", 1))  # new: how it finds targets
    write_json(f"{assembly_dir}/{result[4:]}.json", {"type": "avp:assembly", "ingredients": ingredients,
                                                     "result": {"id": result, "count": int(cnt or 1)}})
    written.append(name)

# new schematics: the D1 power blocks and the new parts
NEW = {
    "part_servo": ([("#c:ingots/iron", 2), ("#c:ingots/copper", 1), ("avp:neodymium_magnet", 1), ("avp:part_regulator", 1)], 2),
    "part_sensor_array": ([("avp:part_processor", 1), ("avp:part_led_display", 1), ("#c:ingots/aluminum", 2),
                           ("avp:part_diode", 2), ("avp:part_ic", 1)], 1),
    "cryo_coolant": ([("#c:ingots/lithium", 1), ("minecraft:packed_ice", 4), ("avp:polycarbonate", 1)], 2),
    "transformer": ([("#c:ingots/copper", 7), ("avp:part_capacitor", 1), ("minecraft:iron_block", 1)], 1),
    "transformer_stepdown": ([("#c:ingots/copper", 7), ("avp:part_regulator", 1), ("minecraft:iron_block", 1)], 1),
    "solarpanel": ([("avp:polycarbonate", 3), ("avp:silicon", 3), ("#c:ingots/lithium", 2), ("#c:ingots/copper", 1)], 1),
    "powercell": ([("avp:polycarbonate", 4), ("avp:part_capacitor", 4), ("#c:ingots/lithium", 2), ("minecraft:redstone_block", 1)], 1),
    "generator": ([("#c:ingots/aluminum", 4), ("avp:polycarbonate", 1), ("#c:ingots/copper", 2), ("avp:neodymium", 1),
                   ("avp:transformer", 1), ("avp:part_servo", 1)], 1),
    "redstonesensor": ([("#c:ingots/aluminum", 4), ("minecraft:redstone", 2), ("avp:powerline", 2), ("minecraft:comparator", 1)], 1),
    "redstoneemitter": ([("#c:ingots/aluminum", 4), ("minecraft:redstone", 2), ("avp:powerline", 2), ("minecraft:redstone_torch", 1)], 1),
    "satellitedish": ([("#c:ingots/aluminum", 5), ("avp:part_ic", 1), ("avp:part_servo", 1), ("minecraft:iron_block", 1)], 1),
    "tool_securitytuner": ([("avp:part_led_display", 1), ("avp:part_processor", 1), ("avp:polycarbonate", 5),
                            ("minecraft:stone_button", 2)], 1),
    # firearms (batch F1): 1.12.2 built these from stock/barrel/action parts that never had icons;
    # the Assembler now fabricates them whole from W-Y components
    "gun_pistol": ([("#c:ingots/iron", 4), ("#c:ingots/aluminum", 2), ("avp:polycarbonate", 1), ("avp:part_servo", 1)], 1),
    "gun_m4": ([("#c:ingots/iron", 6), ("#c:ingots/aluminum", 4), ("avp:polycarbonate", 3), ("avp:part_servo", 1)], 1),
    "gun_ak47": ([("#c:ingots/iron", 7), ("#minecraft:planks", 2), ("#c:ingots/copper", 2), ("avp:part_servo", 1)], 1),
    "gun_sniper": ([("#c:ingots/iron", 6), ("#c:ingots/aluminum", 4), ("avp:polycarbonate", 3), ("avp:part_servo", 1),
                    ("minecraft:spyglass", 1)], 1),
    "plasmacannon": ([("avp:artifact_tech", 2), ("minecraft:diamond", 2), ("avp:part_processor", 1), ("avp:part_capacitor", 2),
                      ("#c:ingots/lithium", 2)], 1),
    "gun_m56sg": ([("#c:ingots/iron", 8), ("#c:ingots/aluminum", 8), ("avp:polycarbonate", 6), ("avp:part_servo", 2),
                   ("avp:part_sensor_array", 1), ("avp:part_processor", 2), ("avp:part_led_display", 1)], 1),
}
for result, (ings, count) in NEW.items():
    write_json(f"{assembly_dir}/{result}.json", {"type": "avp:assembly",
                                                 "ingredients": [ingredient_json(i, c) for i, c in ings],
                                                 "result": {"id": f"avp:{result}", "count": count}})
print(f"assembly schematics: {len(written)} from 1.12.2 + {len(NEW)} new; waiting for their items: {', '.join(pending)}")

lang.update({
    "screen.avp.assembler.search": "Search schematics...",
    "screen.avp.assembler.none": "No schematics match.",
    "screen.avp.assembler.energy": "%s FE (%s per item)",
    "message.avp.assembler.made": "Assembled %s x %s",
    "message.avp.assembler.missing": "You don't have all the materials.",
    "message.avp.assembler.no_power": "Not enough power (needs %s FE per item).",
    "terminal.avp.header": "WEYLAND-YUTANI CORP",
    "terminal.avp.interface": "MU/TH/UR 6000 - POWER INTERFACE",
    "terminal.avp.no_network": "NO POWERLINE CONNECTED",
    "terminal.avp.network": "LINES: %s   DEVICES: %s",
    "terminal.avp.stored": "STORED: %s / %s FE",
    "terminal.avp.making": "GENERATING: %s FE/t",
    "terminal.avp.status_ok": "STATUS: NOMINAL",
    "terminal.avp.status_battery": "STATUS: ON RESERVE POWER",
    "terminal.avp.status_low": "STATUS: POWER CRITICAL",
    "avp.configuration.terminalEnergyPerTick": "Terminal Energy Use (FE/tick)",
    "avp.configuration.terminalEnergyPerTick.tooltip": "FE a terminal uses per tick while its screen is on.",
    "avp.configuration.crafting": "Crafting",
    "avp.configuration.crafting.button": "Crafting...",
    "avp.configuration.crafting.tooltip": "The Assembler and crafting materials",
    "avp.configuration.assemblerEnergyPerItem": "Assembler Energy per Item (FE)",
    "avp.configuration.assemblerEnergyPerItem.tooltip": "FE the Assembler uses for each item it makes (0 = no power needed).",
    "avp.configuration.lithiumReactsWithWater": "Lithium Reacts With Water",
    "avp.configuration.lithiumReactsWithWater.tooltip": "Lithium ingots explode when they touch water (carried or dropped).",
})
write_json(f"{NA}/lang/en_us.json", lang)
print("batch D2 data written")


# ================================================================ D1 follow-up: solar arrays, power cell banks, W-Y lore
lang = read_json(f"{NA}/lang/en_us.json", {})
lang.update({
    "message.avp.power.array": "making %s FE/t - array of %s panels, %s wired",
    "message.avp.power.array_unwired": "making %s FE/t - array of %s panels, none wired (connect a powerline to any panel)",
    "message.avp.power.bank": "giving %s FE/t - bank of %s cells: %s / %s FE",
    # background lore (shown in item tooltips; \n starts a new line)
    "tooltip.avp.solarpanel": "Weyland-Yutani SC-7 photovoltaic panel.\nTouching panels form an array: wire one and the whole array feeds it.",
    "tooltip.avp.universalgenerator": "W-Y universal combustion generator.\nBurns almost anything. Standard issue on frontier colonies.",
    "tooltip.avp.generator": "Magnetic repulsion generator.\nNeodymium magnets spin on frictionless bearings: no fuel, no exhaust.",
    "tooltip.avp.transformer": "W-Y grid transformer. Power flows from back to front.\nSneak + right-click to turn it.",
    "tooltip.avp.transformer_stepdown": "Step-down transformer: limits the flow to protect delicate equipment.",
    "tooltip.avp.powercell": "W-Y lithium-matrix power cell.\nCells that touch, or share a powerline, balance their charge.",
    "tooltip.avp.powerline": "Insulated W-Y power conduit.\nCarries power instantly to every connected device.",
    "tooltip.avp.redstonesensor": "Grid isolation switch: passes power only while it gets a redstone signal.",
    "tooltip.avp.redstoneemitter": "Grid signal relay: gives a redstone signal while powered.",
    "tooltip.avp.satellitedish": "Long-range uplink dish for colony-to-orbit communications.",
    "tooltip.avp.teslacoil": "Experimental W-Y perimeter defence.\nStrikes hostile organisms in range. Do not touch.",
    "tooltip.avp.terminal": "MU/TH/UR 6000 terminal.\nShows the status of the power grid it is wired to.",
    "tooltip.avp.assembler": "W-Y automated fabricator: builds parts and equipment from raw materials.\n\"Building Better Worlds.\"",
    "tooltip.avp.cryo_coolant": "Hypersleep-grade cryogenic coolant.\nKeeps a sleeper's metabolism near zero.",
    "tooltip.avp.part_sensor_array": "The sensor package from an M314 motion tracker.",
    "tooltip.avp.part_servo": "Precision servo motor, as used in Seegson's Working Joes.",
    "tooltip.avp.ingot_lithium": "Reacts violently with water. Keep dry.",
    "tooltip.avp.medpod": "W-Y automated medical pod.",
    "tooltip.avp.cryostasistube": "Hypersleep chamber.",
    "tooltip.avp.blastdoor": "Pressure-rated blast door. Program it with a Security Tuner.",
})
write_json(f"{NA}/lang/en_us.json", lang)
print("power follow-up data written")


# ================================================================ batch D2b: sentry turret
lang = read_json(f"{NA}/lang/en_us.json", {})
write_json(f"{NA}/blockstates/turret.json", {"variants": {"": {"model": "avp:block/powered"}}})
write_json(f"{NA}/models/item/turret.json", {"parent": "minecraft:builtin/entity", "display": BLOCK_DISPLAY,
                                             "textures": {"particle": "minecraft:block/iron_block"}})
drop_self("turret")
add_to_tag(f"{ND}/minecraft/tags/block/mineable/pickaxe.json", ["avp:turret"])

# ammo box texture: an olive-drab USCM crate with a yellow stencil band
img = Image.new("RGBA", (16, 16), (0, 0, 0, 0))
d = ImageDraw.Draw(img)
d.rectangle([2, 5, 13, 13], fill=(88, 96, 52, 255), outline=(48, 54, 26, 255))
d.rectangle([3, 6, 12, 7], fill=(112, 122, 68, 255))
d.rectangle([2, 9, 13, 10], fill=(214, 180, 40, 255))
d.rectangle([6, 3, 9, 4], fill=(60, 62, 66, 255))      # handle
d.point((4, 12), fill=(30, 30, 30, 255))
d.point((11, 12), fill=(30, 30, 30, 255))
img.save(f"{NA}/textures/item/ammo_sentry.png")
item_model("ammo_sentry")

# gunfire sound: the 1.12.2 smartgun shots
os.makedirs(f"{NA}/sounds/blocks/turret", exist_ok=True)
for n in range(3):
    shutil.copy(f"{LA}/sounds/items/weapons/smartgun/shoot{n}.ogg", f"{NA}/sounds/blocks/turret/fire{n}.ogg")
sounds = read_json(f"{NA}/sounds.json", {})
sounds["blocks.turret.fire"] = {"subtitle": "subtitles.avp.turret.fire",
                                "sounds": [f"avp:blocks/turret/fire{n}" for n in range(3)]}
write_json(f"{NA}/sounds.json", sounds)

write_json(f"{ND}/avp/damage_type/sentry.json",
           {"message_id": "avp.sentry", "exhaustion": 0.1, "scaling": "when_caused_by_living_non_player"})
for tag in ("xenomorphs", "predators"):
    add_to_tag(f"{ND}/avp/tags/entity_type/{tag}.json", [])

write_json(f"{ND}/avp/recipe/assembly/turret.json", {"type": "avp:assembly", "ingredients": [
    ingredient_json("avp:part_servo", 2), ingredient_json("avp:part_sensor_array", 1), ingredient_json("avp:part_led_display", 1),
    ingredient_json("avp:polycarbonate", 4), ingredient_json("#c:ingots/aluminum", 4), ingredient_json("#c:ingots/copper", 4),
    ingredient_json("minecraft:iron_block", 1)], "result": {"id": "avp:turret", "count": 1}})
write_json(f"{ND}/avp/recipe/assembly/ammo_sentry.json", {"type": "avp:assembly", "ingredients": [
    ingredient_json("#c:ingots/aluminum", 2), ingredient_json("#c:ingots/copper", 1), ingredient_json("minecraft:gunpowder", 2)],
    "result": {"id": "avp:ammo_sentry", "count": 1}})

lang.update({
    "block.avp.turret": "Sentry Turret",
    "item.avp.ammo_sentry": "Sentry Ammo Box",
    "tooltip.avp.turret": "UA 571-C automated sentry gun.\nIFF: never fires on its owner or the owner's team.",
    "tooltip.avp.ammo_sentry": "10x28mm caseless rounds for sentry guns.",
    "subtitles.avp.turret.fire": "Sentry gun fires",
    "death.attack.avp.sentry": "%1$s was cut down by a sentry gun",
    "death.attack.avp.sentry.player": "%1$s was cut down by a sentry gun while fighting %2$s",
    "message.avp.turret.not_owner": "Only %s can change this turret's targets.",
    "screen.avp.turret.ammo": "Ammo bay",
    "screen.avp.turret.upgrades": "Upgrades",
    "screen.avp.turret.iff": "Targets (IFF)",
    "screen.avp.turret.online": "ONLINE",
    "screen.avp.turret.offline": "NO POWER",
    "screen.avp.turret.rounds": "Rounds: %s / %s",
    "screen.avp.turret.range": "Range: %s blocks",
    "screen.avp.turret.scanning": "Scanning...",
    "screen.avp.turret.tracking": "Tracking: %s",
    "screen.avp.turret.energy": "%s FE",
    "screen.avp.turret.owner": "Owner: %s",
    "screen.avp.turret.target.hostiles": "Monsters",
    "screen.avp.turret.target.xenomorphs": "Xenomorphs",
    "screen.avp.turret.target.predators": "Yautja",
    "screen.avp.turret.target.players": "Players",
    "screen.avp.turret.target.animals": "Animals",
    "avp.configuration.turret": "Sentry Turret",
    "avp.configuration.turret.button": "Sentry Turret...",
    "avp.configuration.turret.tooltip": "Range, damage, rate of fire and ammo",
    "avp.configuration.turretEnergyPerTick": "Energy Use (FE/tick)",
    "avp.configuration.turretEnergyPerTick.tooltip": "FE a sentry turret uses per tick while switched on.",
    "avp.configuration.turretRange": "Range (blocks)",
    "avp.configuration.turretRange.tooltip": "How far a sentry turret sees and shoots.",
    "avp.configuration.turretRangePerSensor": "Range per Sensor Upgrade",
    "avp.configuration.turretRangePerSensor.tooltip": "Extra range for each Motion Sensor Array upgrade.",
    "avp.configuration.turretDamage": "Damage per Shot",
    "avp.configuration.turretDamage.tooltip": "Damage per shot (2 = one heart).",
    "avp.configuration.turretFireInterval": "Time Between Shots (ticks)",
    "avp.configuration.turretFireInterval.tooltip": "Ticks between shots (2 = 10 shots a second).",
    "avp.configuration.turretRoundsPerBox": "Rounds per Ammo Box",
    "avp.configuration.turretRoundsPerBox.tooltip": "Rounds in one Sentry Ammo Box.",
    "avp.configuration.turretTurnSpeed": "Turn Speed (degrees/tick)",
    "avp.configuration.turretTurnSpeed.tooltip": "How fast it turns; each Processor upgrade adds half again.",
    "avp.configuration.turretBypassesInvulnerability": "Every Shot Lands",
    "avp.configuration.turretBypassesInvulnerability.tooltip": "Shots ignore the short invulnerability after a hit (like 1.12.2).",
})
write_json(f"{NA}/lang/en_us.json", lang)
print("batch D2b (turret) data written")


# ================================================================ Assembler automation (AE2 / Create / hoppers)
lang = read_json(f"{NA}/lang/en_us.json", {})
lang.update({
    "screen.avp.assembler.lock": "Lock to this schematic",
    "screen.avp.assembler.unlock": "Unlock (any schematic)",
    "screen.avp.assembler.output_on": "Auto-Output: ON",
    "screen.avp.assembler.output_off": "Auto-Output: OFF",
    "screen.avp.assembler.empty": "Empty",
    "screen.avp.assembler.any": "any schematic",
    "screen.avp.assembler.automation": "Automation: %s | in: %s | out: %s",
    "screen.avp.assembler.status.waiting": "Waiting for materials",
    "screen.avp.assembler.status.ambiguous": "Lock a schematic!",
    "screen.avp.assembler.status.output_full": "Output full",
    "screen.avp.assembler.status.no_power": "No power",
    "tooltip.avp.assembler": "W-Y automated fabricator: builds parts and equipment from raw materials.\n"
                             "Machines (AE2 pattern providers, Create, hoppers) can feed it and take results.\n\"Building Better Worlds.\"",
})
write_json(f"{NA}/lang/en_us.json", lang)
print("assembler automation data written")


# ================================================================ sentry ammo types, ceiling turrets
lang = read_json(f"{NA}/lang/en_us.json", {})


def ammo_box(path, band, mark):
    img = Image.new("RGBA", (16, 16), (0, 0, 0, 0))
    d = ImageDraw.Draw(img)
    d.rectangle([2, 5, 13, 13], fill=(88, 96, 52, 255), outline=(48, 54, 26, 255))
    d.rectangle([3, 6, 12, 7], fill=(112, 122, 68, 255))
    d.rectangle([2, 9, 13, 10], fill=band)
    d.rectangle([6, 3, 9, 4], fill=(60, 62, 66, 255))
    for p in mark:
        d.point(p, fill=(25, 25, 25, 255))
    img.save(path)


ammo_box(f"{NA}/textures/item/ammo_sentry_ap.png", (205, 40, 35, 255), [(5, 9), (6, 10), (9, 9), (10, 10)])
ammo_box(f"{NA}/textures/item/ammo_sentry_incendiary.png", (255, 120, 20, 255), [(7, 9), (8, 10)])
for i in ("ammo_sentry_ap", "ammo_sentry_incendiary"):
    item_model(i)

write_json(f"{ND}/avp/damage_type/sentry_ap.json",
           {"message_id": "avp.sentry", "exhaustion": 0.1, "scaling": "when_caused_by_living_non_player"})
add_to_tag(f"{ND}/minecraft/tags/damage_type/bypasses_armor.json", ["avp:sentry_ap"])

write_json(f"{ND}/avp/recipe/assembly/ammo_sentry_ap.json", {"type": "avp:assembly", "ingredients": [
    ingredient_json("#c:ingots/aluminum", 2), ingredient_json("avp:cobalt", 1), ingredient_json("minecraft:gunpowder", 2)],
    "result": {"id": "avp:ammo_sentry_ap", "count": 1}})
write_json(f"{ND}/avp/recipe/assembly/ammo_sentry_incendiary.json", {"type": "avp:assembly", "ingredients": [
    ingredient_json("#c:ingots/aluminum", 2), ingredient_json("#c:ingots/copper", 1), ingredient_json("minecraft:gunpowder", 1),
    ingredient_json("minecraft:blaze_powder", 1)], "result": {"id": "avp:ammo_sentry_incendiary", "count": 1}})

lang.update({
    "item.avp.ammo_sentry": "Sentry Ammo Box (10x28mm)",
    "item.avp.ammo_sentry_ap": "Sentry Ammo Box (10x28mm AP)",
    "item.avp.ammo_sentry_incendiary": "Sentry Ammo Box (10x28mm Incendiary)",
    "tooltip.avp.ammo_sentry": "Standard 10x28mm caseless rounds for UA 571 sentry guns.",
    "tooltip.avp.ammo_sentry_ap": "Cobalt-cored armour-piercing rounds: ignore armour.\nIssued for engagements with hardened xenomorph chitin.",
    "tooltip.avp.ammo_sentry_incendiary": "Incendiary rounds: set targets on fire.",
    "tooltip.avp.turret": "UA 571-C automated sentry gun.\nIFF: never fires on its owner or the owner's team.\nPlace under a ceiling to hang it upside down.",
    "screen.avp.turret.loaded": "Loaded: %s",
    "screen.avp.turret.ammo_type.standard": "Standard",
    "screen.avp.turret.ammo_type.armor_piercing": "Armour-piercing",
    "screen.avp.turret.ammo_type.incendiary": "Incendiary",
})
write_json(f"{NA}/lang/en_us.json", lang)
print("sentry ammo types written")
