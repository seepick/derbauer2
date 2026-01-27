package com.github.seepick.derbauer2.game.trading

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.transaction.Tx
import com.github.seepick.derbauer2.game.transaction.TxOperation
import com.github.seepick.derbauer2.game.transaction.TxResult
import com.github.seepick.derbauer2.game.transaction.execTx

fun User.trade(requestsX: TradeRequest, vararg requestsXS: TradeRequest): TxResult =
    execTx(
        listOf(requestsX, *requestsXS).map { request ->
            Tx.TxResource(
                resourceClass = request.resourceClass,
                amount = request.amount,
                operation = request.operation.asTxOperation
            )
        }
    )

val TradeOperation.asTxOperation
    get() = when (this) {
        TradeOperation.Buy -> TxOperation.INCREASE
        TradeOperation.Sell -> TxOperation.DECREASE
    }
