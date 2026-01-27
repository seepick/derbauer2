package com.github.seepick.derbauer2.game.common

import kotlin.math.abs

val Long.zz get() = Zz(this)
val Long.z get() = Z(this)
val Int.zz get() = Zz(toLong())
val Int.z get() = Z(toLong())

/** Signed long. */
data class Zz(
    val value: Long,
) {
    val magnitutedValue = translateToMaxMagnitude(value)

    fun toPlusString() = if (value > 0) "+$this" else toString()
    override fun toString() = magnitutedValue.toString()

    fun asZ() = abs(value).z

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

    operator fun compareTo(other: Zz) = value.compareTo(other.value)
    operator fun compareTo(other: Long) = value.compareTo(other)
    operator fun compareTo(other: Int) = value.compareTo(other.toLong())
}

class NegativeZException(val value: Long) :
    IllegalArgumentException("Negative value for unsigned long: $value")

/** Unsigned long. */
data class Z(
    val value: Long,
) {
    init {
        if(value < 0) {
            throw NegativeZException(value)
        }
    }

    val magnitutedValue = translateToMaxMagnitude(value)
    val asZz get() = value.zz

    fun maxOf(owned: Z) = if (this > owned) this else owned
    fun minOf(owned: Z) = if (this < owned) this else owned

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

    operator fun compareTo(other: Z) = value.compareTo(other.value)
    operator fun compareTo(other: Long) = value.compareTo(other)
    operator fun compareTo(other: Int) = value.compareTo(other.toLong())
}

private fun translateToMaxMagnitude(single: Long): MagnitutedNumber {
    // AI generated code ;)
    val absSingle = if (single == Long.MIN_VALUE) Long.MAX_VALUE else abs(single)
    val mag = Magnitude.entries.reversed().firstOrNull {
        absSingle >= (1L shl (10 * it.thousands))
    } ?: Magnitude.Single
    val factor = 1L shl (10 * mag.thousands)
    return MagnitutedNumber(single / factor, mag, single)
}

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

val Double.`%`: Percent get() = Percent(this)

@JvmInline
value class Percent(val value: Double) {
    init {
        require(value in 0.0..1.0) { "Percent value must be between 0.0 and 1.0" }
    }
}
