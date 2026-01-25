package com.github.seepick.derbauer2.game.logic

import io.github.oshai.kotlinlogging.KotlinLogging

class TurnTaker {
    private val log = KotlinLogging.logger {}

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
                // FIXME check capacity, cap to max
                resource.owned += produced // do
                resource to produced // and document
            }
        )
    }
}