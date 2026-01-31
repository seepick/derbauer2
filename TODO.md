# Todo

## Now

* !! finish Technology concept
* tests for transaction logic
* if use `object` (not testable) then document

## Next

* UI: align building list items (like a table, underneath each other; otherwise restless chaos)
* BZ: happening amount with probability range
* BZ: probability (happening) cool-down mechanism to avoid same thing too often
* BZ: implement game over (all citizens dead)
* ! BZ: implement birth rate, increase citizens on turn
* BZ: general variability end turn (production, taxes, consumption)
* BZ: immigration happening
* BZ: change designators based on criteria (can be upped and lowered again! different than feature, which is a one-way
  unlock)
* cheat mode (press secret key, adjust resources, add/remove entities; deeper debug insights)

## Test

* allow for "global config"; set initAssets/custom ones by default applied to all tests within
* beeper uses some event WarningBus; then catch in itests fail-cases as well
* itest for build and (not) enough land
* GameRenderPage ... make reusable also for plain pages (happenings, etc.)

## Misc

* **PackageNaming disabled** (detekt.yml:353): TODO pending package standardization.
* **Recomposition hack** (textengine/MainWindow.kt): `tick.toString()` forces Compose recomposition. Do not remove.
* `/documentation/tech-spec/project-architecture.md` - High level architecture overview.
* BZ: exploration, discover land
* BZ: attack barbarins, or NPCs
* BZ: decisions impact story, influence
* IT: very minimal compose test, so AI can verify workings (right now, i start app manually)
* IT: Use optIn annotation for "secret API"
* IT: refactor ProbabilityInitializer (need to get all instances of a certain type; but in proper order...!)
* IT: if register HappeningDescriptors as bean in koin context, then can get-all via interface lookup; replacing
  HappeningDescriptorRepo :)

## Release

* Support version number
    * a single number, e.g. `1`
    * stored in a text file `VERSION.txt` in the root directory
* Releases done automatically on specific job execution
    * auto-increment version
* Later: Windows build :)
* Later: Auto-update feature

## UI

- bright/dark mode support
- certain events make a sound (happening, etc...)

## Later

* @copilot: music play with memory; auto-play depending on state when application quit; store locally in java
  preferences
* make magnituded number from "1k" to "1.2k"
* BZ: material resources (wood, stone, iron, ...), regard when build buildings
* BZ: extend probability provider
* BZ: improve found-gold-happening: consider history, gold amount (max, current, avg over last x-turns), etc.
* IT: use ULong within Z
* IT could each turn calculation, be an external, plugged-in feature instead?
* UI: global exception handler (e.g. when koin bean definition is missing); show error dialog with exc.message
* BZ: AD turn citizen: if too little food (not 0 though) for >1 round, slowly starve (currently only starve if 0 food)
* UI: maybe provide deterministic order of entities/resources (give each a weight? e.g. for turn report)
    * other hand, it might encode some necessary info, maintaining the order; e.g. how it was feature unlocked,
      chronologically

## Tech

* text-engine: support vert&horizontal alignment of (partial) content; thus delay string creation; pass config-object
* REFACTOR turner: unify happenings + features share both common type; just a sequence of pages (maintain order though)
* REFACTOR ProbabilityProvider: introduce interface for testability

- introduce buildSrc (try again ;)

# Nope

* mutable internal state; make it immutable, find other solution