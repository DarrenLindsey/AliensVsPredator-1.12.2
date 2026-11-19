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
}
AVP_IDS = set(re.findall(r'\("([a-z0-9_]+)"', open("src/main/java/org/avp/registry/ModBlocks.java").read()
                         + open("src/main/java/org/avp/registry/ModItems.java").read()))


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
