package com.github.seepick.derbauer2.game.logic

enum class UnitSize(
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

data class SizedUnits(
    val value: Int,
    val size: UnitSize,
) {
    val formatted = "$value${size.symbol ?: ""}"
}

data class Units(
    val single: Int,
) {
    // FIXME finish unit calc
    val asMaxUnit =
        if (single > 1024) SizedUnits(single / 1024, UnitSize.Kilo)
        else SizedUnits(single, UnitSize.Single)

    val formatted = asMaxUnit.formatted

    operator fun plus(other: Int) = Units(single + other)
    operator fun minus(other: Int) = Units(single - other)
    operator fun compareTo(other: Int) = single.compareTo(other)
}

val Int.units get() = Units(this)
