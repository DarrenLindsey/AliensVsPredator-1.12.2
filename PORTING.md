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
- [x] Batch A - Natural blocks: dirt, grass, logs, leaves, tendons, sapling, stalagmite, wheat grass
- [x] Batch B - Decorative 3D blocks: skulls, biomasks, heads, ferns, slopes/corners/ridges/pyramids
      (+ `tools/convert_models.py`, which converts all 99 old box models)
- [x] Batch C1 - Lockers, gun locker, supply crates (64-slot menus, hopper/AE2/Create item access)
- [x] Batch C2a - Light panel, fluorescent light (CCFL), cryostasis tube, ampule; FE power + redstone
- [ ] Batch C2b - Medpod, blast doors
- [ ] Cryostasis tube contents (creature in stasis, cracking when unpowered), temple spawner (with creatures)
- [ ] Batch D - Power system (FE) and machines
- [ ] Batch E - Black goo / mist fluids, Varda / Acheron portals
- [ ] Hive resin, supply crate parachute drop (with the creatures/entities)
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

## Planned: creature sizes

Decided for the creatures phase (1 block = 1 metre, player = 1.8 blocks):

- **Creature Size** setting with two modes:
  - *Classic* - the 1.12.2 hitboxes (e.g. Drone 1.8 tall, so it fits through 2-block doors).
  - *Film-accurate* - sizes from the films (Drone/Warrior 2.1-2.4 tall and 4.3-4.9 long with tail,
    Queen ~4.3-4.5, Facehugger ~1.05-1.2 long, Chestburster ~0.25 tall / ~1 long,
    Runner ~1.2-1.4 crouched, Neomorph 2.4-2.7, Predator 2.2-2.4, Engineer ~2.4).
  - Plus a per-creature fine-tune slider.
- Tall xenomorphs can **crouch** to get through 2-block gaps (as in the films), so film-accurate
  sizes don't break chasing indoors.
- Skull/trophy sizes get revisited together with the creatures so they match each other.

## Planned: new machines

- **Stasis harvesting (cryostasis tube):** capture a mob with a capture item, load it into a
  powered tube; it's shown frozen inside and periodically rolls the mob's loot table (no kill
  needed) into an internal inventory that hoppers / AE2 / Create can empty. Losing power cracks
  and then shatters the glass and releases the mob (the 1.12.2 behaviour, now a risk to manage).
- **Growth Acceleration Tube** (variant): holds a seed/sapling/crop, grows it and rolls its
  harvest drops on a timer.
- Config: FE per harvest, time between harvests, mob allow/deny lists (e.g. no bosses),
  whether hostile creatures can break out.

## Planned: AI ideas

- **Fear system**: players (and maybe NPC marines) get a fear level from darkness, nearby
  Xenomorphs, motion tracker pings, being hunted, etc., with effects like a heartbeat sound,
  screen shake/vignette, shaky aim; hiding in a locker lowers it slowly. Configurable.
- Players hiding in a locker (riding a `LockerSeatEntity`) can't be seen by Xenomorphs/Predators;
  maybe they can still be heard/smelled at close range, or found by Predator thermal vision.

## Planned: Create and Applied Energistics 2 integration

Both support NeoForge 1.21.1. Two levels:

1. **Built in from the start (Batch D machines):** machines, lockers and generators use NeoForge's
   standard item / fluid / energy (FE) capabilities instead of the old custom power system, so AE2
   buses/interfaces and Create funnels/arms can move items and power in and out with no extra code.
2. **Optional integration module** (only active when the other mod is installed, toggled in the config):
   - Create: crushing/mixing/pressing recipes for AvP ores and materials, maybe a rotation-powered
     machine, Ponder scenes.
   - AE2: inscriber/charger recipes for AvP electronics (processors, RAM, circuit boards).
   Both are declared as *optional* dependencies in `neoforge.mods.toml`.

## Settings (config)

All settings are in `src/main/java/org/avp/config/AvpConfig.java` and can be changed in game:
**Mods → AliensVsPredator → Config**.

- **Client** settings (`config/avp-client.toml`) only change what you see.
- **World** settings (`saves/<world>/serverconfig/avp-server.toml`) change gameplay; a server's
  settings are sent to everyone who joins.

Every new feature should add its options here (see the comment at the top of `AvpConfig`).

## About MDXLib

The 1.12.2 mod needed MDXLib. It is **not** included: it has no open-source license and its
author asks that it isn't packaged with other mods, and it only works on 1.12.2 anyway. Instead,
the port has its own code that does the same jobs (e.g. `tools/convert_models.py` and
`org.avp.client.render`), using MDXLib's source only as a reference for how things behaved.
