# Todo

## Now

* BZ: documentation
* ! BZ: implement birth rate, increase citizens on turn
* IT: refactor `fun showMainWindow` too long
* BZ: general variability end turn (production, taxes, consumption)
* BZ: immigration happening

## Later

* BZ: material resources (wood, stone, iron, ...), regard when build buildings
* BZ: extend probability provider 
* BZ: improve found-gold-happening: consider history, gold amount (max, current, avg over last x-turns), etc.
* IT: use ULong within Z
* IT could each turn calculation, be an external, plugged-in feature instead?
* UI: global exception handler (e.g. when koin bean definition is missing); show error dialog with exc.message
* UI: bright/dark mode support
* cheat mode (press secret key, increase resources)
* BZ: AD turn citizen: if too little food (not 0 though) for >1 round, slowly starve (currently only starve if 0 food)
* IT: cucumber tests for main flows (super high level) ... or simply use current DSL approach?!
* UI: maybe provide deterministic order of entities/resources (give each a weight? e.g. for turn report)
  * other hand, it might encode some necessary info, maintaining the order; e.g. how it was feature unlocked, chronologically

## Tech

* refactor text-engine: support vert&horizontal alignment of (partial) content; thus delay string creation; pass config-object
* IT: automated releases
* IT: switch jacoco to kover?
* nope IT: register in koin validator interface, and collect all (eager singletons) via DI-context; see transaction/logic.kt