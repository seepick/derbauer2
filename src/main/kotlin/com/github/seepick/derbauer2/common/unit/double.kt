package com.github.seepick.derbauer2.common.unit

import com.github.seepick.derbauer2.common.toFormatted

sealed class StrictDouble(val number: Double) : Comparable<StrictDouble> {

    override fun compareTo(other: StrictDouble) = this.number.compareTo(other.number)

    override fun toString() = "${this::class.simpleName}(${number.toFormatted()})"
    override fun equals(other: Any?) = other is StrictDouble && this::class == other::class && number == other.number
    override fun hashCode() = 31 * this::class.hashCode() + number.hashCode()

    class Anything(number: Double) : StrictDouble(number)
    class ZeroToOne(number: Double) : StrictDouble(number) {
        init {
            require(number in 0.0..1.0) { "Percentage must be between 0.0 and 1.0 but was: $number" }
        }
    }

    class Positive(number: Double) : StrictDouble(number) {
        init {
            require(number >= 0.0) { "Percentage must be between >= 0.0 but was: $number" }
        }
    }

    class MinusOneToOne(number: Double) : StrictDouble(number) {
        init {
            require(number in -1.0..1.0) { "Percentage must be between -1.0 and +1.0 but was: $number" }
        }
    }
}

val Double.doubleAny: DoubleAny get() = DoubleAny(this)
val Double.double01: Double01 get() = Double01(this)
val Double.double11: Double11 get() = Double11(this)
val Double.doublePos: DoublePos get() = DoublePos(this)
fun Double.coerceDouble11() = Double11(this.coerceIn(-1.0, 1.0))

typealias DoubleAny = StrictDouble.Anything
typealias Double01 = StrictDouble.ZeroToOne
typealias DoublePos = StrictDouble.Positive
typealias Double11 = StrictDouble.MinusOneToOne
