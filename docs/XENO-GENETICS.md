# Weyland-Yutani Genetics, Queen Containment and the Bio-Reactor

Everything here takes power from the mod's powerline network.

## 1. Sampling
- **Bio-Sampling Syringe**: right-click any living Xenomorph (wild or bonded) to draw a **Xenomorph DNA Sample**. Watch out, because the acid can bite back.
- Killed Xenos drop a sample 15% of the time.
- A sample records its species. Queen DNA ("matriarch") is marked.

## 2. Gene Sequencer (128 FE/t, 15 s)
- Slots: 1 DNA sample and up to 4 splice items.
- It makes a **Xenomorph Embryo Template**. Splices stack, and each one adds instability.

| Trait | Splice item | Effect |
|---|---|---|
| Armour | Iron block | +armour |
| Vitality | Golden apple | +max health |
| Strength | Cobalt | +attack damage |
| Speed | Rabbit's foot | +move speed |
| Size | Royal jelly | +scale |
| Acid | (see tooltip) | acid blood on hit |
| Regeneration | Ghast tear | slow healing |
| Obedience | Processor part | unlocks WORK mode (programmable) |

High instability makes the Xeno more likely to revert to feral. **Obedience** offsets this.

## 3. Incubation Tank (256 FE/t, 60 s)
- Inputs: an embryo template and royal jelly (4, or 16 for a Queen).
- Output: a **bonded** Xeno grows and steps out on top. It belongs to whoever placed the tank.
- A Queen template only grows if a **Queen Containment Cell** sits directly above the tank.

## 4. Bonded Xenos (pets and workers)
- **Right-click empty-handed**: cycle the mode, FOLLOW → STAY → GUARD (→ WORK).
- **Shift + right-click**: show the Xeno's status.
- **Meat or royal jelly**: heals it.
- **Book and quill or written book** (needs Obedience): loads a JOE-SCRIPT program and switches to WORK mode.
- Supported script subset: `set`, `if`/`unless`, `while`/`for`/`repeat`, `func`/`call`, `goto` (home/owner/x y z), `patrol`, `follow`, `guard`, `attack`, `dig`, `pickup`, `take`/`put`, `drop`, `scan`, `find`, `wait`, `say`, `stop`.
- Expression variables: `x`, `y`, `z`, `health`.
- Workers carry 9 slots.
- Bonded Xenos aren't attacked by Joes, marines or other bonded Xenos, and they ignore the hive.

## 5. Queen Containment Cell (512 FE/t, plus meat)
- **Capture**: bring a wild Queen below 30% health within 8 blocks of the cell, then use the cell. She is restrained on top.
- **Grow**: use the Incubation Tank below the cell with a Queen template.
- **Feeding**: put meat in through hoppers, pneumatic capsules or belts (the top input slot).
- **Output**: a fed, powered Queen lays a **contained ovamorph** every 2 minutes. Every third egg also gives 2 royal jelly.
- **Integrity**: falls without power (fastest) or without food. Warnings come at 50%, 25% and 10%. At 0% the Queen **breaks out**.
- Breaking the cell also frees her.
- APOLLO logs the capture as COMPANY PRIORITY.

## 6. Xeno Bio-Reactor (8,192 FE/t)
- Fuel: contained ovamorphs burn for 5 minutes each. Royal jelly burns for 30 seconds.
- Insert fuel by hand, with hoppers or with a Joe.
- **Cooling**: it needs water touching at least 2 faces to stay cool. With 1 face it creeps hotter; with none it heats fast.
- **Hot spent eggs** (above 50% heat) can hatch a facehugger.
- At **100% heat** it scrams and three facehuggers come out.
- A redstone signal pauses refuelling.
- Use Working Joes to handle the eggs. They are immune to facehuggers.

## Config (`avp-server.toml`)
`queenCellPower`, `queenCellEggTicks`, `xenoReactorOutput`.
