# Todo

## 1.2.0

* UI: use Textmap.table also for build/research (extend a bit; see todos)

* refactor: TechItem.state MUST not be var, as children are static singleton objects
    * basically global mutable state persisted across test runs... :-/
    * current workaround see: `fun Given() ... state = TechState.Unresearched ...`
* IT: introduce ActionBus (for history later; skills/XP)
* !! BZ: finish tech concept (warfare, junkfood, etc. implement actual effects)
    * can enable Features; have description (for when displaying in turn report)
* UI: redesign turn report: make it more outstanding
* IT: improve logging: remove unnecessary; add relevant; at least on next turn; short user entities overview

## Next

* BZ: have some sort of SeasonModifier
    * increased rats probability in winter
    * more food production in summer (dynamic resource modifier)
* introduce [knowledge](documentation/business-spec/tech.md#knowledge) asset for Tech

## Backlog

* introduce [stat](documentation/business-spec/stat.md) concept
    * happiness: begin with only season dependent: spring +1, spring +2, autumn 0, winter -1; affects birth rate
