# Todo

## 1.3.0

* BZ: interactive happening; being asked Y/N; randomness of the outcome (success; damage; intensity)

## Backlog

* BZ: keep track of user age (independent of turn); initialize with 15 years; become Lord by roughly 20
* UX: replace beeper with playing proper sound (cache short sound snippets); reuse sound player for ActionBus
    * events/interactions make sounds (happening, etc...) for better feedback
    * provide button to toggle sound on/off; improve compose view-model state
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
* IT: custom type SingleLine and MultiLine
* BZ: finish user title (now only Lord); also city title (depend on citizen); maybe stats
