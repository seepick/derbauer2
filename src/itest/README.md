# Integration Tests (itest)

This directory contains Cucumber-based integration tests for DerBauer2.

## Overview

The integration tests use:
- **Cucumber** for BDD-style feature files (Gherkin syntax)
- **Lambda-style step definitions** via `io.cucumber.java8.En`
- **Programmatic domain interaction** (no UI keyboard simulation)

## Structure

```
src/itest/
├── kotlin/
│   └── com/github/seepick/derbauer2/itest/
│       ├── CucumberTestSuite.kt          # JUnit Platform test runner
│       ├── steps/                         # Step definition classes
│       │   ├── ResourceSteps.kt          # Resource management steps
│       │   └── BuildingSteps.kt          # Building construction steps
│       └── support/                       # Test infrastructure
│           ├── TestAppDriver.kt          # Main test driver
│           ├── TestUserBuilder.kt        # User state setup
│           ├── TestTransactionExecutor.kt # Transaction execution
│           ├── TestWorld.kt              # Shared scenario state
│           └── TestHooks.kt              # Cucumber lifecycle hooks
└── resources/
    └── features/                          # Gherkin feature files
        ├── resources.feature
        ├── buildings.feature
        └── trading.feature
```

## Running Tests

Run all integration tests:
```bash
./gradlew integrationTest
```

Run integration tests and regular tests:
```bash
./gradlew check
```

## Writing New Tests

### 1. Create a Feature File

Add a new `.feature` file in `src/itest/resources/features/`:

```gherkin
Feature: Your Feature Name
  As a player
  I want to do something
  So that I can achieve a goal

  Scenario: Your scenario name
    Given a user with 100 gold
    When the user performs some action
    Then the user should have expected results
```

### 2. Create Step Definitions

Add a new step definition class in `src/itest/kotlin/.../steps/`:

```kotlin
package com.github.seepick.derbauer2.itest.steps

import com.github.seepick.derbauer2.itest.support.TestWorld
import io.cucumber.java8.En

class YourSteps : En {
    init {
        Given("a user with {int} gold") { goldAmount: Int ->
            TestWorld.userBuilder.setResource(Gold::class, goldAmount.z)
        }
        
        When("the user performs some action") {
            // Use TestWorld.txExecutor to execute domain operations
        }
        
        Then("the user should have expected results") {
            // Use assertions to verify state
        }
    }
}
```

## Key Components

### TestAppDriver
Main entry point for test scenarios. Initializes Koin DI and provides access to the User.

### TestUserBuilder
Fluent API for setting up user state (resources, buildings) without triggering transactions.

### TestTransactionExecutor
Execute domain transactions programmatically:
- `executeResourceTransaction()` - single resource transaction
- `executeBuildingTransaction()` - single building transaction
- `executeMultipleTransactions()` - multiple transactions atomically

### TestWorld
Singleton that holds shared state across step definitions in a scenario.

### TestHooks
Manages scenario lifecycle:
- `@Before` - Resets TestWorld and initializes driver
- `@After` - Cleans up Koin DI state

## Design Principles

1. **No UI simulation** - Tests interact with domain code directly, not through keyboard events
2. **Deterministic** - Tests reset DI state between scenarios for isolation
3. **Reusable steps** - Step definitions are parameterized for flexibility
4. **Clear separation** - Domain logic (main) vs test infrastructure (itest)

## Inspired By

These tests are inspired by `ExampleITest` but operate at a lower abstraction level:
- ExampleITest uses `KeyInput.Enter` and `selectPrompt("trade")`
- Integration tests use `TestTransactionExecutor.executeResourceTransaction()`

This provides more direct control and easier debugging.
