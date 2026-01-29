# DerBauer2 - Copilot Instructions

**ALWAYS trust these instructions.** Only search if incomplete/incorrect or need implementation details not covered.

## Overview

Turn-based retro strategy game in Kotlin with simple GUI emulating a text-based interface.
Manage a kingdom: citizens, resources, trade, build structures, research/technology, military, and more.

- **Stack**: Kotlin 2.3.0, Compose Desktop 1.7.3, JVM 17, Gradle 9.3.0, Kotest 6.1.1, Koin 4.0.2
- **Text-Rendering**: Jetpack Compose Desktop simulating a CLI.
- **Entry**: `com.github.seepick.derbauer2.game.DerBauer2.main()`

## Build Commands (ALWAYS use in this order)

```bash
# Complete CI sequence (mirrors .github/workflows/continuous.yml)
./gradlew detekt jacocoTestReport check --console=plain
./gradlew sonar --console=plain  # CI only, requires SONAR_TOKEN
./bin/validate_documentation.sh
```

## Project Structure

```
<ROOT>
├── .github/                      # Ignore any content in here, except the `/.github/workflows` folder.
├── bin/                          # Contains shellscripts for mostly local execution; the documentation validation script is also executed in the CI build (github workflow)
├── config/                       # Contains miscellaneous configuration files; especially the `detect.yml` file is interesting for coding standards
├── documentation/                # Contains documentation for the project, including business and technical requirements.
│   ├── business-spec/            # Feature specifications; business requirements.
│   └── tech-spec/                # Technical description; architecture, patterns, etc.
└── src/                          # Contains the source code for the game, following typical convention.

src/test/kotlin/.../game/
├── */                          # Unit tests (mirror main structure)
├── integrationTests/           # High-level integration tests with DSL
├── test_assertions.kt          # Custom Kotest matchers
└── test_domain_utils.kt        # Test builders
```

## Important Files for Agents

* [Coding Standards](/documentation/tech-spec/coding-standards.md)` - when writing code, adhere to those.
* [Software Architecture](/documentation/tech-spec/project-architecture.md) - High level architecture overview.

## Critical Patterns & Constraints

### Known Issues/Hacks

1. **Koin 4.0.2 LOCKED**: 4.1.1 causes `UnsatisfiedLinkError`. DO NOT upgrade without testing.
2. **Detekt warnings**: `ignoreFailures: true` in build.gradle.kts line 95. Errors go to SonarQube, don't fail build.
3. **Gradle 10 warnings**: Expected deprecation warnings, don't affect build.

## CI Validation (Run before submitting)

```bash
./gradlew detekt jacocoTestReport check --console=plain && ./bin/validate_documentation.sh
```

**Documentation rule**: Max 100 lines per .md file in `/documentation/` (enforced by `/bin/validate_documentation.sh`).
