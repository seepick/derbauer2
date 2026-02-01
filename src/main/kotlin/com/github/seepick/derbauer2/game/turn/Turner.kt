package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.citizen.CitizenTurner
import com.github.seepick.derbauer2.game.citizen.ResourceTurnStep
import com.github.seepick.derbauer2.game.citizen.TurnPhase
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.citizens
import com.github.seepick.derbauer2.game.core.hasEntity
import com.github.seepick.derbauer2.game.feature.FeatureTurner
import com.github.seepick.derbauer2.game.happening.HappeningTurner
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.ResourceChanges
import com.github.seepick.derbauer2.game.resource.ResourceTurner
import com.github.seepick.derbauer2.game.resource.TxResource
import com.github.seepick.derbauer2.game.resource.toChanges
import com.github.seepick.derbauer2.game.transaction.errorOnFail
import com.github.seepick.derbauer2.game.transaction.execTx
import io.github.oshai.kotlinlogging.KotlinLogging.logger

@Suppress("LongParameterList")
class Turner(
    private val happeningTurner: HappeningTurner,
    private val resourceTurner: ResourceTurner,
    private val citizenTurner: CitizenTurner,
    private val featureTurner: FeatureTurner,
    private val user: User,
    private val reports: ReportIntelligence,
    private val resourceTurnSteps: List<ResourceTurnStep>,
) {
    private val log = logger {}

    var turn = 1
        private set

    fun collectAndExecuteNextTurnReport(): TurnReport {
        turn++
        log.info { "Taking turn $turn" }
        execResourceStepsInPhase(TurnPhase.First)

        val resourceResourceChanges = resourceTurner.buildResourceChanges()
        resourceResourceChanges.execute(user)
        val citizenResourceChanges = citizenTurner.buildResourceChanges()
        citizenResourceChanges.execute(user)

        execResourceStepsInPhase(TurnPhase.Last)
        return TurnReport(
            turn = turn - 1,
            resourceChanges = resourceResourceChanges.merge(citizenResourceChanges).changes,
            happenings = happeningTurner.buildHappeningMultiPages(),
            newFeatures = featureTurner.buildFeaturMultiPages(),
            isGameOver = user.isGameOver(),
        ).also { result ->
            reports.addReport(result)
        }
    }

    private fun execResourceStepsInPhase(phase: TurnPhase) {
        resourceTurnSteps
            .filter { it.phase == phase && it.requiresEntities.all { required -> user.hasEntity(required) } }
            .forEach { it.calcResourceChanges().toChanges().execute(user) }
    }

    private fun User.isGameOver() =
        if (!hasEntity<Citizen>()) {
            false
        } else {
            citizens == 0.z
        }
}


private fun ResourceChanges.execute(user: User) {
    user.execTx(changes.map { line ->
        TxResource(
            targetClass = line.resource::class,
            amount = line.changeAmount,
        )
    }).errorOnFail()
}
