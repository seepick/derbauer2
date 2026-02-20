# Todo

## Todo Requirements

* fugger2 get names from sources (handbook, wiki, websites)
* reverse engineer DerBauer1

* incorporate inspirations into business-spec (mark files on top with `✅ INCORPORATED ✅`)
* reflect on what do i like about games A) in general B) use specific examples to help thoughts
* then cleanup every MD file

* create DIAGRAMs
    * domain concepts; vertical ordering; e.g. happening inside turner => reflect in code packages
    * epochs; roadmap, timeline; with milestones, features, ...
        * enhance existing turn step and types
* let AI generate doc, based on code (before that, align documents with package structure)
* create epoch milestone project plan

## Howto Release

1. **refactor** code
2. run: `./bin/validate_all.sh`
3. resolve **sonarqube** issues
4. smoke tests: `./bin/run_distributable.sh`
5. execute github [auto-release](https://github.com/seepick/derbauer2/actions/workflows/release.yml)

# Versions

## 1.3.0

* 🖋️ refactor UserTitleLordFeature (it's not a feature but something else...)
    * make reporting via a simple `News` at end of chain (not a feature!)
    * the logic underneath is something new; defining thresholds
        * computing how "high level in enum", and dispatch (newly built) news if change detected
* 🖋 dynamicInfo for Entity; e.g. building produce/store, but GENERIC!
* 🤖 ad ResourceChanges: use a map instead to guarantee uniqueness and make lookups faster
* 🤖 when interact toolbar, then dozens "MainWindow - UI render tick #1" logs... suppress them
* 🤖 Use @OptIn annotation for "secret API"
* 🤖 version check plugin manes: compose macos 1.9.0 gets through and warned about; disable check

## Unsorted

* 🖋 display each tech what it enables (dynamically calculated)
* 🤖 ad classpath scanning: ignore all from src/test/kotlin (instead relying on annotation)
* 🤖 ad KClass<*>.emojiHack: a design mistake, that: to get the emoji of a given resource, need `user` reference
    * to get the real instance, instead of fiddling around with kotlin KClass shizzle...
* 🤖 macos signed app (suppress security warning)
* 🤖 verify runs smoothly on win/linux as well
