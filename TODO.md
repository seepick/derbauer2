# Todo

## 1.3.0

* IT: delete Feature.Discriminator
* BZ: interactive happening; being asked Y/N; randomness of the outcome (success; damage; intensity)
* UX: disable being able trading 0

## Backlog

* BZ: tech enables feature, (adds to game engine/functionality/action...)
* BZ: new season dependent happening
* IT: ad Building: change `costsGold` to a more generic `costs` that can include multiple resources
* IT: ad ResourceChanges: use a map instead to guarantee uniqueness and make lookups faster
* IT: use classpath scan, to get all Resource impls, and print in declared order
* ? BZ: happiness stat only enabled if researched?
* IT: ad TurnReport.featurePages:List<FeatureSubPage> ... execute before, and only add Feature (instead of page)
* BZ: ad HappinessDeathPostModifier: look into history, if previous turns also deaths; if so, increase more (with max!)
* IT: refactor happenings/features (and tech?!) into unified "News" concept
* IT: ad TradePromptBuilder: outsource construction of "logical view on" trading options into TradingService...
* BZ: get ideas/names/concepts from other games; age empire, anno, civ, settlers, fugger, ...
* UI: use compose viewmodel
* minorIT: refactor deterministic order of Tech; use tree to do it properly (not manually registry)
* BZ: change city/user titles based on "some stats" (e.g. happiness, population, etc.); display more prominent
* IT: custom type SingleLine and MultiLine
