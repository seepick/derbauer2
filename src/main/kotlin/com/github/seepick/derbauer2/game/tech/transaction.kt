package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.execTx
import com.github.seepick.derbauer2.game.transaction.TxResult
import io.github.oshai.kotlinlogging.KotlinLogging.logger

private val log = logger {}

fun User.researchTech(techRef: TechRef): TxResult {
    log.info { "User.researchTech($techRef)" }
    techRef.costs.requireAllZeroOrPositive() // sanity check
    val txResult = execTx(techRef.costs.invertSig())
    // no custom validators needed (to be able to be researched == valid)
    if (txResult.isFail) {
        return txResult
    }
    val tech = techRef.buildTech()
    add(tech)
    return TxResult.Success
}
