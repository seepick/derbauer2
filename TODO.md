# Todo

## 1.2.0

* BZ: introduce [knowledge](documentation/business-spec/tech.md#knowledge) asset for Tech
* IT: introduce ActionBus + extract Reports from User => centralize History

## Backlog

* introduce [stat](documentation/business-spec/stat.md) concept
    * happiness: begin with only season dependent: spring +1, spring +2, autumn 0, winter -1; affects birth rate
    * also decrease a bit if capitalism researched
* BZ: have some sort of SeasonModifier
    * increased rats probability in winter
    * more food production in summer (dynamic resource modifier)
* BZ: new feature: trade 5 food at a time; triggered by having traded enough food (tracking interactions)
* IT: tech enables Feature; refactor feature (remove enum construct)
* UI: ensure order of tech (deterministic, logical); right now "irrigation" puts itself on top (weird)
* "unsloppify" website
