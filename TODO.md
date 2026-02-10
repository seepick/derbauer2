# Todo

// use AI more; write PRs; e.g. compose color theme

## 1.3.0

* BZ: interactive happening; being asked Y/N; randomness of the outcome (success; damage; intensity)
* UX: remember window position
* UX: disable being able trading 0
* IT: delete Feature.Discriminator

## Backlog

* IT: use classpath scan, to get all Resource impls, and print in declared order
* ? BZ: happiness stat only enabled if researched?
* BZ: have to research seasons; at start only render good old "Turn 1"
* IT: ad TurnReport.featurePages:List<FeatureSubPage> ... execute before, and only add Feature (instead of page)
* BZ: ad HappinessDeathPostModifier: look into history, if previous turns also deaths; if so, increase more (with max!)
* IT: refactor happenings/features (and tech?!) into unified "News" concept
* BZ: make tech enables Feature
* IT: ad TradePromptBuilder: outsource construction of "logical view on" trading options into TradingService...
* BZ: get ideas/names/concepts from other games; age empire, anno, civ, settlers, fugger, ...
* UI: use compose viewmodel
* minorIT: refactor deterministic order of Tech; use tree to do it properly (not manually registry)
* BZ: change city/user titles based on "some stats" (e.g. happiness, population, etc.); display more prominent
