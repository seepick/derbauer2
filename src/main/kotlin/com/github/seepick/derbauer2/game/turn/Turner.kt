package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.building.buildings
import com.github.seepick.derbauer2.game.logic.Game
import com.github.seepick.derbauer2.game.logic.Mechanics
import com.github.seepick.derbauer2.game.logic.ProducesResource
import com.github.seepick.derbauer2.game.logic.TurnReport
import com.github.seepick.derbauer2.game.logic.Units
import com.github.seepick.derbauer2.game.logic.storageFor
import com.github.seepick.derbauer2.game.logic.units
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Resource
import com.github.seepick.derbauer2.game.resource.StorableResource
import io.github.oshai.kotlinlogging.KotlinLogging.logger

class Turner {
    private val log = logger {}

    var turn = 1
        private set

    fun takeTurn(game: Game): TurnReport {
        turn++
        log.info { "Taking turn $turn" }
        return TurnReport(
            // FIXME clean list: if two different types, then accumulaten result!
            resourceChanges = buildList {
                addAll(executeCitizens(game))
                addAll(executeResources(game))
            }
        )
    }

    private fun executeCitizens(game: Game): List<Pair<Resource, Units>> {
        val user = game.user
        // TODO increase citizens (birth, immigration)
        val gold = user.resource(Gold::class)
        val citizen = user.resource(Citizen::class)
        val taxIncome = (citizen.owned.single * Mechanics.citizenTaxPercentage).toLong().units
        gold.owned += taxIncome

        val foodConsumed = (citizen.owned.single * Mechanics.citizenFoodConsumePercentage).toLong().units
        val food = user.resource(Food::class)
        food.owned -= foodConsumed // FIXME check for negative! maybe dont manipulate directly, but via method!
        // TODO if not enough food, citizens should decrease (left, or died)

        return listOf(
            gold to taxIncome,
            food to -foodConsumed,
        )
    }

    private fun executeResources(game: Game): List<Pair<Resource, Units>> {
        val user = game.user

        return user.buildings.filterIsInstance<ProducesResource>().map { producer ->
            val resource = user.resource(producer.producingResourceType)
            val producing = producer.resourceProductionAmount

            val added = if (resource is StorableResource) {
                val available = user.storageFor(resource) - resource.owned
                producing.single.coerceAtMost(available.single).units
            } else {
                producing
            }
            // TODO test if added == 0 (should not be contained in report!)
            resource.owned += added // do
            resource to added // and document
        }
    }
}
