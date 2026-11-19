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

lang["avp.configuration.openLockersAreWalkThrough"] = "Open Lockers Are Walk-Through"
lang["avp.configuration.openLockersAreWalkThrough.tooltip"] = "You can step into an open locker to hide inside it, like in 1.12.2."
write_json(f"{NA}/lang/en_us.json", lang)
print("batch C1 data written")
