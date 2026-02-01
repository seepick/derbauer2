# Developer's Handbook

## Quicklinks

* SonarQube: https://sonarcloud.io/summary/new_code?id=seepick_derbauer2

## How To Release

* Simply by triggering the [release pipeline](https://github.com/seepick/derbauer2/actions/workflows/release.yml) and
  selecting the main branch, click, wait, done.
* It will auto-update the version number, package the app, tag it, and create
  a [GitHub release](https://github.com/seepick/derbauer2/releases) for all OSes.

## Gradle Commands

* `./gradlew run` - run the compose app locally
* `./gradlew dependencyUpdates` - check for newer versions (dependeabot is also configured)
* `./gradlew detekt` - static code analysis

## GitHub Workflows

* Workflow types:
    * Continuous Integration: on each push, verifying quality
    * Release: manually triggered (version bumping/tagging, GitHub release, uploaded artifacts)
* PS: parse string into json: from `"['ubuntu-latest']"` to: `${{ fromJSON(inputs.runner) }}`
