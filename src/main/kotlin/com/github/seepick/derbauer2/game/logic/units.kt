package com.github.seepick.derbauer2.game.logic

data class Units(
    val single: Long,
) {
    companion object {
        private fun translateToMaxMagnitude(single: Long): MagnitutedUnits {
            // AI generated code ;)
            val absSingle = if (single == Long.MIN_VALUE) Long.MAX_VALUE else kotlin.math.abs(single)
            val mag = Magnitude.entries.reversed().firstOrNull {
                absSingle >= (1L shl (10 * it.thousands))
            } ?: Magnitude.Single
            val factor = 1L shl (10 * mag.thousands)
            return MagnitutedUnits(single / factor, mag)
        }
    }

    val magnitutedUnits = translateToMaxMagnitude(single)

    fun toPlusString() = if(single > 0) "+$this" else toString()
    override fun toString() = magnitutedUnits.toString()

    operator fun unaryMinus() = Units(-single)
    operator fun plus(other: Units) = Units(single + other.single)
    operator fun plus(other: Long) = Units(single + other)
    operator fun plus(other: Int) = Units(single + other)

    operator fun minus(other: Units) = Units(single - other.single)
    operator fun minus(other: Long) = Units(single - other)
    operator fun minus(other: Int) = Units(single - other)

    operator fun times(other: Units) = Units(single * other.single)
    operator fun times(other: Long) = Units(single * other)
    operator fun times(other: Int) = Units(single * other)

    operator fun compareTo(other: Units) = single.compareTo(other.single)
    operator fun compareTo(other: Long) = single.compareTo(other)
    operator fun compareTo(other: Int) = single.compareTo(other.toLong())
}

/** pseudo-constructor */
val Int.units get() = Units(this.toLong())
val Long.units get() = Units(this)

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

data class MagnitutedUnits(
    val value: Long,
    val magnitude: Magnitude,
) {
    override fun toString() = "$value${magnitude.symbol ?: ""}"
}
