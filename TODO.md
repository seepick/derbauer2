# Todo

## 1.3.0

* 🖋️refactor UserTitleLordFeature (it's not a feature but something else...)
    * make reporting via a simple `News` at end of chain (not a feature!)
    * the logic underneath is something new; defining thresholds
        * computing how "high level in enum", and dispatch (newly built) news if change detected
* 🖋 description for each building (maybe dynamic effect construction; if is ProduceResource/Storage then ...)
* 🖋 new season dependent happening
* 🎨 use compose viewmodel
* 🎨 sound system :)
    * build/trade/research/next-turn/happenings/features make sounds too
    * replace beeper with playing proper sound (cache short sound snippets); reuse sound player for ActionBus
    * provide button to toggle sound on/off; improve compose view-model state
* 🤖 ad Building: change `costsGold` to a more generic `costs` that can include multiple resources
* 🤖 custom type SingleLine and MultiLine
* draw diagram of game_progress; enhance existing turn step and types

## Backlog

* 🎨 make toolbar height dynamic fitting content (not full height)
* 🤖 classpath scan to counter registry hack; write test; scan and compare with actual registered ones
* 🖋 make poor philospher interactive; being asked Y/N
    * -gold +happiness, basically special trade into stats with consequences
    * !!! requires refactoring of happening rendering and multi-view-page thing...
* 🖋 PoorPhilosopherHappening needs refactoring
* 🖋 keep track of user age (independent of turn); initialize with 15 years; become Lord by roughly 20
* 🖋 tech enables feature (adds to game engine/functionality/action...)
* 🤖 ad ResourceChanges: use a map instead to guarantee uniqueness and make lookups faster
* 🤖 ad TurnReport.featurePages:List<FeatureSubPage> ... execute before, and only add Feature (instead of page)
* 🖋 get ideas/names/concepts from other games; age empire, anno, civ, settlers, fugger, ...
* 🖋 finish user title (now only Lord); also city title (depend on citizen); maybe stats
* 🖋 happenings base values are dynamically calculated (all time max; then low-med-high "packages")
