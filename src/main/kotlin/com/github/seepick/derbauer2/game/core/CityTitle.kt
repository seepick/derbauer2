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

