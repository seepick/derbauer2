# Todo

* introduce buildSrc (try again ;)
* cucumber first
* resolve tech debt
    * mutable internal state; make it immutable, find other solution
    * tests for transaction logic
    * if use `object` (not testable) then document
    * showMainWindow too long
    * split into sub-projects: app, view, logic, textengine
* alternative to sealed interfaces: use enums as properties for each type; exhaustiveness possible (compiler safety);
  doc in some MD file

Jacoco HTML report disabled — poorer developer UX for coverage inspection
Where: build.gradle.kts — tasks.jacocoTestReport { reports { xml.required = true; html.required = false } }
Why: Developers commonly inspect HTML coverage locally; disabling HTML makes quick local inspection harder.
Fix: Enable HTML in local builds or add a -Pci flag to produce XML-only in CI while enabling HTML locally.

Tests may leak DI state between tests — Koin lifecycle not reset in tests
Where: Tests under src/test/kotlin/** (integration tests and unit tests reference Koin modules).
Why: If Koin modules are started and not stopped between tests, tests can be order-dependent and flaky.
Fix: Ensure each test isolates/starts/stops Koin with stopKoin() or use Koin test facilities; add a global test utility
to reset DI between test cases.

## Now

* BZ: documentation
* ! BZ: implement birth rate, increase citizens on turn
* IT: refactor `fun showMainWindow` too long
* BZ: general variability end turn (production, taxes, consumption)
* BZ: immigration happening
* BZ: change designators based on criteria (can be upped and lowered again! different than feature, which is a one-way
  unlock)

## Misc

* **PackageNaming disabled** (detekt.yml:353): TODO pending package standardization.
* **Recomposition hack** (textengine/MainWindow.kt): `tick.toString()` forces Compose recomposition. Do not remove.
* `/documentation/tech-spec/project-architecture.md` - High level architecture overview.

## Release

* Support version number
    * a single number, e.g. `1`
    * stored in a text file `VERSION.txt` in the root directory
* Releases done automatically on specific job execution
    * auto-increment version
* macOs build:
    * better name
    * use icons (dmg and app)
    * show version in app metadata and in "About" dialog
* Windows build

Later:

* Auto-update feature

## Later

* make magnituded number from "1k" to "1.2k"
* BZ: material resources (wood, stone, iron, ...), regard when build buildings
* BZ: extend probability provider
* BZ: improve found-gold-happening: consider history, gold amount (max, current, avg over last x-turns), etc.
* IT: use ULong within Z
* IT could each turn calculation, be an external, plugged-in feature instead?
* UI: global exception handler (e.g. when koin bean definition is missing); show error dialog with exc.message
* UI: bright/dark mode support
* cheat mode (press secret key, increase resources)
* BZ: AD turn citizen: if too little food (not 0 though) for >1 round, slowly starve (currently only starve if 0 food)
* IT: cucumber tests; allow for precise setup of the world (Given part)
* UI: maybe provide deterministic order of entities/resources (give each a weight? e.g. for turn report)
    * other hand, it might encode some necessary info, maintaining the order; e.g. how it was feature unlocked,
      chronologically

## Tech

* refactor text-engine: support vert&horizontal alignment of (partial) content; thus delay string creation; pass
  config-object
* IT: automated releases
* IT: switch jacoco to kover?
* nope IT: register in koin validator interface, and collect all (eager singletons) via DI-context; see
  transaction/logic.kt