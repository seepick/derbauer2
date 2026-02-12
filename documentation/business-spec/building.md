# Building

* assets which can be built (constructed, but basically same semantics as bought/traded).
    * they cost resources (primarily gold, maybe also further building-resources like wood and stone).
    * they occupy land (so land is a resource which can be used up).
* attributes:
    * resources cost
    * maybe pay upcome in gold (each turn)
    * always can unlimited amount
        - if condition >= 1, then any further have a multiplying effect
* buildings can be destroyed/sold?
    * yes, at least they can be reduced: think of a negative happening (natural disaster) or foreign army attacks!
    * if building is destroyed, need to rearrange the loss
        * possible features go away; capacity reduced (surplus resources lost; needs explicit recalculation)
* TODO 🤖 change `costsGold` to a more generic `costs` that can include multiple resources

## Type Areas

* civic: increase happiness/karma
    * increase happiness/karma
    * the more people, the more social buildings required
* economy: affects resources (prod/cap)
    * food cap/prod
    * people cap/prod (reproduction rate, immigrants)
    * gold prod
    * trade benefits
* military: strengthen armies and increase cap
    * unit cap/hire (=buy)
    * enable hire (new types)
* tech: school, university, alchemy
    * gain more research points; faster tech progression
    * unlock certain features with it
* special: feature enabler; e.g.: castle (for throne room)
    * these are one-time enablers of special features/actions/happenings

## Effects

* resources efficiency
    * increase production (food/people/gold)
    * increase capacity (food/people; not gold, it's not a storable entity, thus infinite possible)
* military:
    * army capacity
    * army effectiveness (att/def modifier)
    * raid modifier (getting attacked)
* research:
    * produces research points
* trade:
    * more available, quicker regenration, better prices, ...
* price modifier for all kind of things (buildings/trading/military/technology)
* feature/action enabled (which itself might enable something else...)
* influences happenings
* change overall state (e.g. happiness)
* some buildings are "multi-areal" (affects several areas, like castle)

## Ideas

* citizens are used as workes, and workers are used by buildings
