# Coding Standards

Adhere as much as possible to the official Kotlin coding conventions (
see: https://kotlinlang.org/docs/coding-conventions.html).

## Static Code Analysis via Detekt

Based on [Detekt config](/config/detekt.yml)

- Max line length: 120 chars
- Max function length: 50 lines
- Max return count: 2 (excludes lambdas/guards)
- Boolean properties: `is*`, `has*`, `are*` prefix
- Function naming: camelCase
- Immutability preferred
- Logging: `val log = logger {}`

## Patterns

### Mutation via Transactions

All resource changes uses a transaction pattern to allow domain validations to be run in a simulation after applying.

```kotlin
execTx(
    TxResource(Gold::class, amount.asZz),
    TxResource(Land::class, cost.asZz)
).errorOnFail()
```
