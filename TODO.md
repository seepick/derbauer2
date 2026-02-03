# Todo

Continuous Cleanup: 1) TODOs 2) Detekt+Sonar 3) DepVersions

## Versions

### Current

* BUG!!! eat + birth clash; both try to adjust population/food; need to sequence properly
* BZ: growth diffuser for: birth, starvation (already for taxes, eat)
* break up MainWindow() god function into smaller pieces
* make the whole window bigger

### Next

* !! BZ: finish tech concept (warfare, junkfood, etc. implement actual effects)
    * can enable Features; have description (for when displaying in turn report)
* UI: events/interactions make sounds (happening, etc...) for better feedback
    * first introduce ActionBus (for history later; skills/XP)
* UI: redesign turn report: make it more outstanding

## Backlog

* IT: runUiTest needs proper implementation
    * disabled by default; enableable via CI
    * locally: can run easily (selectively); no interference with IDE test execution though (2x test execution mode)

### Finish Existing Game Concepts

* BZ: rat happening: either { lil=5%, med=10%, big=20% } of food loss; with min/max caps
* BZ: happening amount with probability range

### New Game Concepts

* BZ: game renderer, resource info bar, add happy indicator: "12/20 🙎🏻‍♂️☹️"
* BZ: increase designators (title, city) based on "some criteria"
    * can be upped and lowered again! different than feature, which is a one-way unlock
* BZ: new chronos: instead of "turn", do it "Week 13 Year 1052"
* BZ: immigration happening

### Misc

* cheat mode (press secret key, adjust resources, add/remove entities; deeper debug insights)
* BZ: probability (happening) cool-down mechanism to avoid same thing too often
* IT: refactor Entity :HasLabel (not :HasLabels). only Ownable have :HasLabels.
* UI: let AI generate some prompts; old-english style, arrrr, bloody hell; irish kingdom, aight?!
* UI: align building list items (like a table, underneath each other; otherwise restless chaos)
* BZ: market adjustment: when selling too much, price drops; recovers over time to baseline

* -- BZ: FeatureTurner
    * TX-exec one, could later allow another to check==true
    * thus: do rounds of applying, until no feature returns check==true anymore
    * e.g.: TradingFeature enables TradeLandFeature; should BOTH appear in turn screens
*
    - BZ: once stats are implemented, then let junkfood-tech also increase happiness
*
    - val User.`🍖` get(): Food = findResource(Food::class)
*
    - let AI generate doc, based on code (before that, align documents with package structure)
*
    - BZ: ad TradingFeature check logic: look for turns played (and/or money owned?)
*
    - BZ: reverse engineer DerBauer1
*
    - BZ: decisions impact story, influence
* BZ: improve found-gold-happening: consider history, gold amount (max, current, avg over last x-turns), etc.
* IT: jar file for linux&co
* BZ: AD turn citizen: if too little food (not 0 though) for >1 round, slowly starve
    * currently only starve if 0 food

- BZ: attack barbarians, or NPCs

*
    - UI: make magnituded number from "1k" to "1.2k"

### Tech

* macos app signing
* Use @OptIn annotation for "secret API"
* use ULong within Z
* REFACTOR turner: unify happenings + features share both common type; just a sequence of pages (maintain order though)
* UI text-engine: support vert&horizontal alignment of (partial) content; thus delay string creation; pass config-object
* TEST for transaction logic
* TEST ad GamePageParser: private val widthHack ==> scan from right to left, until find "   "
* TEST allow for "global config"; set initAssets/custom ones by default applied to all tests within
* TEST beeper uses some event WarningBus; then catch in itests fail-cases as well
* TEST itest for build and (not) enough land
* TEST GameRenderPage ... make reusable also for plain pages (happenings, etc.)
* ? maybe Percent can be any Double (not limited to 0..1)?!
*
    - GitHub REFACTOR necessary (deprecated functionality used)
        * see: https://github.blog/changelog/2022-10-11-github-actions-deprecating-save-state-and-set-output-commands/
*
    - Auto-update feature
*
    - could provide a UserReadOnly sub-interface
* -- use AOP to log methods with annotation
* TEST: ResourceTurnerTest
    * two distinct buildings producing food Then sum
    * resource producing entity, which is not ownable/count; eg not a building but a feature or anything
*
    - `/documentation/tech-spec/project-architecture.md` - High level architecture overview.
* --- introduce buildSrc (try again ;) version toml buildSrc shizzle...
* -- provide a `UserReadOnly` interface (for reduced visibility/more stability through immutability)

### UI

* UI: global exception handler (e.g. when koin bean definition is missing); show error dialog with exc.message
* UI: maybe provide deterministic order of entities/resources (give each a weight? e.g. for turn report)
    * or, it might encode some necessary info, maintaining order; e.g. how it was feature unlocked, chronologically

- UI: bright/dark mode support
