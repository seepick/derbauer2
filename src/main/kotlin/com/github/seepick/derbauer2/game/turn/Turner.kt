package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.building.buildings
import com.github.seepick.derbauer2.game.logic.Game
import com.github.seepick.derbauer2.game.logic.ProducesResource
import com.github.seepick.derbauer2.game.logic.TurnReport
import com.github.seepick.derbauer2.game.logic.storageFor
import com.github.seepick.derbauer2.game.logic.units
import com.github.seepick.derbauer2.game.resource.StorableResource
import io.github.oshai.kotlinlogging.KotlinLogging

class Turner {
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
            // FIXME if two different types, the accumulate result!
            resourceProduction = user.buildings.filterIsInstance<ProducesResource>().map { producer ->
                val resource = user.resource(producer.producingResourceType)
                val producing = producer.resourceProductionAmount

                val added = if(resource is StorableResource) {
                    val available = user.storageFor(resource) - resource.owned
                    producing.single.coerceAtMost(available.single).units
                } else {
                    producing
                }

                resource.owned += added // do
                resource to added // and document
            }
        )
    }
}