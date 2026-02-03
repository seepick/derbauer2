# Todo

Continuous Cleanup: 1) TODOs 2) Detekt+Sonar 3) DepVersions

## Versions

### Current

* !! BZ: finish Technology concept
    * tech tree; let AI generate print tree statement
    * tech can enable Features
    * tech can have description
    * IT: remove enum-pseudo-sealed again, and introduce repo; the DefaultRepo holds a manual list; so what...
        * otherwise not testable; too hard-wired with enum.entries; no real "dynamicism"
    * refactor irrigation to a lvl II agriculture upgrade
    * pottery, enables granaries
    * houses, upgrade from tents; replaces building? then auto-upgrade necessary (keep UI items low as ascend)
    * finish 3 tech modifiers

* BUG: when 9/10 food, and produce way more, then should be +1 but was 0!!!
* BUG: leftover food not eaten at the end; citizens should start to starve

#### Window Refactoring

* first break up MainWindow() god function into smaller pieces
* make the whole window bigger
* BUG: width with emojis is wrong... implement spike
    * render X*Y raster-matrix, each char a separate UI-component to draw into, like a regular table (?)
    * HomePageTest disabled emoji width calc issue
    * TextmapTest disabled emojis take up 2 chars, thus calc is off...

### Next

* BZ: general variability end turn (production, taxes, consumption)
    * use java.util.Random().nextGaussian(mean, standardDeviation)
* IT: refactor Entity :HasLabel (not :HasLabels). only Ownable have :HasLabels.
* game renderer, resource info bar, add happy indicator: "12/20 🙎🏻‍♂️☹️"
* let AI generate doc, based on code (before that, align documents with package structure)

## Backlog

* BZ: once stats are implemented, then let junkfood-tech also increase happiness
* IT: could make TechState a proper state pattern implementation
* BZ: FeatureTurner
    * TX-exec one, could later allow another to check==true
    * thus: do rounds of applying, until no feature returns check==true anymore
    * e.g.: TradingFeature enables TradeLandFeature; should BOTH appear in turn screens
* IT: runUiTest needs proper implementation
    * disabled by default; enableable via CI
    * locally: can run easily (selectively); no interference with IDE test execution though (2x test execution mode)
* BZ: ad TradingFeature check logic: look for turns played (and/or money owned?)
* BZ: reverse engineer DerBauer1
* BZ: increase designators (title, city) based on "some criteria"
* BZ: new chronos: instead of "turn", do it "Week 13 Year 1052"
* UI: let AI generate some prompts; old-english style, arrrr, bloody hell; irish kingdom, aight?!
* UI: align building list items (like a table, underneath each other; otherwise restless chaos)
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

* refactor ProducesResourceTurnStep#calcResourceChanges (too big/complex)
* refactor MainWindow (too big/complex)
* -- use AOP to log methods with annotation
* maybe Percent can be any Double (not limited to 0..1)?!
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
* TEST ad GamePageParser: private val widthHack ==> scan from right to left, until find "   "
* TEST allow for "global config"; set initAssets/custom ones by default applied to all tests within
* TEST beeper uses some event WarningBus; then catch in itests fail-cases as well
* TEST itest for build and (not) enough land
* TEST GameRenderPage ... make reusable also for plain pages (happenings, etc.)
* Later: Windows build :)
* Later: Auto-update feature
* TEST: ResourceTurnerTest
    * two distinct buildings producing food Then sum
    * resource producing entity, which is not ownable/count; eg not a building but a feature or anything
* -- provide a `UserReadOnly` interface (for reduced visibility/more stability through immutability)

### UI

* UI: global exception handler (e.g. when koin bean definition is missing); show error dialog with exc.message
* UI: maybe provide deterministic order of entities/resources (give each a weight? e.g. for turn report)
    * or, it might encode some necessary info, maintaining order; e.g. how it was feature unlocked, chronologically

- UI: bright/dark mode support
- UI: certain events make a sound (happening, etc...)
