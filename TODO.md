# Todo

## 1.2.0

* BZ: stat happiness
    * every turn, stat updater decreases happiness by citizen * -0.01
    * theater building increases it again
        * season dependent: spring +1, spring +2, autumn 0, winter -1
    * happening nature changes it
        * affects: birth rate+
        * if capitalism researched, constant -0.x happiness per turn

    * increase if theater building exists
    * decrease more when unfed/starving
    * depend on happening.nature

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
