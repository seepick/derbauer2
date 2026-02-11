# Developer's Handbook

* Run app in development mode: `-Dderbauer2.devMode=true` (basically cheat mode)
* Run `com.github.seepick.derbauer2.DerBauer2DevApp` in `src/test/kotlin` for full development control
* If macOs quit handler AWT logic causes issues, add JVM arg: `-Dderbauer2.disableMacOsQuitHandler`

## How To Release

* Simply by triggering the [release pipeline](https://github.com/seepick/derbauer2/actions/workflows/release.yml)
    * Click "Run workflow ▼"
    * Choose "Version segment to bump" (major, minor, patch)
    * Click "Run workflow"
* This will build, auto-update the `version.txt`, tag, and create
  a [GitHub release](https://github.com/seepick/derbauer2/releases).

## Gradle Commands

* `./gradlew run` - run the compose app
* `./gradlew detekt` - static code analysis
* `./gradlew dependencyUpdates` - check for newer versions (dependeabot is also configured)
* `./gradlew --refresh-dependencies` - for troubleshooting

## GitHub Workflows

* **Continuous Integration**: on push code; build and verify tests/quality
* **Automatic Release**: manually triggered; version bumping, tagging, GitHub release (uploaded artifacts)
