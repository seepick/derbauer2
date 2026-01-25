package com.github.seepick.derbauer2.game.logic

import com.github.seepick.derbauer2.game.building.Building
import io.github.oshai.kotlinlogging.KotlinLogging.logger

data class TurnReport(
    val turn: Int,
    val resourceProduction: List<Pair<Resource, Units>>,
)

class Game {
    private val log = logger {}

    val user = User()

    private val turner = TurnTaker()
    val turn = turner.turn
    val reports = mutableListOf<TurnReport>()

    fun nextTurn() {
        reports += turner.takeTurn(this)
    }

    fun buildBuilding(building: Building): BuildResult {
        // FIXME building costs Resource_s_ (not Gold)
        val gold = user.resources.filterIsInstance<Gold>().first()
        if (gold.owned < building.costsGold) {
            return BuildResult.NotEnoughGold
        }
        building.owned += 1
        gold.owned -= building.costsGold
        return BuildResult.Success
    }
}
