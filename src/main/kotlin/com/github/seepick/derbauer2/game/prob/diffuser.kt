package com.github.seepick.derbauer2.game.prob

import com.github.seepick.derbauer2.game.common.Percent
import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.common.zz
import java.util.Random
import kotlin.math.roundToLong

@JvmInline
value class ProbDiffuserKey(val name: String) {
    override fun toString() = "${this::class.simpleName}[$name]"

    companion object
}

interface ProbDiffuser {
    fun diffuse(baseValue: Zz): Zz
}

class GrowthDiffuser(private val variation: Percent) : ProbDiffuser {
    private val random = Random()

    init {
        require(variation.value >= 0.0)
    }

    override fun diffuse(baseValue: Zz): Zz =
        if (baseValue <= 0.zz) {
            0.zz
        } else {
            random.nextGaussianInRange(
                mean = baseValue.toDouble(),
                sd = baseValue.toDouble() * variation.value,
            ).roundToLong().zz
        }
}

class GaussianDiffuser(private val deviation: Z) : ProbDiffuser {
    private val random = Random()
    override fun diffuse(baseValue: Zz): Zz =
        random.nextGaussianInRange(
            mean = baseValue.toDouble(),
            sd = deviation.toDouble()
        ).roundToLong().zz
}

fun Random.nextGaussianInRange(mean: Double, sd: Double, maxAttempts: Int = 10000): Double {
    val min = mean - sd
    val max = mean + sd
    repeat(maxAttempts) {
        val v = nextGaussian() * sd + mean
        if (v in min..max) return v
    }
    return mean.coerceIn(min, max) // fallback; unlikely though
}

object PassThroughDiffuser : ProbDiffuser {
    override fun diffuse(baseValue: Zz) = baseValue
}

class StaticDiffuser(private val staticValue: Zz) : ProbDiffuser {
    override fun diffuse(baseValue: Zz) = staticValue
}

data class ProbDiffuserHandle(
    val key: ProbDiffuserKey,
    val diffuser: ProbDiffuser,
) {
    fun withSelector(newDiffuser: ProbDiffuser) = ProbDiffuserHandle(key, newDiffuser)
}
