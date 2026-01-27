package com.github.seepick.derbauer2.game.trading

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.resource.Resource
import kotlin.reflect.KClass

class TradeRequest(
    val resourceClass: KClass<out Resource>,
    val operation: TradeOperation,
    val amount: Z,
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
