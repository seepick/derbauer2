package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.execTx
import com.github.seepick.derbauer2.game.transaction.TxResult
import io.github.oshai.kotlinlogging.KotlinLogging.logger

private val log = logger {}

fun User.researchTech(item: TechItem): TxResult {
    log.info { "User.researchTech($item)" }
    require(item.state is TechState.Unresearched)
    item.costs.requireAllZeroOrPositive()
    val txResult = execTx(item.costs.invertSig())
    // no custom validators needed (to be able to be researched == valid)
    if (txResult.isFail) {
        return txResult
    }
    val tech = item.buildTech()
    enable(tech)
    return TxResult.Success
}
