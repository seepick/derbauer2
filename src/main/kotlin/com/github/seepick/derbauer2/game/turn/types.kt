package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.common.AiGenerated
import com.github.seepick.derbauer2.game.common.Emoji
import com.github.seepick.derbauer2.game.common.emoji
import io.github.oshai.kotlinlogging.KotlinLogging.logger

private const val WEEKS_PER_YEAR = 52
private const val WEEKS_PER_SEASON = WEEKS_PER_YEAR / 4 // 13

interface CurrentTurn : Turn {
    val current: Turn
    fun next(): Turn

    companion object // for extensions
}

class CurrentTurnImpl(
    override var current: Turn = Turn(),
) : CurrentTurn, Turn by current {

    private val log = logger {}
    override fun next() =
        current.increment().also {
            log.debug { "Next turn: $it" }
            current = it
        }
}

interface Turn : Comparable<Turn> {
    val number: Int
    val week: Int
    val year: Int
    val season: Season

    fun increment(): Turn
    fun toSeasonedString(): String

    companion object {
        operator fun invoke(): Turn = TurnImpl()
        operator fun invoke(number: Int): Turn = TurnImpl(number)
        fun byYears(years: Int): Turn = TurnImpl((years * WEEKS_PER_YEAR) + 1)
    }
}

@Suppress("MagicNumber")
@AiGenerated // partially
data class TurnImpl(override val number: Int = 1) : Turn {

    init {
        require(number >= 1) { "Turn number must be >= 1 but was: $number" }
    }

    override val week = ((number - 1) % WEEKS_PER_YEAR) + 1
    override val year = ((number - 1) / WEEKS_PER_YEAR) + 1
    override val season = when (week) {
        in 1..WEEKS_PER_SEASON -> Season.Spring
        in (WEEKS_PER_SEASON + 1)..(WEEKS_PER_SEASON * 2) -> Season.Summer
        in (WEEKS_PER_SEASON * 2 + 1)..(WEEKS_PER_SEASON * 3) -> Season.Autumn
        else -> Season.Winter
    }

    override fun increment() = TurnImpl(number + 1)
    override fun compareTo(other: Turn) = this.number.compareTo(other.number)

    override fun toSeasonedString() = "${season.emoji}  W$week Y$year"
    override fun toString() = "${this::class.simpleName}($number / ${toSeasonedString()})"
}

enum class Season(val emoji: Emoji) {
    Spring("🌸".emoji),
    Summer("☀️".emoji),
    Autumn("🍂".emoji),
    Winter("❄️".emoji),
}
