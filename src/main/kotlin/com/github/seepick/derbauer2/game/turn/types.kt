package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.common.AiGenerated
import com.github.seepick.derbauer2.game.common.Emoji
import com.github.seepick.derbauer2.game.common.emoji

interface CurrentTurn {
    val current: Turn
    fun next(): Turn

    companion object // for extensions
}

class CurrentTurnImpl : CurrentTurn {
    override var current: Turn = Turn()
    override fun next() =
        current.increment().also {
            current = it
        }
}

@Suppress("MagicNumber")
@AiGenerated // partially
data class Turn(val number: Int = 1) : Comparable<Turn> {
    init {
        require(number >= 1) { "Turn number must be >= 1 but was: $number" }
    }

    val week: Int = ((number - 1) % WEEKS_PER_YEAR) + 1
    val year: Int = ((number - 1) / WEEKS_PER_YEAR) + 1
    val season = when (week) {
        in 1..WEEKS_PER_SEASON -> Season.Spring
        in (WEEKS_PER_SEASON + 1)..(WEEKS_PER_SEASON * 2) -> Season.Summer
        in (WEEKS_PER_SEASON * 2 + 1)..(WEEKS_PER_SEASON * 3) -> Season.Autumn
        else -> Season.Winter
    }

    fun increment() = Turn(number + 1)
    override fun compareTo(other: Turn) = this.number.compareTo(other.number)

    fun toSeasonedString() = "${season.emoji}  W$week Y$year"
    override fun toString() = "${this::class.simpleName}($number / ${toSeasonedString()})"

    companion object {
        private const val WEEKS_PER_YEAR = 52
        private const val WEEKS_PER_SEASON = WEEKS_PER_YEAR / 4 // 13
        fun byYears(years: Int) = Turn((years * WEEKS_PER_YEAR) + 1)
    }
}

enum class Season(val emoji: Emoji) {
    Spring("🌸".emoji),
    Summer("☀️".emoji),
    Autumn("🍂".emoji),
    Winter("❄️".emoji),
}
