# Todo

## 1.2.0

* tech enables Feature; refactor feature (remove enum construct)
* UI: redesign turn report: make it more outstanding
* IT: improve logging: remove unnecessary; add relevant; at least on next turn; short user entities overview
* two open copilot PRs ;)
* trade 5 food at a time

## Next

* UI: ensure order of tech (deterministic, logical); right now "irrigation" puts itself on top (weird)
* BZ: have some sort of SeasonModifier
    * increased rats probability in winter
    * more food production in summer (dynamic resource modifier)
* introduce [knowledge](documentation/business-spec/tech.md#knowledge) asset for Tech

## Backlog

* IT: introduce ActionBus (for history later; skills/XP)
* introduce [stat](documentation/business-spec/stat.md) concept
    * happiness: begin with only season dependent: spring +1, spring +2, autumn 0, winter -1; affects birth rate
    * also decrease a bit if capitalism researched

## Later

* IT/UI: remove SelectPrompt.title; respect single responsibility! (prompt is about prompt; not more!)
* IT/UI: ad Tabled-SelectPrompt: implicit contract that all columns are same length... support variable column count
    * maybe determine max column count, and for the rest do an implicit fill with blank