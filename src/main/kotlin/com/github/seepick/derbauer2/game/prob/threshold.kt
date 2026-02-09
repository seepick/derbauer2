package com.github.seepick.derbauer2.game.prob

import com.github.seepick.derbauer2.game.common.StrictDouble
import kotlin.random.Random

@JvmInline
value class ProbThresholderKey(val name: String) {
    override fun toString() = "${this::class.simpleName}[$name]"

    companion object
}

data class ProbThresholderHandle(
    val key: ProbThresholderKey,
    val thresholder: ProbThresholder,
)

data class FixedProbThresholder(
    /** The higher, the more likely. */
    private val threshold: () -> StrictDouble.ZeroToOne,
) : ProbThresholder {

    override fun isThresholdReached() =
        Random.nextDouble(0.0, 1.0) < threshold().number

}

fun interface ProbThresholder {
    fun isThresholdReached(): Boolean
}
