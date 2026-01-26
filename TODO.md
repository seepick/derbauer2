# Todo

==> FOCUS: test properly; fix all major bugs; release <==

## Now

* !! change SPACE to ENTER for back&continue
* resolve TODOs/FIXMEs
* encapsulate resource mutations behind safe APIs with validation and bounded math
  * resource can go negative (safeguard in data class itself!); centralize
  * duplicated/max-storage
* document mechanics as "magic constant provider"
* handle starvation (test first)
* BZ: citizens: houses, birth, food consumption, gold taxes

## Next

* BZ: general variability end turn (production, taxes, consumption)
* BZ: material resources (wood, stone, iron, ...), regard when build buildings
* UI: predictable order of entities/resources (give each a weight??)
* UI: bright/dark mode support

## Later

* cheat mode (press secret key, increase resources)
* sound: continuous bg music; press key beep; each age different theme?!
  * how to configure? overlay menu?!
* font styling: <red> <green> <bold> etc.
  * like ctrl-codes in shell; process in renderer and remember positions and style for compose annotations

## Tech

* refactor text-engine: support vert&horizontal alignment of (partial) content; thus delay string creation; pass config-object
* IT: automated releases
* IT: detekt
* IT: sonarqube cloud
* IT: coverage

## Ideas

* UI: overlay info notifier/window/toast for InteractionResult.Failure (or keep it simple?)
* UI: colored text? (build annotated string necessary for compose text...)
* UI: set icon in Dock (iconFile in build.gradle)
* UI: set icon of window
