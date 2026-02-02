package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.execTx
import com.github.seepick.derbauer2.game.transaction.TxResult
import io.github.oshai.kotlinlogging.KotlinLogging.logger

private val log = logger {}

fun User.researchTech(item: TechTreeItem): TxResult {
    log.info { "User.research($item)" }
    require(item.state is TechState.Unresearched)

    item.costs.requireAllNonNegative()
    val res = execTx(item.costs.invertSig())
    // no custom validators needed (to be able to be researched == valid)
    if (res.isFail) {
        return res
    }

    val tech = item.buildTech()
    enable(tech)

    return TxResult.Success
}
