# Porting AliensVsPredator to Minecraft 1.21.1 (NeoForge)

This branch (`port/1.21.1`) is a rebuild of the mod on NeoForge 1.21.1.
The original 1.12.2 code is kept in `legacy-1.12.2/` as a reference - it is **not** compiled.

## How to build and test

You need **Java 21** (Gradle can download it for you) and an internet connection the first time.

```
git fetch
git checkout port/1.21.1
./gradlew runClient        # Windows: gradlew.bat runClient
```

The first run downloads Minecraft and NeoForge, which takes a while.
When the game opens, make a Creative world and look for the **AvP:** creative tabs.

To build a jar you can drop into a normal `mods` folder: `./gradlew build` → `build/libs/`.

If something fails, copy the **first** error from the console (or `run/logs/latest.log`).

## Layout

| Path | What it is |
|---|---|
| `src/main/java/org/avp/AliensVsPredator.java` | Mod entry point |
| `src/main/java/org/avp/registry/` | Blocks, items and creative tabs |
| `src/main/resources/assets/avp/` | Textures, models, blockstates, `lang/en_us.json` |
| `src/main/resources/data/` | Loot tables (block drops), tool tags, item tags |
| `src/main/templates/META-INF/neoforge.mods.toml` | Mod info shown in the Mods screen (filled in from `gradle.properties`) |
| `tools/port_simple.py` | Script that converted the simple blocks/items from `legacy-1.12.2/` |
| `PORTING-skipped.txt` | Simple-looking items the script couldn't convert (3D-rendered or special) |
| `PORTING-skipped-recipes.txt` | Old recipes waiting for an item that isn't ported yet |

## Progress

- [x] Step 1 - Project skeleton that builds and loads
- [x] Step 2a - Simple decorative blocks (73) and simple items/foods (28)
- [x] Step 2b - Stairs (5), slabs (5), ores with special drops (silicon, monazite, cobalt)
- [x] Step 2c - Recipes: 45 crafting + 9 smelting/blasting/smoking/campfire (`tools/port_step2.py`)
- [ ] Step 2d - Remaining special blocks (hive resin, skulls, striped wall, black goo fluid, lithium ingot)
- [ ] Ore generation in the world (ores exist but don't spawn yet)
- [ ] Step 3 - One creature end to end (template for the rest)
- [ ] Step 4 - Other creatures, guns, armour, tile entities
- [ ] Step 5 - Dimensions (Varda, Acheron) and world generation

Registry names changed from `shippanel.foo` style to `shippanel_foo` (modern convention).
Worlds from 1.12.2 can't be loaded in 1.21.1 anyway, so this doesn't break anything.

## If the NeoForge version can't be found

`neo_version` in `gradle.properties` is set to `21.1.77`. If Gradle says it can't find it,
or you just want the newest fixes, change it to the latest `21.1.x` listed at
https://projects.neoforged.net/neoforged/neoforge
