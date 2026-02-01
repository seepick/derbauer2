# Todo

## Versions

### v5

* release semver like; input which to auto-increment: major, minor, patch
* !! BZ: finish Technology concept
* ! BZ: implement birth rate, increase citizens on turn
* BZ: implement citizens death if no food
    * BZ: AD turn citizen: if too little food (not 0 though) for >1 round, slowly starve (currently only starve if 0
      food)
* BZ: general variability end turn (production, taxes, consumption)
* BZ: implement game over (all citizens dead)
* IT: macos app signing
* BZ: market adjustment: when selling too much, price drops; recovers over time to baseline

### v6

* UI: align building list items (like a table, underneath each other; otherwise restless chaos)

## Backlog

* BZ: change (user/city) titles/designators based on criteria
    * can be upped and lowered again! different than feature, which is a one-way unlock
* cheat mode (press secret key, adjust resources, add/remove entities; deeper debug insights)
* BZ: immigration happening
* BZ: decisions impact story, influence
* BZ: probability (happening) cool-down mechanism to avoid same thing too often
* BZ: happening amount with probability range
* `/documentation/tech-spec/project-architecture.md` - High level architecture overview.
* UI: make magnituded number from "1k" to "1.2k"
* BZ: material resources (wood, stone, iron, ...), regard when build buildings
* BZ: extend probability provider
* BZ: improve found-gold-happening: consider history, gold amount (max, current, avg over last x-turns), etc.
* IT: jar file for linux&co

- BZ: attack barbarins, or NPCs

### Tech

* ad github workflow: check-quality-gates also does jacoco verify/enforce threshold
* ad github workflow: check-quality-gates valid doc runs independent from previous step, so both can fail
* GitHub REFACTOR necessary (deprecated functionality used)
    * see: https://github.blog/changelog/2022-10-11-github-actions-deprecating-save-state-and-set-output-commands/
* REFACTOR ProbabilityInitializer (need to get all instances of a certain type; but in proper order...!)
* register HappeningDescriptors as koin beans, get-all via interface lookup; replacing HappeningDescriptorRepo :)
* Use @OptIn annotation for "secret API"
* use ULong within Z
* could each turn calculation be an external/plugged-in feature instead?
* text-engine: support vert&horizontal alignment of (partial) content; thus delay string creation; pass config-object
* REFACTOR turner: unify happenings + features share both common type; just a sequence of pages (maintain order though)
* REFACTOR ProbabilityProvider: introduce interface for testability

- introduce buildSrc (try again ;)

* TEST for transaction logic
* TEST allow for "global config"; set initAssets/custom ones by default applied to all tests within
* TEST beeper uses some event WarningBus; then catch in itests fail-cases as well
* TEST itest for build and (not) enough land
* TEST GameRenderPage ... make reusable also for plain pages (happenings, etc.)
* Later: Windows build :)
* Later: Auto-update feature

### UI

* UI: global exception handler (e.g. when koin bean definition is missing); show error dialog with exc.message
* UI: maybe provide deterministic order of entities/resources (give each a weight? e.g. for turn report)
    * or, it might encode some necessary info, maintaining order; e.g. how it was feature unlocked, chronologically

- UI: bright/dark mode support
- UI: certain events make a sound (happening, etc...)
