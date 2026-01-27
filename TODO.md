# Todo

==> FOCUS: test properly; fix all major bugs; release <==

## Now

* resolve TODOs/FIXMEs
* BZ: citizens borth

## Next

* ? could each turn calculation, be an external, plugged-in feature instead?
* UI: global exception handler (e.g. when koin bean definition is missing); show error dialog with exc.message
* IT: cucumber tests for main flows (super high level)
* BZ: citizens: houses, birth, food consumption, gold taxes
* BZ: general variability end turn (production, taxes, consumption)
* BZ: material resources (wood, stone, iron, ...), regard when build buildings
* UI: predictable order of entities/resources (give each a weight??)
* UI: bright/dark mode support
* BZ: implement birth rate, increase citizens on turn

## Later

* cheat mode (press secret key, increase resources)
* UI: sound: continuous bg music; press key beep; each age different theme?!
  * how to configure? overlay menu?!
* UI: font styling: <red> <green> <bold> etc.
  * like ctrl-codes in shell; process in renderer and remember positions and style for compose annotations
* BZ: immigration happening

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
