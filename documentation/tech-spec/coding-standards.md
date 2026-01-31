# Coding Standards

Adhere as much as possible to the official Kotlin coding conventions
(see https://kotlinlang.org/docs/coding-conventions.html).

## General Conventions

* Write concise, **small functions** with a single responsibility and low coupling.
* Don't write any **comments**. If you feel the need to comment, refactor the code instead.
* Use **meaningful names** for variables, functions, classes, etc.
    * Capturing its essence, from a service not a consumer view.
    * Using short, single words which are self-explanatory.
* Prefer **immutability** (val) over mutability (var).
* Use **data classes** for value objects, and **value classes** for single-value wrappers.
* Avoid using the `Any` type and runtime type checks/casts.
    * Except if necessary due to a more dynamic/flexible approach; but do so with caution (safeguard errors manually).
* Use **expression body syntax** for functions and properties with single expressions.
    * Do so especially when implementing interfaces/abstract functions.
* Use **named arguments** for functions with more than 2 parameters.
* Use **logging** only if it adds value (e.g. for debugging/traceability)
    * Always use appropriate log levels and include context.
* Use Kotlin's `object class` declaration for **singletons** which don't hold state or are not meant to be swapped out.
    * This is okay also in regard to testability.

### Formatting

* Use **trailing commas** in multi-line collections, function calls, etc.
* Use 4 spaces for **indentation**.
* Use **blank lines** to separate logical blocks of code.

## Static Code Analysis via Detekt

Based on [Detekt config](/config/detekt.yml)

- Max line length: 120 chars
- Max function length: 50 lines
- Max return count: 2 (excludes lambdas/guards)
- Boolean properties: `is*`, `has*`, `are*` prefix
- Function naming: camelCase
- Immutability preferred
- Logging: `val log = logger {}`

You can run detekt locally via: `./gradlew detekt`

## Patterns

### Mutation via Transactions

All resource changes uses a transaction pattern to allow domain validations to be run in a simulation after applying.

```kotlin
execTx(
    TxResource(Gold::class, amount.asZz),
    TxResource(Land::class, cost.asZz)
).errorOnFail()
```
