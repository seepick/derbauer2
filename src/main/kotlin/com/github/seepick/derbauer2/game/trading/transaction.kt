package com.github.seepick.derbauer2.game.trading

import com.github.seepick.derbauer2.game.common.Emoji
import com.github.seepick.derbauer2.game.core.TxOwnable
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.transaction.TxOperation
import com.github.seepick.derbauer2.game.transaction.TxResult
import com.github.seepick.derbauer2.game.transaction.execTx
import io.github.oshai.kotlinlogging.KotlinLogging.logger

private val log = logger {}

fun User.trade(requests: List<TradeRequest>): TxResult {
    log.info { "${Emoji.`trade 💸`} trading for: $requests" }
    return execTx(requests.map { it.toTxOwnable() })
}

fun User.trade(requestsX: TradeRequest, vararg requestsXS: TradeRequest) =
    trade(listOf(requestsX, *requestsXS))

fun TradeRequest.toTxOwnable() = TxOwnable(
    ownableClass = resourceClass,
    amount = amount,
    operation = operation.asTxOperation
)

val TradeOperation.asTxOperation
    get() = when (this) {
        TradeOperation.Buy -> TxOperation.INCREASE
        TradeOperation.Sell -> TxOperation.DECREASE
    }
