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
- [x] Batch C2b part 1 - Medpod (walk in, heals while closed and powered)
- [x] Batch C2b part 2 - Blast doors (4x3 and Sevastopol 2x3): power, passwords, Security Tuner
      admin screen and binding, Auto-Lock, Maintenance Jack prying; network payloads in org.avp.network
- [ ] Cryostasis tube contents (creature in stasis, cracking when unpowered), temple spawner (with creatures)
- [x] Batch D1 - Power network (first pass, plain FE): powerlines, solar panel, Universal Generator
      (burns fuel), Repulsion Generator (neodymium magnets), transformer + step-down, power cell,
      redstone sensor (powerline switch) / emitter, satellite dish, tesla coil (strikes hostiles)
- [x] Batch D2 - Assembler: 1.12.2 schematics are now data-pack recipes (`data/avp/recipe/assembly/*.json`,
      type `avp:assembly`), built from your inventory, uses FE. Terminal shows its power network.
      New parts: processor, LED display, SSD, lithium ingot (reacts with water) + lore parts:
      servo motor, motion sensor array, hypersleep coolant.
- [x] Batch D2b - Sentry turret (UA 571-C style): FE, Sentry Ammo Boxes (hopper-loadable), upgrades
      (processor = faster turning, sensor array = range, LED display = round counter), IFF target
      categories, owner/team never targeted, laser sight and tracers. Entity tags avp:xenomorphs and
      avp:predators are empty until the creatures are ported.
- [ ] Later: network rack (with the Working Joe network), stasis mechanism (with creatures),
      schematics still waiting for their items (guns, armour, Predator gear - listed by tools/port_batch_d.py)
- [ ] Batch D3 - Make the power system unique and robust (see "Planned: power system" below)
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

## Planned: Engineer ampule (black goo)

Currently decorative (as in 1.12.2). Planned, with the black goo fluid (batch E) and creatures:

- **Black goo source:** right-click with an empty phial to fill it; a few uses before it's empty.
- **Leaking:** breaking or cracking it spills black goo as a fluid.
- **Mutation:** black goo that touches creatures/plants mutates them, Prometheus-style
  (e.g. worms -> Hammerpedes, people -> goo mutants; both creatures exist in 1.12.2).
- **Config:** leak chance, mutation rules, or switch it back to decorative only.

## Planned: medpod surgery and ingredients

- **Ingredients:** the medpod gets a small inventory; items loaded into it are used up during a
  treatment and give effects based on the item and amount (e.g. golden apple -> full golden
  apple boost, glistering melon -> extra healing, honey bottle -> cure poison, milk -> clear
  effects, magma cream -> fire resistance, potions -> their own effects). Data-driven via a
  NeoForge data map (JSON: item -> effect, strength, duration per amount), so packs/datapacks can
  change it. Exposed to hoppers / AE2 / Create for restocking.

- With the creatures: a closed, powered medpod removes a Xenomorph embryo (chestburster) from
  whoever is inside, like the surgery pod in Prometheus; configurable cost/time/success chance.

## Planned: reinforcement (doors and walls)

- **Reinforcement item** (e.g. "Security Reinforcement Kit"): use it on *any* door - vanilla,
  other mods', trapdoors (and possibly walls/any block) - to upgrade it:
  - **Extra durability:** much harder to break; mobs can't break it down (zombies on Hard,
    and later Xenomorphs), resistant to acid and explosions; maybe a "reinforcement health"
    that gets worn down by attacks and can be repaired.
  - **Password lock:** the same terminal as blast doors (Security Tuner admin, binding).
  - **Entity detection:** the blast door sensors - Auto-Open and Threat Detection (lockdown,
    alarm, redstone signal).
  - Shown with a visible plated overlay on the upgraded block.
- **Wall reinforcement device** (earlier idea): a powered device that reinforces walls in an
  area the same way (acid/explosion/break resistant), FE + materials, configurable radius.
- Technical note: vanilla and other mods' blocks can't get our block entity, so the upgrade data
  is stored per position in a NeoForge *data attachment* on the chunk, and events (block break,
  right-click, mob griefing) apply the behaviour.

## Planned: power system (after the first pass)

The first pass (D1) is plain FE so it works with every other mod. Ideas to give it its own
character while staying FE-compatible at the edges:
- **Voltage tiers** (like 1.12.2's volts): low / medium / high lines; transformers step between them;
  machines need the right tier, the wrong one damages them or trips a breaker.
- **Line losses and overloads**: long runs lose a little power; overloaded powerlines spark, arc to
  nearby creatures (the 1.12.2 "electric arc" from bare lines) and can burn out.
- **Breakers / fuses** and a **power monitor** (terminal screen showing each network's load).
- **Grid events**: Xenomorphs cutting power, brownouts that flicker lights, emergency backup cells.
- Tesla coil wirelessly powering CCFL tubes (as in 1.12.2).
- Done already: solar panels that touch form an **array** (unwired panels feed the wired ones);
  **power cells** that touch or share a powerline form a **bank** and balance their charge.
- **Weyland-Yutani technology** to draw on (it's a far-future setting):
  - *Atmosphere processor* (Aliens, Hadley's Hope): huge multiblock fusion plant, endgame power,
    can go critical and explode if damaged or overloaded.
  - *Compact fusion reactor* / *reactor core* from W-Y starships (Nostromo, Sulaco).
  - *Synthetic "blood" / micro-cells* powering androids and Working Joes.
  - Seegson (cheap, low tier, Alien: Isolation) vs Weyland-Yutani (expensive, high tier) parts.
  - W-Y wireless power beacons, colony-wide grid status on MU/TH/UR terminals, "Special Order 937"
    style corporate overrides (admins can lock a grid).

## Planned: Working Joe robots

Seegson "Working Joe" androids you build and set up as a network:
- A control terminal assigns jobs (patrol, guard a door, carry items between storage, repair doors,
  farm, light-switching, alarm response); Joes report back over the network.
- Optional **programming**: a small, safe scripting language typed into a terminal (or loaded from a
  data-pack file) - e.g. `when door "Lab" opens: goto "Lab"; say "Please remain calm"`.
- They need power (charging stations on the power network) and can be "hacked" or go hostile
  (config), like in Alien: Isolation.

## Planned: cloning (like iChun's Sync)

- A **clone vat / shell constructor** grows a copy of you (costs power, materials, maybe biomass).
- A **shell storage** pod keeps clones; step into one to switch bodies (each keeps its own inventory).
- Dying sends you to your nearest stored clone instead of the respawn point (config).
- Ties in with the AvP story (Weyland-Yutani cloning, Ripley 8): imperfect clones, possible
  Xenomorph DNA mixing as an optional hard-mode twist.

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
