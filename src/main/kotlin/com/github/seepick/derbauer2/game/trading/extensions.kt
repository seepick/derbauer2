package com.github.seepick.derbauer2.game.trading

import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.resource.StorableResource
import com.github.seepick.derbauer2.game.resource.hasAtLeast
import com.github.seepick.derbauer2.game.resource.isAbleToStore
import com.github.seepick.derbauer2.game.resource.resource
import com.github.seepick.derbauer2.game.transaction.TxResult

fun User.trade(requestsX: TradeRequest, vararg requestsXS: TradeRequest): TxResult {
    val requests = listOf(requestsX, *requestsXS)
    if (!canBuy(requests)) {
        return TxResult.Fail.InsufficientResources("Not enough storage")
    }
    if (!canSell(requests)) {
        return TxResult.Fail.InsufficientResources("Not enough resources")
    }
    requests.forEach { request ->
        val resource = resource(request.resource)
        when (request.operation) {
            TradeOperation.Buy -> resource.owned += request.amount
            TradeOperation.Sell -> resource.owned -= request.amount
        }
    }
    return TxResult.Success
}

private fun User.canBuy(requests: List<TradeRequest>): Boolean =
    requests
        .filter { it.operation == TradeOperation.Buy }
        .map { it to resource(it.resource) }
        .mapNotNull { (request, resource) ->
            (resource as? StorableResource)?.let { request to it } // up-cast from Resource to StorableResource
        }
        .all { (request, resource) -> isAbleToStore(resource, request.amount) }

// FIXME bug, if two times same resource; but that shouldn't be possible anyway; ensure by umbrella TradeRequestMgr or similar
private fun User.canSell(requests: List<TradeRequest>): Boolean =
    requests.filter { it.operation == TradeOperation.Sell }
        .all {
            hasAtLeast(it.resource, it.amount)
        }
