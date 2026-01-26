package com.github.seepick.derbauer2.game.interaction

import com.github.seepick.derbauer2.game.building.Building
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.logic.landAvailable
import com.github.seepick.derbauer2.game.logic.storageFor
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.StorableResource
import com.github.seepick.derbauer2.game.trading.TradeOperation
import com.github.seepick.derbauer2.game.trading.TradeRequest

class Interaction(
    private val user: User,
) {
    fun build(building: Building): InteractionResult {
        val gold = user.resource(Gold::class)
        if (gold.owned < building.costsGold) {
            return InteractionResult.Failure.InsufficientResources("Not enough gold")
        }
        if(user.landAvailable < building.landUse) {
            return InteractionResult.Failure.InsufficientResources("Not enough land")
        }
        building.owned += 1
        gold.owned -= building.costsGold
        return InteractionResult.Success
    }

    fun trade(requestsX: TradeRequest, vararg requestsXS: TradeRequest): InteractionResult {
        val requests = listOf(requestsX, *requestsXS)
        if (!canBuy(requests)) {
            return InteractionResult.Failure.InsufficientResources("Not enough storage")
        }
        if (!canSell(requests)) {
            return InteractionResult.Failure.InsufficientResources("Not enough resources")
        }
        requests.forEach { request ->
            val resource = user.resource(request.resource)
            when (request.operation) {
                TradeOperation.Buy -> resource.owned += request.amount
                TradeOperation.Sell -> resource.owned -= request.amount
            }
        }
        return InteractionResult.Success
    }

    private fun canBuy(requests: List<TradeRequest>): Boolean =
        requests.filter { it.operation == TradeOperation.Buy }
            .all { request ->
                val resource = user.resource(request.resource)
                if (resource is StorableResource) {
                    val totalCapacity = user.storageFor(resource)
                    (resource.owned + request.amount) <= totalCapacity
                } else {
                    true
                }
            }

    private fun canSell(requests: List<TradeRequest>): Boolean =
        requests.filter { it.operation == TradeOperation.Sell }
            .all { request ->
                val resource = user.resource(request.resource)
                resource.owned >= request.amount
            }
}

sealed interface InteractionResult {
    object Success : InteractionResult
    sealed interface Failure : InteractionResult {
        val reason: String
        data class InsufficientResources(override val reason: String = "Insufficient resources") : Failure
    }
}
