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
* Use **expression body syntax** for functions and properties with single expressions.
* Use **named arguments** for functions with more than 2 parameters.
* Use **data classes** for value objects, and **value classes** for single-value wrappers.
* Avoid using the `Any` type and runtime type checks/casts.
    * Except if necessary due to a more dynamic/flexible approach; but do so with caution (safeguard errors manually).

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
