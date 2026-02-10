package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.common.zz
import com.github.seepick.derbauer2.game.core.Action
import com.github.seepick.derbauer2.game.core.ActionsCollector
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.feature.FeatureSubPage
import com.github.seepick.derbauer2.game.feature.FeatureTurner
import com.github.seepick.derbauer2.game.happening.Happening
import com.github.seepick.derbauer2.game.happening.HappeningTurner
import com.github.seepick.derbauer2.game.resource.Resource
import com.github.seepick.derbauer2.game.resource.ResourceChange
import com.github.seepick.derbauer2.game.resource.ResourceChanges
import com.github.seepick.derbauer2.game.resource.StorableResource
import com.github.seepick.derbauer2.game.resource.execTx
import com.github.seepick.derbauer2.game.resource.findResource
import com.github.seepick.derbauer2.game.resource.freeStorageFor
import com.github.seepick.derbauer2.game.transaction.errorOnFail
import io.github.oshai.kotlinlogging.KotlinLogging.logger

/**
 * Implementations are declared as Koin beans and injected into [Turner].
 *
 * Do NOT confuse it with something stored in Users.all as an Entity, as done for
 * [com.github.seepick.derbauer2.game.resource.ProducesResource]
 */
interface GlobalTurnStep {
    fun execPreTurn()
    fun execPostTurn(report: TurnReport)
}

@Suppress("LongParameterList") // it's okay ;)
class Turner(
    private val user: User,
    private val turn: CurrentTurn,
    private val actionsCollector: ActionsCollector,
    private val globalSteps: List<GlobalTurnStep>,
    private val resSteps: List<ResourceTurnStep>,
    private val happeningTurner: HappeningTurner,
    private val featureTurner: FeatureTurner,
) {
    private val log = logger {}

    fun execTurnAndBuildReport(): TurnReport {
        log.info { "🔁🔁🔁 =================== ⬇️ TURN ⬇️ =================== 🔁🔁🔁" }
        globalSteps.forEach { it.execPreTurn() }
        val report = TurnReport(
            turn = turn.current,
            resourceChanges = resSteps
                .sortedBy { it.order }
                .map { execResourceStep(it) }
                .reduceOrNull { accRc, otherRc -> accRc.merge(otherRc) } ?: ResourceChanges.empty,
            happenings = happeningTurner.maybeHappening()?.let { listOf(it) } ?: emptyList(),
            featurePages = featureTurner.buildFeatureMultiPages(),
            actions = actionsCollector.getAllAndClear(),
        )
        globalSteps.forEach { it.execPostTurn(report) }
        log.info { "🔁 Changes: $report" }
        log.debug { "🔁 User.all: $user" }
        turn.next()
        log.info { "🔁🔁🔁 =================== ⬆️ TURN ⬆️ =================== 🔁🔁🔁" }
        return report
    }

    private fun execResourceStep(step: ResourceTurnStep): ResourceChanges =
        step.calcChanges().toLimitedAmounts().also { changes ->
            log.debug { "Executing: ${changes.toShortString()}" }
            user.execTx(changes).errorOnFail() // assume TXs were already validated
        }

    private fun ResourceChanges.toLimitedAmounts() = ResourceChanges(
        changes.map { change ->
            val resource = user.findResource(change.resourceClass)
            val limitedAmount = limitAmount(resource, change.change)
            ResourceChange(resource, limitedAmount)
        },
    )

    private fun limitAmount(resource: Resource, change: Zz): Zz =
        if (change > 0) { // is positive
            val positiveChange = change.toZAbs()
            if (resource is StorableResource) { // limit to maximum storage capacity
                positiveChange.coerceAtMost(user.freeStorageFor(resource)).zz
            } else { // get as much as you want
                positiveChange.zz
            }
        } else { // is negative
            if (resource.owned.zz + change < 0.zz) { // can't lose more than owned
                -resource.owned.zz
            } else {
                change
            }
        }

    companion object // for extension functions
}

data class TurnReport(
    val turn: Turn,
    val resourceChanges: ResourceChanges,
    val happenings: List<Happening>,
    val featurePages: List<FeatureSubPage>,
    val actions: List<Action>,
) {
    companion object // for extension functions
}
