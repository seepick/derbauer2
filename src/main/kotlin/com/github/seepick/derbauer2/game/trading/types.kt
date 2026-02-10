package com.github.seepick.derbauer2.game.trading

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.core.Action
import com.github.seepick.derbauer2.game.resource.Resource
import kotlin.reflect.KClass

data class TradeSingleRequest(
    val resourceClass: KClass<out Resource>,
    val operation: TradeOperation,
    val amount: Z,
) {
    constructor(
        resourceClass: KClass<out Resource>,
        amount: Zz,
    ) : this(
        resourceClass = resourceClass,
        operation = if (amount >= 0) TradeOperation.Buy else TradeOperation.Sell,
        amount = amount.toZAbs(),
    )
}

sealed class TradeOperation(val label: String) {

    val inverse: TradeOperation
        get() = when (this) {
            is Buy -> Sell
            is Sell -> Buy
        }

    override fun toString() = this::class.simpleName ?: "!UnknownTradeOperation!"

    object Buy : TradeOperation(label = "Buy")
    object Sell : TradeOperation(label = "Sell")
}

/** The same resource could occure multiple times in these requests. */
data class ResourcesTradedAction(val requests: List<TradeSingleRequest>) : Action {
    companion object // for extensions
}

@Suppress("JavaDefaultMethodsNotOverriddenByDelegation")
data class TradeCompoundRequests(val options: List<TradeCompoundRequest>) : List<TradeCompoundRequest> by options

data class TradeAmount(
    val resoureClass: KClass<out Resource>,
    val amount: Z,
)

data class TradeCompoundRequest(
    val operation: TradeOperation,
    val target: TradeAmount,
    val counters: List<TradeAmount>,
)
