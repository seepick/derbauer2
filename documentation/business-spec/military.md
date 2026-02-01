# Military

* basic functionality:
    * army management (buildings/units)
    * warfare (attack/defend)

## Army Management

* use to word "hire" instead of build/buy
* build army units (soldiers, knights)
* costs: gold, people; upcome gold/people/food over time
    * can't be reverted back (?)
* buildings to unlock different unit types (barracks, stable, archery, siege workshop); also act as storage
* have attack/defense values at least (hp? against citizens/army/city?)
* IDEA: concept of heroes
* IDEA: give free stuff/money to army; increase morale => better performance

### Examples

* "angry farmer": cheap, weak; when being attacked and no more army left; if all die -> game over
* soldier: basic unit (barracks)
* knight: +att, +att against wildlings (stable)
* archer: +def (archery)
* catapult: +att against empire
* ram: +att only against buildings/empire, not units
* trap builder: +def, 0 att!
* wizard
* arch mage
* scout: is a (non-army) military unit; to see when the next attack is approaching
* AngryFarmer: if run out of armies, people will fight

## Warfare

* "battle animation" (=countdown) as countdown tickers for both armies
    * the time always stays "roughly" the same, but the speed of the ticker changes depending on army size
    * IDEA: has a fade-out at the end, to make it more dramatic
* weakened for some turns, after military interaction
* IDEAs:
    * attack types (melee, ranged, siege, magic)
    * dedicated military resources (metal, horses, ...)
    * espionage to reveal enemies' stats
    * ?weapon types? (sword, axe, spear, ...)

### Attack

* attack an enemy (gain resources; land, gold; maybe with proper feature enabled take slaves)
    * over time, reward of winning increases (proportional to and % of global max-resources)
* lowers karma (when done too often)
* choose target:
    * barbarians VS NPCs/other countries? display relationship (requires diplomacy)
        * only mid-game able to attack other countries (first mostly/only attack weaker barbarians)
    * leveled: wildlings, village, town, ... city, empire
        * each different target, will have different amount of armies
        * if successfully attacked highest level target, next level target is enabled
* choose how many units to send, and which ones
* gain **loot**
    1. wildlings: +food, +people, -gold, 0 land
    2. empire: avg food, -people, +gold, -land
* when can attacks happen?
    1. during turn as it is now
    2. only at end of turn
    3. send troops during turn, but get outcome (see "animation") at end of turn

### Defend

* if no more armies => people will fight
* when lose (land!) => building destroyed
