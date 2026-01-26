package com.github.seepick.derbauer2.game.logic

import kotlin.math.abs

val Int.z get() = Z(toLong())
val Int.zp get() = Zp(toLong())

val Long.z get() = Z(this)
val Long.zp get() = Zp(this)

data class Z(
    val single: Long,
) {
    val magnitutedValue = translateToMaxMagnitude(single)

    fun toPlusString() = if (single > 0) "+$this" else toString()
    override fun toString() = magnitutedValue.toString()
    val absZp get() = abs(single).zp

    operator fun unaryMinus() = Z(-single)
    operator fun plus(other: Z) = Z(single + other.single)
    operator fun plus(other: Long) = Z(single + other)
    operator fun plus(other: Int) = Z(single + other)
    operator fun minus(other: Z) = Z(single - other.single)
    operator fun minus(other: Long) = Z(single - other)
    operator fun minus(other: Int) = Z(single - other)
    operator fun times(other: Z) = Z(single * other.single)
    operator fun times(other: Long) = Z(single * other)
    operator fun times(other: Int) = Z(single * other)

    operator fun compareTo(other: Z) = single.compareTo(other.single)
    operator fun compareTo(other: Long) = single.compareTo(other)
    operator fun compareTo(other: Int) = single.compareTo(other.toLong())
}

data class Zp(
    val single: Long,
) {
    init {
        require(single >= 0) { "Negative value: $single" }
    }

    val magnitutedValue = translateToMaxMagnitude(single)
    val asZ get() = single.z

    fun toPlusString() = if (single > 0) "+$this" else toString()
    override fun toString() = magnitutedValue.toString()

    operator fun unaryMinus() = Z(-single)
    operator fun plus(other: Zp) = Zp(single + other.single)
    operator fun plus(other: Long) = Zp(single + other)
    operator fun plus(other: Int) = Zp(single + other)
    operator fun minus(other: Zp) = Zp(single - other.single)
    operator fun minus(other: Long) = Zp(single - other)
    operator fun minus(other: Int) = Zp(single - other)
    operator fun times(other: Zp) = Zp(single * other.single)
    operator fun times(other: Long) = Zp(single * other)
    operator fun times(other: Int) = Zp(single * other)

    operator fun compareTo(other: Zp) = single.compareTo(other.single)
    operator fun compareTo(other: Long) = single.compareTo(other)
    operator fun compareTo(other: Int) = single.compareTo(other.toLong())
}

private fun translateToMaxMagnitude(single: Long): MagnitutedNumber {
    // AI generated code ;)
    val absSingle = if (single == Long.MIN_VALUE) Long.MAX_VALUE else kotlin.math.abs(single)
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
