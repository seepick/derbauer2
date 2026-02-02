package com.github.seepick.derbauer2.game.core

@Suppress("MagicNumber")
enum class CityTitle(
    val label: String,
    val order: Int,
) {
    Village("Village", 0),
    Town("Town", 1),
    City("City", 2),
    Metropolis("Metropolis", 3);

    companion object {
        val initial = entries.minBy { it.order }
    }
}

@Suppress("MagicNumber")
enum class UserTitle(
    val label: String,
    val order: Int,
) {
    Sir("Sir", 0),
    Lord("Lord", 1),
    King("King", 2),
    Emperor("Emperor", 3);

    companion object {
        val initial = entries.minBy { it.order }
    }
}
