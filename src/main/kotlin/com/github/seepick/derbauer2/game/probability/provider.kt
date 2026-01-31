package com.github.seepick.derbauer2.game.probability

import io.github.oshai.kotlinlogging.KotlinLogging.logger

/** To identify for tests. */
enum class ProbabilityProviderSource {
    HappeningTurner,
    HappeningIsNeg,
}

data class ProbabilityProviderHandle<T>(
    private val source: ProbabilityProviderSource,
    private val calculator: ProbabilityCalculator,
    private val provider: () -> T,
) {
    private val log = logger {}

    fun provide(): T? =
        if (calculator.nextBoolean()) {
            log.debug { "${this::class.simpleName} probability hit for: $source" }
            provider()
        } else {
            null
        }
}
