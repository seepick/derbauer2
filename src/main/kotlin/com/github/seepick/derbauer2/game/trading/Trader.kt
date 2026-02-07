package com.github.seepick.derbauer2.game.trading

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.resource.Resource
import kotlin.reflect.KClass

data class TradeRequest(
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
