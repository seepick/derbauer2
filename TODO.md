# Todo

## 1.2.0

* refactor: TechItem.state MUST not be var, as children are static singleton objects
    * basically global mutable state persisted across test runs... :-/
    * current workaround see: `fun Given() ... state = TechState.Unresearched ...`
* IT: introduce ActionBus (for history later; skills/XP)
* UI: align building list items (like a table, underneath each other; reusable for build/research esp turn report)
* BZ: have some sort of SeasonModifier
    * increased rats probability in winter
    * more food production in summer (dynamic resource modifier)
* !! BZ: finish tech concept (warfare, junkfood, etc. implement actual effects)
    * can enable Features; have description (for when displaying in turn report)
* UI: redesign turn report: make it more outstanding

## 1.3.0
