# Todo

## 1.2.0

* BZ: stat happiness
    * season dependent: spring +1, spring +2, autumn 0, winter -1
        * happening nature changes it
    * affects: birth rate+
    * if capitalism researched, constant -0.x happiness per turn
        * decrease more when unfed/starving
    * write TESTS for all of it
* IT: delete Feature discriminator shizzle

## Backlog

* BZ: more food production in summer+autumn (dynamic resource modifier)
* UI: ensure order of tech (deterministic, logical); right now "irrigation" puts itself on top (weird)
* IT: refactor happenings/features (and tech?!) into unified "News" concept
* IT: refactor HasLabels, HasEmoji... shizzle; see: Entity
* BZ: new feature: trade 5 food at a time; triggered by having traded enough food (tracking interactions)
* IT: tech enables Feature; refactor feature (remove enum construct)
* BZ: change city/user titles based on "some stats" (e.g. happiness, population, etc.)
