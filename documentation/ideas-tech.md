# 🤖 Tech Ideas

## High

* 🤖 cheat mode (press secret key, adjust resources, add/remove entities; deeper debug insights)
* 🤖 refactor HappeningRef; so can&build is enforced by design (not runtime require check)

## Mid

* 🤖 Auto-update feature (check version; see LSC)
* 🤖 custom type SingleLine and MultiLine
* 🤖 use AOP to log methods with annotation
* 🚦 GameRenderPage ... make reusable also for plain pages (happenings, etc.)

## Low

* 🤖 generate resource declarations, with kotlin poet
* 🤖 use ULong within Z
* 🤖 improve GitHub action: first verify, then build assets (win/mac/linux) with new version already
    * then mutate state: verify assets exist; GIT tag+push; create GitHub release and upload assets (no build anymore)
* 🚦 ad GamePageParser: private val widthHack ==> scan from right to left, until find "   "

# 🎨 UI/UX Ideas

## High

* 🎨 make magnituded number from "1k" to "1.2k"
* 🎨 global exception handler (e.g. when koin bean definition is missing); show error dialog with exc.message
* 🎨 Epoch.colorScheme; ColorScheme.epoch
* 🎨 ad Text-engine: be able to enter numbers (also freetext maybe?)

## Mid

* 🎨 dynamic texts generation; choose from wide range; but make affected by xxx
    * let AI generate some prompts; old-english style, arrrr, bloody hell; irish kingdom, aight?!
* 🎨 in toolbar, info dialog (website URL, version number, build date, random fun message)
* 🎨 change bg music based on event (news, new title/epoch; disaster happening like plague)
* LATER: configure in toolbar which resources to be displayed in info bar (limit to N due to width)

## Low

* 🎨 change size of mainwindow with presets (small/med/big) in toolbar (would be an achievement to support resizable ;)
* 🎨 avoid copyrighted bg music; let AI generate it (and enter greyzone of copyright ;)
    * think of epic medieval games such as: stronghold, age of empires, settlers, ...

## NO

* ad text-engine: support vert&horizontal alignment of (partial) content; thus delay string creation; pass config-object
