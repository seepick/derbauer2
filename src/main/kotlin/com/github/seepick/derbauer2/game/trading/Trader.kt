package com.github.seepick.derbauer2.game.trading

import com.github.seepick.derbauer2.game.logic.Game
import com.github.seepick.derbauer2.game.logic.Units
import com.github.seepick.derbauer2.game.logic.storageFor
import com.github.seepick.derbauer2.game.resource.Resource
import com.github.seepick.derbauer2.game.resource.StorableResource
import kotlin.reflect.KClass

sealed interface TradeResult {
    object Success : TradeResult
    object NotEnoughResources : TradeResult
    object NotEnoughStorage : TradeResult
}

class Trader(
    private val game: Game,
) {
    fun trade(requestsX: TradeRequest, vararg requestsXS: TradeRequest): TradeResult {
        val requests = listOf(requestsX, *requestsXS)
        if (!canBuy(requests)) {
            return TradeResult.NotEnoughStorage
        }
        if (!canSell(requests)) {
            return TradeResult.NotEnoughResources
        }
        requests.forEach { request ->
            val resource = game.user.resource(request.resource)
            when (request.operation) {
                TradeOperation.Buy -> resource.owned += request.amount
                TradeOperation.Sell -> resource.owned -= request.amount
            }
        }
        return TradeResult.Success
    }

    private fun canBuy(requests: List<TradeRequest>): Boolean =
        requests.filter { it.operation == TradeOperation.Buy }
            .all { request ->
                val resource = game.user.resource(request.resource)
                if (resource is StorableResource) {
                    val totalCapacity = game.user.storageFor(resource)
                    (resource.owned + request.amount) <= totalCapacity
                } else {
                    true
                }
            }

    private fun canSell(requests: List<TradeRequest>): Boolean =
        requests.filter { it.operation == TradeOperation.Sell }
            .all { request ->
                val resource = game.user.resource(request.resource)
                resource.owned >= request.amount
            }

}

class TradeRequest(
    val resource: KClass<out Resource>,
    val operation: TradeOperation,
    val amount: Units,
)

sealed interface TradeOperation {

    val label: String

    val inverse: TradeOperation
        get() = when (this) {
            is Buy -> Sell
            is Sell -> Buy
        }

    object Buy : TradeOperation {
        override val label = "Buy"
    }

    object Sell : TradeOperation {
        override val label = "Sell"
    }
}
