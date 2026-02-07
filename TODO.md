# Todo

## 1.2.0

* BZ: stat
    * every turn, stat updater decreases happiness by citizen * -0.01
    * special building increases it again, e.g.: theater
    * season dependent: spring +1, spring +2, autumn 0, winter -1
    * affects: birth rate+
    * if capitalism researched, constant -0.x happiness per turn
* IT: delete Feature discriminator shizzle

## 1.3.0

* IT: refactor happenings/features (and tech?!) into unified "News" concept
* IT: refactor HasLabels, HasEmoji... shizzle; see: Entity

## Backlog

* change city/user titles as (enum) stat?
* BZ: more food production in summer+autumn (dynamic resource modifier)
* BZ: new feature: trade 5 food at a time; triggered by having traded enough food (tracking interactions)
* IT: tech enables Feature; refactor feature (remove enum construct)
* UI: ensure order of tech (deterministic, logical); right now "irrigation" puts itself on top (weird)
