# DerBauer2 - Copilot Instructions

**ALWAYS trust these instructions.** Only search if incomplete/incorrect or need implementation details not covered.

## Overview

Turn-based retro strategy game in Kotlin with simple GUI emulating a text-based interface.
Manage a kingdom: citizens, resources, trade, build structures, research/technology, military, and more.

- **Stack**: Kotlin 2.3.0, Compose Desktop 1.7.3, JVM 17, Gradle 9.3.0, Kotest 6.1.1, Koin 4.0.2
- **Text-Rendering**: Jetpack Compose Desktop simulating a CLI.
- **Entry**: `com.github.seepick.derbauer2.game.DerBauer2.main()`

## Project Structure

```
<ROOT>
├── .github/                      # Ignore any content in here, except the `/.github/workflows` folder.
├── bin/                          # Contains shellscripts for mostly local execution; the documentation validation script is also executed in the CI build (github workflow)
├── config/                       # Contains miscellaneous configuration files; especially the `detect.yml` file is interesting for coding standards
├── documentation/                # Contains documentation for the project, including business and technical requirements.
│   ├── ai-prompts/               # Disregard those when working on the project; used prompts for previous AI tasks.
│   ├── business-spec/            # Feature specifications; business requirements.
│   └── tech-spec/                # Technical description; architecture, patterns, etc.
└── src/                          # Contains the source code for the game, following typical convention.
```

### Important Files for Agents

* [Coding Standards](/documentation/tech-spec/coding-standards.md)` - When writing any code, adhere to those.
* [Software Architecture](/documentation/tech-spec/software-architecture.md) - High level architecture overview.
* [Gradle Dependencies](/gradle/libs.versions.toml) - Change dependencies if needed.

## Critical Patterns & Constraints

## AI Specific Coding Rules

* Whenever you write a new class or function, always add the `@com.github.seepick.derbauer2.game.core.AiGenerated`
  annotation to it (ensure using import statements).
* Write **short functions**, ideally 2-5 lines. If longer, split into smaller functions and give them descriptive names.
* Do NOT write any **inline comments**; the code should be self-explanatory. If you need to explain something, write a
  separate function with a descriptive name.
* Make use of **trailing commas** in multiline function calls and declarations for better readability and easier diffs.

### Known Issues/Hacks

1. **Koin 4.0.2 LOCKED**: 4.1.1 causes `UnsatisfiedLinkError`. DO NOT upgrade without testing.
2. **Detekt warnings**: `ignoreFailures: true` in build.gradle.kts line 95. Errors go to SonarQube, don't fail build.
3. **Gradle 10 warnings**: Expected deprecation warnings, don't affect build.

## CI Validation (Run before submitting)

Run the following checks to verify your work is done and ready for submission:

* Run all tests including UI headless tests:
  `xvfb-run -a -s "-screen 0 1280x1024x24" ./gradlew test uiTest -PenableUiTests --console=plain`
* Validate code quality `./gradlew detekt -PfailOnDetektIssue --console=plain`
* In case you modified files in the `/documentation/` folder, also run: `/bin/validate_documentation.sh`. This will
  ensure that no markdown file is longer than 100 lines.

Ignore any already existing issues which were not caused by you.
