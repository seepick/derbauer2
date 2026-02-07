# Todo

## 1.2.0

* IT: introduce ActionBus + accumulate in TurnReport (turn scope); reset after next turn

## Backlog

* IT: refactor HasLabels, HasEmoji... see: Entity
* introduce [stat](documentation/business-spec/stat.md) concept
    * happiness: begin with only season dependent: spring +1, spring +2, autumn 0, winter -1; affects birth rate
    * also decrease a bit if capitalism researched
    * change city/user titles as (enum) stat
* IT: refactor happenings/features (and tech?!) into unified "News" concept
* BZ: more food production in summer+autumn (dynamic resource modifier)
* BZ: new feature: trade 5 food at a time; triggered by having traded enough food (tracking interactions)
* IT: tech enables Feature; refactor feature (remove enum construct)
* UI: ensure order of tech (deterministic, logical); right now "irrigation" puts itself on top (weird)
* "unsloppify" website
