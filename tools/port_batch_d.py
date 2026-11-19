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
