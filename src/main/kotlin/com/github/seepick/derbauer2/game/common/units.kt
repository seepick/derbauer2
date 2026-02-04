package com.github.seepick.derbauer2.game.common

import kotlin.math.abs
import kotlin.math.ceil

val Long.zz get() = Zz(this)
@Suppress("VariableMinLength")
val Long.z get() = Z(this)
val Int.zz get() = Zz(toLong())
@Suppress("VariableMinLength")
val Int.z get() = Z(toLong())

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
    operator fun minus(other: Long) = Zz(value - other)
    operator fun minus(other: Int) = Zz(value - other)
    operator fun times(other: Zz) = Zz(value * other.value)
    operator fun times(other: Long) = Zz(value * other)
    operator fun times(other: Int) = Zz(value * other)
    operator fun times(other: Percent) = Zz((value.toDouble() * other.value).toLong())

    override operator fun compareTo(other: Zz) = value.compareTo(other.value)
    operator fun compareTo(other: Long) = value.compareTo(other)
    operator fun compareTo(other: Int) = value.compareTo(other.toLong())
}

class NegativeZException(val value: Long) :
    IllegalArgumentException("Negative value for unsigned long: $value")

/** Unsigned long. */
@Suppress("TooManyFunctions")
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

    fun coerceAtMost(max: Z) = value.coerceAtMost(max.value).z
    fun coerceIn(min: Z, max: Z) = value.coerceIn(min.value, max.value).z
    infix fun orMaxOf(other: Z) = if (this > other) this else other
    infix fun orMinOf(other: Z) = if (this < other) this else other

    fun toPlusString() = if (value > 0) "+$this" else toString()
    override fun toString() = magnitutedValue.toString()

    operator fun unaryMinus() = Zz(-value)
    operator fun plus(other: Z) = Z(value + other.value)
    operator fun plus(other: Long) = Z(value + other)
    operator fun plus(other: Int) = Z(value + other)
    operator fun minus(other: Z) = Z(value - other.value)
    operator fun minus(other: Long) = Z(value - other)
    operator fun minus(other: Int) = Z(value - other)
    operator fun times(other: Z) = Z(value * other.value)
    operator fun times(other: Long) = Z(value * other)
    operator fun times(other: Int) = Z(value * other)
    operator fun times(other: Percent) = Z((value * other.value).toLong())

    override operator fun compareTo(other: Z) = value.compareTo(other.value)
    operator fun compareTo(other: Long) = value.compareTo(other)
    operator fun compareTo(other: Int) = value.compareTo(other.toLong())
}

// operator fun Long.plus(other: Z) = Z(this + other.value) .........

@Suppress("MagicNumber")
private fun translateToMaxMagnitude(single: Long): MagnitutedNumber {
    // AI generated code ;)
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
    Exa(6, "x"),
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
value class Percent(val value: Double) {
    operator fun compareTo(other: Percent) = this.value.compareTo(other.value)
    operator fun compareTo(other: Double) = this.value.compareTo(other)
    operator fun plus(other: Percent) = Percent(this.value + other.value)

    /** How many of this is needed to get 'amount' back after % has been applied. */
    // TODO test this; maybe need ceil() instead cutoff via toLong()
    fun neededToGetTo(amount: Int): Z = ceil((1.0 / value) * amount).toLong().z
}

operator fun Double.compareTo(other: Percent) = this.compareTo(other.value)
