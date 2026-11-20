# JOE-SCRIPT v2 — Seegson Working Joe programming reference

A program is plain text, one statement per line, written in a **book and quill** (use it on the Joe),
the **Seegson Terminal** editor, or a data-pack file `data/<namespace>/joe_programs/<name>.joe`.
Comments start with `#` or `//`. A program runs top to bottom and starts over at the end
(unless it reaches `stop`).

## Places
`X Y Z` coordinates. `~` means "where the Joe stood when the program was loaded": `~ ~ ~5` is 5 blocks
south of that. `$var` puts a variable in: `goto $fx $fy $fz`.

## Settings
| statement | meaning |
|---|---|
| `name Joe-Lab` | the Joe's name |
| `home X Y Z` | its home (`goto home`, recall) |
| `load avp:farmer` | run a data-pack program instead |

## Variables and expressions
```
set n = 5
set n = n * 2 + 1
set label = "Wheat: " + count(wheat)
```
Values are numbers, text and true/false. Operators: `+ - * / %`, `== != < > <= >=`, `and or not`, `( )`.
Text joins with `+`. Unknown variables are 0.

**Built-in values:** `x y z charge health time night day raining lowpower full empty items slots alarm hurt
hostile player security name homex homey homez ownerx ownery ownerz found fx fy fz block solid target moved crafted msg sender channel pi`

**Built-in functions:** `count(item)`, `items(X,Y,Z[,item])` (container contents), `block(X,Y,Z)`, `isair(X,Y,Z)`,
`ripe(X,Y,Z)`, `powered(X,Y,Z)`, `dist(X,Y,Z)`, `light(X,Y,Z)`, `upgrade(id)`, `joes()`, `random(n)`, `chance(pct)`,
`abs floor ceil round sqrt min max sin cos`, `str num len upper lower contains(s,t) word(s,i) var(name)`.

## Blocks
```
if count(iron_ingot) >= 16          while not full          repeat 4
  ...                                 ...                     ...
elif night                          end                     end
  ...
else                                for i = 1 to 10 step 2  forever
  ...                                 ...                     ...
end                                 end                     end
```
`break` / `continue` inside loops. One-line forms: `if COND: statement`, `unless COND: statement`.
Old-style conditions still work: `night`, `has wheat 10`, `powered X Y Z`, `chance 20`.

## Functions
```
func restock
  take ~2 ~ ~ iron_ingot 16
  if moved == 0: report "Out of iron"
  return
end
call restock
```
Up to 32 nested calls. `label NAME` / `jump NAME` also work.

## Movement
`goto X Y Z` · `goto home|alarm|owner` · `patrol A ; B ; C` · `face X Y Z` · `follow [secs]` ·
`guard X Y Z [radius] [secs]` · `wait SECS` · `charge`

## Hands (like a ComputerCraft turtle)
| statement | does |
|---|---|
| `dig X Y Z` | breaks a block, keeps the drops (drill module: 5x faster, mines anything) |
| `place ITEM X Y Z` | places a block from its inventory |
| `use X Y Z [ITEM]` | right-clicks a block (with an item): bone meal, buttons, buckets... |
| `take X Y Z [ITEM] [N]` | takes from a container (sets `moved`) |
| `put X Y Z [ITEM] [N]` | puts into a container (no item = everything) |
| `drop [ITEM] [N]` | drops items in front of it (onto a conveyor, say) |
| `pickup [radius]` | collects loose items |
| `equip ITEM` / `equip nothing` | holds a tool or weapon |
| `craft ITEM [N]` | crafts from its inventory (fabricator module) |
| `attack [radius]` | fights the nearest hostile |
| `haul A -> B [ITEM] [N]` | carries items between containers |
| `farm A B [-> STORE]` | harvests and replants ripe crops in a box |
| `maintain A B [<- STORE]` | puts back missing powerlines and lights |
| `lever X Y Z on/off`, `press`, `open`, `close` | switches and doors |

## Senses
`scan BLOCK [radius]` (block id or `#tag`) and `find hostile|player|animal|joe|xeno|item|ENTITY [radius]` set
`found`, `fx fy fz` (and `target`). `inspect X Y Z` sets `block` and `solid`. The radius is capped at 12, or 48
with a sensor module.

## Talking and the network
`say "text"` (people nearby) · `report "text"` (owner, over the network) · `print "text"` (terminal log) ·
`alarm` · `security on|off` · `send CHANNEL "text"` (every Joe you own, any dimension).

## Responses
`when EVENT: statement` runs whenever the event happens, then the program carries on.
`when EVENT: call NAME` runs a whole function. Events: `night day alarm hurt lowpower hostile player`,
`redstone X Y Z`, `message [CHANNEL]` (sets `msg`, `sender`, `channel`).

## Modules (4 bays)
cargo (+9 slots, x2) · battery (+100%, x2) · drill · fabricator · sensor · plating · servos · anchor (chunk loading).
Use a module on the Joe to fit it; sneak-use shears to take them all out.

## Example: an iron miner
```
name Joe-Miner
home ~ ~ ~
when lowpower: charge
forever
  scan iron_ore 24
  if not found
    report "No more iron nearby."
    stop
  end
  dig $fx $fy $fz
  pickup 4
  if full
    goto home
    put ~2 ~ ~
  end
end
```

## The Seegson Terminal, Network Server and Field Tablet
- **Seegson Terminal** (computer, 4 FE/t): amber CRT console. Tabs: **STATUS** (unit details, battery, variables;
  recall / stop / resume / security / clear alarm / rename), **PROGRAM** (editor: fetch a unit's program, check syntax,
  upload to one or all units, save to the library), **LIBRARY** (your saved programs plus the built-in `[ROM]` ones:
  open, run, run on all, delete), **DRIVE** (live top-down radar with blips, and remote control: WASD to walk,
  Q dig, E use, F attack, X stop, plus dig up/down, pick up, drop, place), **LOG** (the unit's `print` output, errors
  and reports; send messages to one unit or all on a channel).
- Without a server a terminal reaches your Joes within 64 blocks, in the same dimension.
- **Seegson Network Server** (64 FE/t, keeps its own chunk loaded): while it's powered your terminals and tablet
  reach **every Joe you own, at any distance and in any dimension**, and you get the program library (64 files).
- **Seegson Field Tablet**: the terminal in your hand, anywhere (needs your server online).
- Pair with the **chunk anchor** module so far-away Joes keep running.

## Conveyors
Conveyor Belt, Express Conveyor Belt and Sorting Conveyor Belt carry items, mobs and players. At the end of a belt,
items go into whatever container is there. Hoppers, pipes and Joes (`put` / `drop`) can load a belt. A redstone signal
stops it. Use an item on a sorting belt to set its filter: matching items go off to the left, the rest straight on
(sneak-use with an empty hand to clear).

## Built-in programs (`/joes run avp:NAME`, or the terminal's LIBRARY)
farmer, patrol, security, maintenance, courier, lights, escort, miner, assembler, dispatcher.

## APOLLO (station AI storage)
The **APOLLO Core** stores items as a manifest (counts, not slots): 16,384 items and 64 types, plus that much again for
every **APOLLO Data Bank** joined to it (touching it, or touching another bank). It needs power (32 FE/t).
- Hoppers, pipes, PneuPost and Joes (`put`) can put anything into it.
- It's a PneuPost station called **APOLLO**, so canisters can deliver to it, and it can **dispatch** down the tubes.
- In a Seegson Terminal's **APOLLO** tab: search the manifest, then **TO ME** (a courier Joe brings it to you, wherever
  you are) or **DISPATCH** to a named PneuPost station.
- `courier` (JOE-SCRIPT): take one delivery job from APOLLO and carry it to the player who asked.
  `store`: take everything it carries to APOLLO. See the built-in `avp:apollo_courier` program.
- Company policy: specimens (eggs, facehuggers, embryos, royal jelly) go in, but never come out. Special Order 937.

## Weyland-Yutani ore refining (multiplies ore up to x8)
| machine | from ore | power | needs | also takes |
|---|---|---|---|---|
| Ore Crusher | 2 crushed ore | 24 FE/t | - | washed ore -> crushed (and cobble -> gravel -> sand) |
| Hydro-Washer | 3 washed ore | 48 FE/t | water (touching it, buckets, pipes) | shards -> washed |
| Acid Leaching Vat | 4 leached shards | 96 FE/t | Xenomorph acid | crystals -> shards |
| Electrolytic Refinery | 6 refined crystals | 384 FE/t | - (may sit on high voltage) | - |
| Nostromo-class Fusion Refinery | 8 refined crystals | 1,536 FE/t | - (may sit on high voltage) | - |
Crushed ore smelts (or blasts) to an ingot. Works for iron, gold, copper, aluminium, lithium, cobalt, neodymium and silicon.
Xenomorph acid: use an empty phial on a Xenomorph to draw a vial (careful - it splashes; chitin armour protects you).

## Seegson PneuPost (pneumatic tubes)
Glass **PneuPost Tubes** link **Stations** (named: name the item in an anvil, or rename in its screen) and **Compressors**
(16 FE/t each; more compressors, faster canisters, up to 4). Put items in a station's OUTGOING row and click a destination
to send a canister - you can watch it shoot through the glass. "A" sets a station to auto-send there (when a stack fills,
every few seconds, or on a redstone pulse). Hoppers/pipes/Joes put into OUTGOING and take from INCOMING. If a tube on the
way is broken (or melted), the canister bursts out there and spills.

## Seegson Flow (visual programming)
The terminal's **FLOW** tab shows a program as blocks, one per line of JOE-SCRIPT:
- Pick a category (MOVE, WORK, SENSE, TALK, LOGIC, EVENT) and click a block to add it after the selected one.
  Blocks that open a section (IF, WHILE, REPEAT, FOREVER, FOR, FUNCTION) come with their END; nesting is shown by indent.
- Click a block to edit its fields underneath; **@** fills a position with where the selected Joe stands.
- **UP / DN / DUP / DEL** rearrange; **UPLOAD** sends it to the selected Joe; the block it's running glows.
- **TO CODE / FROM CODE** convert to and from the text editor. Nothing is lost: a line it has no block for is kept as a
  grey CODE block.

## Weyland-Yutani Seismic Drill
Build a 3x3 platform of **Seismic Drill Casing** and put the **Seismic Drill** on its centre. Power it (400 FE per block,
times hardness; it may sit on high voltage). In its panel: **START/STOP**, area **- / +** (up to 33x33), **MODE**
(QUARRY digs everything; ORE-BORE pulls only ore and backfills with stone, for 3x the power), **SURVEY** (a seismic count
of the six commonest ores below), **R** to reset. It works top-down to the bottom of the world and pushes its haul into
any container beside it - a PneuPost station or the APOLLO core make good ones - or holds 27 slots itself.

## Power Loader
Use the crate on the ground; right-click to climb in; sneak-click to open its 27-slot hold; sneak-click with a
maintenance jack to crate it again. Runs on a battery (charge it at a Joe Charger or feed it lithium ingots).
Inside: you mine 4x faster, 3x3 at a time, and everything goes into the hold; empty-handed attacks are 16-damage loader
punches; empty-handed right-click on a block lifts it (chests keep their contents) and right-click again sets it down.

## Smart belts
Every belt carries items, mobs and players; at the end of a line items go into whatever container is there; hoppers,
pipes and Joes can put items on a belt; redstone stops a belt. The smart belts (each crafted from a Conveyor Belt):

| belt | does |
|---|---|
| Sorting Belt | items matching its filter (use an item on it) go left |
| **Logic Belt** | runs a **BeltScript** for every item (right-click to edit) - route it left/right/straight/back, or store it; keeps variables; drives a redstone signal |
| **Processing Belt** | holds each item in the middle and SMELTs, BLASTs, CRUSHes or WASHes it (right-click to change); 16 FE/t |
| **Crafting Belt** | set a 3x3 recipe (right-click); it takes the ingredients that pass (up to 4 crafts' worth), crafts, and sends the result on; everything else rides past |
| **Storage Belt** | keeps what arrives (27 slots x 1,024); a redstone signal releases it a stack at a time; hoppers/Joes can take from it |
| **Extractor Belt** | pulls items out of the container behind it onto the line (use an item on it to filter) |

### BeltScript
```
# runs top to bottom for every item; the first route wins
set n = n + 1                 # variables keep their values
if tag("c:ores"): left        # item tags
if is("cobblestone"): store   # item ids (minecraft: optional)
if count >= 32: right
if contains(name, "Diamond"): back
if n % 3 == 0: left           # every third item
unless redstone: straight
signal n % 16                 # redstone output 0-15
straight                      # default
```
Values: `item` (id), `name`, `count`, `n` (items seen), `redstone`, `time`, your variables.
Functions: `is(id)`, `tag(t)`, `contains(a, b)`, `random(n)`, `chance(pct)`, `min max abs floor`.
Routes: `left right straight back store`.

## Spline lines: belts, power cables and hoses
Place two anchors, then use a spool on the first and then on the second; the line strings itself between them
(up to 48 blocks; one spool per 8 blocks; sneak-use a spool on an anchor to take its line down).
- **Belt Anchor + Belt Spool / Express Belt Spool**: a curved belt that bends, climbs and drops. Items ride it (rendered
  on the belt) and queue up when the far end is full. Feed an anchor with hoppers, block belts, pipes or Joes; a belt can
  end at another anchor or at any belt block (including the smart belts). An anchor with no belt of its own hands items to
  the block it faces (a belt or a container), or drops them there.
- **Weyland-Yutani Cable Pylon + Power Cable Spool**: hanging power cables. A pylon is part of the power network, and a
  span joins the networks at both ends, so you can run power across valleys. Voltage tiers, overloads and breakers apply as
  usual, and Xenomorphs will tear pylons down.
- **Seegson Hose Anchor + Hose Spool**: hanging hoses (250 mB/t). The anchor a hose leaves from pumps from the block behind
  it (a tank, a machine, or a water source - endless); the anchor it ends at fills the block behind it. The hose takes the
  colour of what's in it.
