package com.github.seepick.derbauer2.game.core

enum class EpochProgress(
    override val label: String,
) : HasLabel {
    // keep order!
    Survival("Survival"),
    Expansion("Expansion"),
    Conquer("Conquer"),
    Golden("Golden"),
    ;

    companion object {
        val initial = entries.first()
    }
}
