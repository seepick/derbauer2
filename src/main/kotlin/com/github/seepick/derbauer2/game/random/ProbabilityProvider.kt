package com.github.seepick.derbauer2.game.random

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlin.random.Random

class ProbabilityProvider<R>(
    startValue: Double,
    private val growthRate: Double,
    val provider: () -> R,
) {
    init {
        require(startValue >= 0.0) { "startValue must be >= 0.0 but was: $startValue" }
        require(growthRate > 0.0) { "growthRate must be > 0.0 but was: $growthRate" }
    }

    private val log = KotlinLogging.logger {}
    private var currentValue = startValue

    fun randomProvide(): R? {
        if (Random.nextDouble(0.0, 1.0) <= currentValue) {
            log.debug { "probability hit." }
            currentValue = 0.0
            return provider()
        }
        currentValue += growthRate
        if (currentValue > 1.0) currentValue = 1.0
        return null
    }
}

fun <T> MutableList<T>.addRandomIfNotNull(prob: ProbabilityProvider<T>) {
    prob.randomProvide()?.let {
        add(it)
    }
}
