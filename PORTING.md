# Porting AliensVsPredator to Minecraft 1.20.1 (Forge)

This branch (`port/1.20.1`) is a rebuild of the mod on Forge 1.20.1.
The original 1.12.2 code is kept in `legacy-1.12.2/` as a reference - it is **not** compiled.

## How to build and test

You need **Java 17** (Gradle can download it for you) and an internet connection the first time.

```
git fetch
git checkout port/1.20.1
./gradlew runClient        # Windows: gradlew.bat runClient
```

The first run downloads Minecraft, Forge and the mappings, which takes a while.
When the game opens, make a Creative world and look for the **AvP:** creative tabs.

To build a jar you can drop into a normal `mods` folder: `./gradlew build` → `build/libs/`.

If something fails, copy the **first** error from the console (or `run/logs/latest.log`).

## Layout

| Path | What it is |
|---|---|
| `src/main/java/org/avp/AliensVsPredator.java` | Mod entry point |
| `src/main/java/org/avp/registry/` | Blocks, items and creative tabs |
| `src/main/resources/assets/avp/` | Textures, models, blockstates, `lang/en_us.json` |
| `src/main/resources/data/` | Loot tables (block drops) and tool tags |
| `tools/port_simple.py` | Script that converted the simple blocks/items from `legacy-1.12.2/` |
| `PORTING-skipped.txt` | Simple-looking items the script couldn't convert (3D-rendered or special) |

## Progress

- [x] Step 1 - Project skeleton that builds and loads
- [x] Step 2a - Simple decorative blocks (73) and simple items/foods (28)
- [ ] Step 2b - Special blocks (stairs, slabs, ores with drops, resin, skulls, fluids)
- [ ] Step 2c - Recipes
- [ ] Step 3 - One creature end to end (template for the rest)
- [ ] Step 4 - Other creatures, guns, armour, tile entities
- [ ] Step 5 - Dimensions (Varda, Acheron) and world generation

Registry names changed from `shippanel.foo` style to `shippanel_foo` (1.20 convention).
Worlds from 1.12.2 can't be loaded in 1.20.1 anyway, so this doesn't break anything.
