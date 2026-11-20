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
