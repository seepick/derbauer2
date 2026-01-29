# DerBauer2 - Copilot Instructions

## Overview
Turn-based retro strategy game in Kotlin + Jetpack Compose Desktop. Manage resources, trade, build structures, expand territory.
- **Size**: ~4.5MB, 99 Kotlin files
- **Stack**: Kotlin 2.3.0, Compose Desktop 1.7.3, JVM 17, Gradle 9.3.0, Kotest 6.1.1, Koin 4.0.2
- **Entry**: `com.github.seepick.derbauer2.game.DerBauer2.main()` (not the `Main` reference in build.gradle.kts line 62)

## Build Commands (ALWAYS use in this order)

```bash
# Complete CI sequence (mirrors .github/workflows/continuous.yml)
./gradlew detekt jacocoTestReport check --console=plain
./gradlew sonar --console=plain  # CI only, requires SONAR_TOKEN
./bin/validate_documentation.sh

# Individual commands
./gradlew clean                  # Clean (<5s)
./gradlew test                   # Run tests (20-40s, uses Kotest/JUnit Platform)
./gradlew detekt                 # Lint (10-20s, config: src/main/config/detekt.yml)
./gradlew check                  # Full verification (40-60s)
./gradlew run                    # Run application
./gradlew build --offline        # Build offline if dependencies cached

# First build: 2-5min to download dependencies from Maven Central, dl.google.com, JetBrains repos
```

**CRITICAL**: First build requires internet. If dl.google.com unreachable, build fails with "No address associated with hostname" - no workaround except network access or `--offline` mode with cached deps.

## Project Structure

```
src/main/kotlin/com/github/seepick/derbauer2/
├── game/                        # Core game logic (domain-driven modules)
│   ├── building/               # Structures: House, Farm, Granary
│   ├── citizen/                # Population management
│   ├── core/                   # Domain models: User, Mechanics, Emoji
│   ├── resource/               # Gold, Food, Land, Citizens
│   ├── transaction/            # Immutable state change pattern (TxResource, execTx)
│   ├── turn/                   # Turn-based game loop
│   ├── happening/              # Random events
│   ├── trading/                # Trade system
│   ├── feature/                # Feature unlocks
│   ├── technology/             # Tech tree
│   ├── view/                   # UI pages (Compose)
│   └── common/                 # Utilities
└── textengine/                  # Text-based UI framework

src/test/kotlin/.../game/
├── */                          # Unit tests (mirror main structure)
├── integrationTests/           # High-level integration tests with DSL
├── test_assertions.kt          # Custom Kotest matchers
└── test_domain_utils.kt        # Test builders

src/main/config/detekt.yml      # 783 lines, maxLineLength:120, LongMethod:50, ReturnCount:2
src/main/resources/logback.xml  # Logging: ALL for derbauer2 package, INFO for root
build.gradle.kts                # Main build config, Jacoco XML reports (for SonarQube)
.github/workflows/continuous.yml # CI: checkout depth:0 (SonarQube needs blame), cache Gradle+Sonar
```

## Critical Patterns & Constraints

### Transaction Pattern (MUST FOLLOW)
All resource changes use immutable transaction pattern:
```kotlin
import com.github.seepick.derbauer2.game.resource.TxResource
import com.github.seepick.derbauer2.game.transaction.execTx

val newUser = execTx(
    TxResource(Gold::class, amount.asZz),
    TxResource(Land::class, cost.asZz)
).errorOnFail().user
```

### Testing Pattern (Kotest DescribeSpec)
```kotlin
class MyTest : DescribeSpec({
    var user = User()
    beforeTest { user = User() }  // Fresh state each test
    describe("feature") {
        it("does something") { /* test */ }
    }
})
```

### Known Issues/Hacks
1. **Koin 4.0.2 LOCKED**: 4.1.1 causes `UnsatisfiedLinkError`. DO NOT upgrade without testing.
2. **Recomposition hack** (textengine/MainWindow.kt): `tick.toString()` forces Compose recomposition. Do not remove.
3. **Detekt warnings**: `ignoreFailures: true` in build.gradle.kts line 95. Errors go to SonarQube, don't fail build.
4. **Gradle 10 warnings**: Expected deprecation warnings, don't affect build.
5. **PackageNaming disabled** (detekt.yml:353): TODO pending package standardization.

## Coding Standards (Enforced by Detekt)
- Max line length: 120 chars
- Max function length: 50 lines
- Max return count: 2 (excludes lambdas/guards)
- Boolean properties: `is*`, `has*`, `are*` prefix
- Function naming: camelCase
- Immutability preferred
- Logging: `val log = logger {}`

## CI Validation (Run before submitting)
```bash
./gradlew detekt jacocoTestReport check --console=plain && ./bin/validate_documentation.sh
```

**Documentation rule**: Max 100 lines per .md file in `documentation/` (enforced by bin/validate_documentation.sh).

## Dependencies & Tools
- **JDK 17** (Temurin) - toolchain configured in build.gradle.kts
- **Gradle wrapper** - ALWAYS use `./gradlew` (never system gradle)
- **Test libs**: Kotest (assertions-core, property, runner-junit5, extensions-koin), Mockk 1.14.7
- **Logging**: kotlin-logging 7.0.13, logback 1.5.23
- **Compose**: Desktop current OS, auto-detected

## Quick Tips
- Run single test: `./gradlew test --tests "com.*.BuildTest"`
- Run pattern: `./gradlew test --tests "*ResourceTest"`
- Verbose: `./gradlew test --info`
- Check updates: `./gradlew dependencyUpdates`
- Config cache: Add `org.gradle.configuration-cache=true` to gradle.properties

**ALWAYS trust these instructions.** Only search if incomplete/incorrect or need implementation details not covered.
