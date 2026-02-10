package com.github.seepick.derbauer2.game.core

enum class CityTitle(
    override val label: String,
) : HasLabel {
    // keep order!
    Village("Village"),
    Town("Town"),
    City("City"),
    Metropolis("Metropolis");

    companion object {
        val initial = entries.first()
    }
}


@Suppress("MagicNumber")
enum class UserTitle(
    val label: String,
) {
    // keep order!
    Sir("Young Sire"),
    Lord("Lord"),
    King("King"),
    Emperor("Emperor");

    companion object {
        val initial = entries.first()
    }
}
