# Tech Ideas

* kotlin poet to generate resource declarations
* refactor HappeningDescriptor; so can&build is enforced by design (not runtime require check)
* macos app signing
* Use @OptIn annotation for "secret API"
* use ULong within Z
* refactor turner: unify happenings + features share both common type; just a sequence of pages (maintain order though)
* text-engine: support vert&horizontal alignment of (partial) content; thus delay string creation; pass config-object
* change size of mainwindow with presets (small/med/big) in toolbar (would be an achievement to support resizable ;)
* global exception handler (e.g. when koin bean definition is missing); show error dialog with exc.message
* improve GitHub action: first verify, then build assets (win/mac/linux) with new version already
    * then mutate state: verify assets exist; GIT tag+push; create GitHub release and upload assets (no build anymore)

Low:

* val User.`🍖` get(): Food = findResource(Food::class)
* use compose viewmodel, proper state management (audio player); but more to come in the future
    * marry together with koin, thus core logic and UI can interact properly
* ? maybe Percent can be any Double (not limited to 0..1)?!
* GitHub REFACTOR necessary (deprecated functionality used)
    * see: https://github.blog/changelog/2022-10-11-github-actions-deprecating-save-state-and-set-output-commands/
* Auto-update feature
* could provide a UserReadOnly sub-interface
* use AOP to log methods with annotation
* text-to-speech :)

* `/documentation/tech-spec/project-architecture.md` - High level architecture overview.
* introduce buildSrc (try again ;) version toml buildSrc shizzle...
* provide a `UserReadOnly` interface (for reduced visibility/more stability through immutability)
* version check plugin manes: compose macos 1.9.0 gets through and warned about; disable check

## Test

* add ITest.autoPrintPage dev feature
* for transaction logic
* ad GamePageParser: private val widthHack ==> scan from right to left, until find "   "
* allow for "global config"; set initAssets/custom ones by default applied to all tests within
* beeper uses some event WarningBus; then catch in itests fail-cases as well
* itest for build and (not) enough land
* GameRenderPage ... make reusable also for plain pages (happenings, etc.)
* ad ResourceTurnerTest
    * two distinct buildings producing food Then sum
    * resource producing entity, which is not ownable/count; eg not a building but a feature or anything

## UI

* ad text engine, be able to enter numbers (also freetext maybe?)
* replace beeper with playing proper sound (cache short sound snippets); reuse sound player for ActionBus
    * events/interactions make sounds (happening, etc...) for better feedback
* make magnituded number from "1k" to "1.2k"
* choose color theme (circular button in toolbar, dropdown with a few common presets)
* let AI generate some prompts; old-english style, arrrr, bloody hell; irish kingdom, aight?!
* configure in toolbar which resources to be displayed in info bar (limit to N due to width)
* avoid copyrighted bg music; let AI generate it (and enter greyzone of copyright ;)
    * think of epic medieval games such as: stronghold, age of empires, settlers, ...
* bright/dark mode support
