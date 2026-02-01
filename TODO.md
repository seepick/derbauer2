# Todo

Continuous Cleanup:

* Resolve Detekt issues
* Resolve SonarQube issues
* Upgrade dependency versions
* Increase test coverage

## Versions

### new ideas

### v5

* ! BZ: implement birth rate, increase citizens on turn
* BZ: trade is not enabled at startup, first needs... gold become < 10, or so...
* !! BZ: finish Technology concept

Refactor window:

* make the whole window bigger
* BUG: width with emojis is wrong... implement spike
    * render X*Y raster-matrix, each char a separate UI-component to draw into, like a regular table (?)

### v6

* UI: align building list items (like a table, underneath each other; otherwise restless chaos)

## Backlog

* BZ: general variability end turn (production, taxes, consumption)
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
* BZ: AD turn citizen: if too little food (not 0 though) for >1 round, slowly starve
    * currently only starve if 0 food

- BZ: attack barbarians, or NPCs

* BZ: market adjustment: when selling too much, price drops; recovers over time to baseline

### Tech

* could provide a UserReadOnly sub-interface
* macos app signing
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
* --- introduce buildSrc (try again ;)
* TEST for transaction logic
* TEST allow for "global config"; set initAssets/custom ones by default applied to all tests within
* TEST beeper uses some event WarningBus; then catch in itests fail-cases as well
* TEST itest for build and (not) enough land
* TEST GameRenderPage ... make reusable also for plain pages (happenings, etc.)
* Later: Windows build :)
* Later: Auto-update feature
* TEST: ResourceTurnerTest
    * two distinct buildings producing food Then sum
    * resource producing entity, which is not ownable/count; eg not a building but a feature or anything

### UI

* UI: global exception handler (e.g. when koin bean definition is missing); show error dialog with exc.message
* UI: maybe provide deterministic order of entities/resources (give each a weight? e.g. for turn report)
    * or, it might encode some necessary info, maintaining order; e.g. how it was feature unlocked, chronologically

- UI: bright/dark mode support
- UI: certain events make a sound (happening, etc...)
