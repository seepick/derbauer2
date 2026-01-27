# Todo

==> FOCUS: test properly; fix all major bugs; release <==

## Now

* resolve TODOs/FIXMEs
* BZ: citizens borth
* MCP sonarqube cloud integration
* IT: use ULong within Z

## Next

* ? could each turn calculation, be an external, plugged-in feature instead?
* UI: global exception handler (e.g. when koin bean definition is missing); show error dialog with exc.message
* instead `user.add(House(10.z))` enforce `user.tx(..)` to go through validations (e.g. sufficient gold/land); test can by-pass 
* BZ: citizens: houses, birth, food consumption, gold taxes
* BZ: general variability end turn (production, taxes, consumption)
* BZ: material resources (wood, stone, iron, ...), regard when build buildings
* UI: predictable order of entities/resources (give each a weight??)
* UI: bright/dark mode support
* BZ: implement birth rate, increase citizens on turn

## Later

* BZ: extend probability provider: use a cooldown mechanism; linear/exp/log chance 
* BZ: improve found-gold-happening: consider history, gold amount (max, current, avg over last x-turns), etc.
* cheat mode (press secret key, increase resources)
* UI: sound: continuous bg music; press key beep; each age different theme?!
  * how to configure? overlay menu?!
* UI: font styling: <red> <green> <bold> etc.
  * like ctrl-codes in shell; process in renderer and remember positions and style for compose annotations
* BZ: immigration happening
* UI: dynamic window size, adjust so it correlates with textmap size (font measure size needed)

## Tech

* refactor text-engine: support vert&horizontal alignment of (partial) content; thus delay string creation; pass config-object
* IT: automated releases
* IT: detekt
* IT: sonarqube cloud
* IT: coverage

## Ideas

* AD turn citizen: if too little food (not 0 though) for >1 round, slowly starve (currently only starve if 0 food)
* UI: overlay info notifier/window/toast for InteractionResult.Failure (or keep it simple?)
* UI: colored text? (build annotated string necessary for compose text...)
* UI: set icon in Dock (iconFile in build.gradle)
* UI: set icon of window
* IT: cucumber tests for main flows (super high level) ... or simply use current DSL approach?!
* UI: maybe provide deterministic order of entities/resources (give each a weight? e.g. for turn report)
