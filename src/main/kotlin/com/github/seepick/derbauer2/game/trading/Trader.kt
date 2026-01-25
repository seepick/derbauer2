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
    fun handle(vararg requests: TradeRequest): TradeResult {
        require(requests.isNotEmpty())
        if (!canBuy(requests)) {
            return TradeResult.NotEnoughStorage
        }
        if (!canSell(requests)) {
            return TradeResult.NotEnoughResources
        }
        requests.forEach { request ->
            val resource = game.user.resource(request.resource)
            when (request.operation) {
                TradeOperation.BUY -> resource.owned += request.amount
                TradeOperation.SELL -> resource.owned -= request.amount
            }
        }
        return TradeResult.Success
    }

    private fun canBuy(requests: Array<out TradeRequest>): Boolean =
        requests.filter { it.operation == TradeOperation.BUY }
            .all { request ->
                val resource = game.user.resource(request.resource)
                if (resource is StorableResource) {
                    val totalCapacity = game.user.storageFor(resource)
                    (resource.owned + request.amount) <= totalCapacity
                } else {
                    true
                }
            }

    private fun canSell(requests: Array<out TradeRequest>): Boolean =
        requests.filter { it.operation == TradeOperation.SELL }
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

enum class TradeOperation {
    BUY, SELL
}
