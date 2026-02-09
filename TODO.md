# Todo

## 1.3.0

* BZ: more food production in summer+autumn (dynamic resource modifier)
* BZ: new feature: trade 5 food at a time; triggered by having traded enough food (tracking interactions)
* BZ: change city/user titles based on "some stats" (e.g. happiness, population, etc.); display more prominent
* UI: support ESC if in sub-menu
* UI: deterministic order
    * of resources (in turn report; info bar)
    * of tech (logical); right now "irrigation" puts itself on top (weird)
* IT: detekt file max lines
* IT: refactor HasLabels, HasEmoji... shizzle; see: Entity
* IT: koin test missing bean

## Backlog

* BZ: ad HappinessDeathPostModifier: look into history, if previous turns also deaths; if so, increase more (with max!)
* IT: delete Feature discriminator enum shizzle -or? useful e.g. to assign mechanics-values to specific and exhaustive!
* IT: refactor happenings/features (and tech?!) into unified "News" concept
* IT: make tech enables Feature
