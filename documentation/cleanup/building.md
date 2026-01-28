# Buildings

* about:
  * occupy land
* attributes:
    * costs gold (buy price)
    * uses land
    * pay upcome in gold (each turn)
        - and (maybe) in people ... so people have to be usable entities
    * always can unlimited amount
        - if condition >= 1, then any further have a multiplying effect
        - if building is destroyed afterwards, feature goes (usually!) away; e.g.: castle

* enables:
    * food cap
    * food prod
    * people cap
    * happyness production for people
    * enable features (increase efficiency)
        - at least 1, but also enable for X amount
    * ...
    * some buildings are "multi-areal" (affects several areas, like castle)

* areas:
    * enable new "stuff": action/upgrade/feature
    * economy:
        - resource prod/cap
        - agriculture: food resource only
        - improve trade (more available, quicker regenration, better prices)
    * military:
        - army capacity
        - army effectiveness
    * social: happyness


### Misc

* house: +people cap
* "big house" (nobility quarters): resistince to happenings (fire, flood)
* castle: +people cap, +food cap; enables ThroneRoomVisitors-action
* alchemy: +enable upgrade, -upgrade price
* pub: +happiness
* church: +happiness, +happening luck (as praising the lord helps)
* town hall/center
* monument 
* workshop

## Effects

* resources:
    * food/people cap
    * food/people/gold prod
* military:
    * unit cap
    * att/def mod
    * raid mod
* price mod for build/trade/military/upgrade
* condition/feature/upgrade/action enabled (which itself might enable army...)
* influences happening

# Ideas

* can be destroyed/sold?
* build multiple buildings at once?
* bsp: kaserne, schmiede, universitaet, farm, castle
* building.upkeep += people
    - render overview bar as "2 / 6 / 10", 2 used, 6 living, 10 max cap. (6-2 = 4 available)
    - oder doch nicht, da zu komplex?!? gold upkeep enough?

* wonder buildings (one time enabler)
* gebaeude abreissen: kostet gold
* cost any resources to build
* cost maintenance
* requires/occupies land
* provides:
    * produces resources
    * storage (for resources, army)
* e.g.
    * farm = produce food
    * carnary = store food
