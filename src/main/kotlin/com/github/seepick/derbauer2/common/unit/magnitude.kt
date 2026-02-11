package com.github.seepick.derbauer2.common.unit

import com.github.seepick.derbauer2.common.AiGenerated
import kotlin.math.abs

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

@AiGenerated
@Suppress("MagicNumber")
fun translateToMaxMagnitude(single: Long): MagnitutedNumber {
    val absSingle = if (single == Long.MIN_VALUE) Long.MAX_VALUE else abs(single)
    val mag = Magnitude.entries.reversed().firstOrNull {
        absSingle >= 1L shl 10 * it.thousands
    } ?: Magnitude.Single
    val factor = 1L shl 10 * mag.thousands
    return MagnitutedNumber(single / factor, mag, single)
}

@Suppress("VariableMinLength")
val Long.k get() = Magnitude.Kilo.multiply(this).z
@Suppress("VariableMinLength")
val Int.k get() = toLong().k
