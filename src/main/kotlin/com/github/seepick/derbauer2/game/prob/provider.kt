package com.github.seepick.derbauer2.game.prob

import io.github.oshai.kotlinlogging.KotlinLogging.logger

data class ProbProviderHandle<T>(
    private val key: ProbProviderKey<T>,
    private val calculator: ProbCalculator,
    private val provider: () -> T,
) {
    private val log = logger {}

    fun provide(): T? =
        if (calculator.nextBoolean()) {
            log.debug { "Probability hit for: $key" }
            provider()
        } else {
            null
        }
}

@JvmInline
value class ProbProviderKey<T>(val name: String) {
    override fun toString() = "${this::class.simpleName}[$name]"

    companion object
}
