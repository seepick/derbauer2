package com.github.seepick.derbauer2.game.common

import com.github.seepick.derbauer2.game.core.AiGenerated
import kotlin.math.abs
import kotlin.math.ceil

val Long.zz get() = Zz(this)
@Suppress("VariableMinLength")
val Long.z get() = Z(this)
val Int.zz get() = Zz(toLong())
@Suppress("VariableMinLength")
val Int.z get() = Z(toLong())

@Suppress("VariableMinLength")
val Long.k get() = Magnitude.Kilo.multiply(this).z
@Suppress("VariableMinLength")
val Int.k get() = toLong().k


/** Signed long. */
@Suppress("TooManyFunctions")
data class Zz(
    val value: Long,
) : Comparable<Zz> {
    val magnitutedValue = translateToMaxMagnitude(value)

    fun toSymboledString() = if (value > 0) "+$this" else toString()
    override fun toString() = magnitutedValue.toString()

    /** Will simply remove "-" sign if present, returning its absolute value. */
    fun toZAbs() = abs(value).z
    /** Will throw if negative. */
    fun toZLimitMinZero() = if (value < 0) 0.z else value.z
    fun toZOrThrowIfNegative() = value.z
    fun toDouble() = value.toDouble()

    operator fun unaryMinus() = Zz(-value)
    operator fun plus(other: Zz) = Zz(value + other.value)
    operator fun plus(other: Long) = Zz(value + other)
    operator fun plus(other: Int) = Zz(value + other)
    operator fun minus(other: Zz) = Zz(value - other.value)
    operator fun minus(other: Z) = Zz(value - other.value)
    operator fun minus(other: Long) = Zz(value - other)
    operator fun minus(other: Int) = Zz(value - other)
    operator fun times(other: Zz) = Zz(value * other.value)
    operator fun times(other: Long) = Zz(value * other)
    operator fun times(other: Int) = Zz(value * other)
    operator fun times(other: Percent) = Zz((value.toDouble() * other.number).toLong())

    override operator fun compareTo(other: Zz) = value.compareTo(other.value)
    operator fun compareTo(other: Long) = value.compareTo(other)
    operator fun compareTo(other: Int) = value.compareTo(other.toLong())
}

class NegativeZException(val value: Long) :
    IllegalArgumentException("Negative value for unsigned long: $value")

/** Unsigned long. */
@Suppress("TooManyFunctions", "MethodOverloading")
data class Z(
    val value: Long,
) : Comparable<Z> {
    init {
        if (value < 0) {
            throw NegativeZException(value)
        }
    }

    val magnitutedValue = translateToMaxMagnitude(value)
    val zz get() = value.zz
    fun toDouble() = value.toDouble()

    @Deprecated("user coerce fun")
    fun coerceIn(min: Z, max: Z) = value.coerceIn(min.value, max.value).z
    @Deprecated("user coerce fun")
    infix fun orMaxOf(other: Z) = if (this > other) this else other
    @Deprecated("user coerce fun")
    infix fun orMinOf(other: Z) = if (this < other) this else other

    fun toPrefixedString() = if (value > 0) "+$this" else toString()
    override fun toString() = magnitutedValue.toString()

    operator fun unaryMinus() = Zz(-value)
    operator fun plus(other: Z) = Z(value + other.value)
    operator fun plus(other: Zz) = Zz(value + other.value)
    operator fun plus(other: Long) = Z(value + other)
    operator fun plus(other: Int) = Z(value + other)
    operator fun minus(other: Z) = Z(value - other.value)
    operator fun minus(other: Long) = Z(value - other)
    operator fun minus(other: Int) = Z(value - other)
    operator fun times(other: Z) = Z(value * other.value)
    operator fun times(other: Zz) = Zz(value * other.value)
    operator fun times(other: Long) = Z(value * other)
    operator fun times(other: Int) = Z(value * other)
    operator fun times(other: Percent) = Z((value * other.number).toLong())

    override operator fun compareTo(other: Z) = value.compareTo(other.value)
    operator fun compareTo(other: Long) = value.compareTo(other)
    operator fun compareTo(other: Int) = value.compareTo(other.toLong())
}

// operator fun Long.plus(other: Z) = Z(this + other.value) .........

@AiGenerated
@Suppress("MagicNumber")
private fun translateToMaxMagnitude(single: Long): MagnitutedNumber {
    val absSingle = if (single == Long.MIN_VALUE) Long.MAX_VALUE else abs(single)
    val mag = Magnitude.entries.reversed().firstOrNull {
        absSingle >= 1L shl 10 * it.thousands
    } ?: Magnitude.Single
    val factor = 1L shl 10 * mag.thousands
    return MagnitutedNumber(single / factor, mag, single)
}

@Suppress("MagicNumber")
enum class Magnitude(
    val thousands: Int,
    val symbol: String?,
) {
    Single(0, null),
    Kilo(1, "k"),
    Mega(2, "m"),
    Giga(3, "g"),
    Tera(4, "t"),
    Peta(5, "p"),
    Exa(6, "x");

    fun multiply(single: Long) = single * (1L shl 10 * thousands)
}

data class MagnitutedNumber(
    val number: Long,
    val magnitude: Magnitude,
    val originalNumber: Long,
) {
    override fun toString() = "$number${magnitude.symbol ?: ""}"
}

@Suppress("ObjectPropertyName", "DANGEROUS_CHARACTERS")
val Double.`%`: Percent get() = Percent(this)

@Suppress("ObjectPropertyName", "DANGEROUS_CHARACTERS", "MagicNumber")
val Int.`%`: Percent get() = Percent(this.toDouble() / 100.0)

@JvmInline
value class Percent(val number: Double) {
    operator fun compareTo(other: Percent) = this.number.compareTo(other.number)
    operator fun compareTo(other: Double) = this.number.compareTo(other)
    operator fun plus(other: Percent) = Percent(this.number + other.number)

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

val Double.strictAny: DoubleAny get() = DoubleAny(this)
val Double.strict01: Double01 get() = Double01(this)
val Double.strict11: Double11 get() = Double11(this)
val Double.strictPos: DoublePos get() = DoublePos(this)

typealias DoubleAny = StrictDouble.Anything
typealias Double01 = StrictDouble.ZeroToOne
typealias DoublePos = StrictDouble.Positive
typealias Double11 = StrictDouble.MinusOneToOne

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
