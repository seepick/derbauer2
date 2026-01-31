package com.github.seepick.derbauer2.game.probability

import com.github.seepick.derbauer2.game.common.`%`
import com.github.seepick.derbauer2.game.common.Percent
import com.github.seepick.derbauer2.game.common.compareTo
import kotlin.random.Random

fun interface ProbabilityCalculator {
    fun nextBoolean(): Boolean
}

class AlwaysProbabilityCalculator(private val always: Boolean) : ProbabilityCalculator {
    override fun nextBoolean() = always
}

class PercentageProbabilityCalculator(
    /** Will be true if: random < threshold */
    private val threshold: Percent,
) : ProbabilityCalculator {
    override fun nextBoolean(): Boolean {
        return Random.nextDouble(0.0, 1.0) < threshold
    }
}

class GrowthProbabilityCalculator(
    startValue: Percent,
    private val growthRate: Percent,
) : ProbabilityCalculator {

    init {
        require(startValue >= 0.0) { "startValue must be >= 0.0 but was: $startValue" }
        require(growthRate > 0.0) { "growthRate must be > 0.0 but was: $growthRate" }
    }

    private var currentValue = startValue

    override fun nextBoolean(): Boolean {
        if (Random.nextDouble(0.0, 1.0) <= currentValue) {
            currentValue = 0.0.`%` // reset
            return true
        }
        currentValue += growthRate
        if (currentValue > 1.0) currentValue = 1.0.`%`
        return false
    }
}
