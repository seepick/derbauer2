package com.github.seepick.derbauer2.common.unit

import kotlin.math.ceil

@Suppress("ObjectPropertyName", "DANGEROUS_CHARACTERS")
val Double.`%`: Percent get() = Percent(this)

@Suppress("ObjectPropertyName", "DANGEROUS_CHARACTERS", "MagicNumber")
val Int.`%`: Percent get() = Percent(this.toDouble() / 100.0)

@JvmInline
value class Percent(val number: Double) {
    operator fun compareTo(other: Double) = this.number.compareTo(other)
    operator fun compareTo(other: Percent) = compareTo(other.number)
    operator fun plus(other: Double) = Percent(this.number + other)
    operator fun plus(other: Percent) = plus(other.number)

    /**
     * How many of this is needed to get 'amount' back after % has been applied.
     * e.g. 50.% neededToGetTo(1) == 2
     */
    fun neededToGetTo(amount: Int): Z {
        require(number != 0.0) { "division by zero" }
        return ceil((1.0 / number) * amount).toLong().z
    }
}

operator fun Double.compareTo(other: Percent) = this.compareTo(other.number)
