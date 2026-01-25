package com.github.seepick.derbauer2.game.logic

import com.github.seepick.derbauer2.game.building.Building
import io.github.oshai.kotlinlogging.KotlinLogging.logger

class TurnTaker {
    private val log = logger {}

    var turn = 1
        private set

    fun takeTurn(game: Game): TurnReport {
        turn++
        log.info { "Taking turn $turn" }
        val user = game.user

        // TODO consume food per citizen
        return TurnReport(
            turn = turn,
            resourceProduction = user.buildings.filterIsInstance<ProducesResource>().map { producer ->
                val resource = user.resource(producer.resourceType)
                val produced = producer.produce()
                resource.units += produced // do
                resource to produced // and document
            }
        )
    }
}

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

    fun buyBuilding(building: Building): BuyResult {
        // FIXME building costs Resource_s_ (not Gold)
        val gold = user.resources.filterIsInstance<Gold>().first()
        if (gold.units < building.costsGold) {
            return BuyResult.NotEnoughGold
        }
        building.units += 1
        gold.units -= building.costsGold
        return BuyResult.Success
    }
}