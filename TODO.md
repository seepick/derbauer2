# Todo

## 1.3.0

* BZ: change city/user titles based on "some stats" (e.g. happiness, population, etc.); display more prominent
* UI: support ESC if in sub-menu
* UI: deterministic order
    * of resources (in turn report; info bar)
    * of tech (logical); right now "irrigation" puts itself on top (weird)
* IT: detekt file max lines
* IT: refactor HasLabels, HasEmoji... shizzle; see: Entity

## Backlog

* BZ: interactive happening; being asked a Y/N question, with some randomness of the outcome (success; damage;
  intensity)
* rename "house" to "tent"
* ? BZ: happiness stat only enabled if researched?
* BZ: have to research seasons; at start only render good old "Turn 1"
* UX: disable being able trading 0
* IT: delete Feature.Discriminator
* IT: ad TurnReport.featurePages:List<FeatureSubPage> ... execute before, and only add Feature (instead of page)
* BZ: ad HappinessDeathPostModifier: look into history, if previous turns also deaths; if so, increase more (with max!)
* IT: refactor happenings/features (and tech?!) into unified "News" concept
* IT: make tech enables Feature
* IT: registry hack: write test which scans classpath and compares with actual registered ones
* IT: ad TradePromptBuilder: outsource construction of "logical view on" trading options into TradingService...
* IT: use AI more; write PRs; e.g. compose color theme
* BZ: get ideas/names/concepts from other games; age empire, anno, civ, settlers, fugger, ...
* UI: use compose viewmodel
* UI: ad text engine, be able to enter numbers (also freetext maybe?)
