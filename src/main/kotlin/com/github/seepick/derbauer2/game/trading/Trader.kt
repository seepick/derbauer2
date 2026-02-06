package com.github.seepick.derbauer2.game.trading

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.resource.Resource
import kotlin.reflect.KClass

data class TradeRequest(
    val resourceClass: KClass<out Resource>,
    val operation: TradeOperation,
    val amount: Z,
)

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
