package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.core.Action
import com.github.seepick.derbauer2.game.core.ActionBus
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.execTx
import com.github.seepick.derbauer2.game.transaction.TxResult
import io.github.oshai.kotlinlogging.KotlinLogging.logger

private val log = logger {}

data class TechResearchedAction(val techRef: TechRef) : Action

class TechService(
    private val user: User,
    private val actionBus: ActionBus,
) {
    fun researchTech(techRef: TechRef): TxResult {
        log.info { "User.researchTech($techRef)" }
        techRef.costs.requireAllZeroOrPositive() // sanity check
        val txResult = user.execTx(techRef.costs.invertSig())
        // no custom validators needed (to be able to be researched == valid)
        if (txResult.isFail) {
            return txResult
        }
        val tech = techRef.buildTech()
        user.add(tech)
        actionBus.dispatch(TechResearchedAction(techRef))
        return TxResult.Success
    }
}
