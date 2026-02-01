package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.feature.FeatureTurner
import com.github.seepick.derbauer2.game.happening.HappeningTurner
import com.github.seepick.derbauer2.game.resource.ResourceChanges
import com.github.seepick.derbauer2.game.resource.TxResource
import com.github.seepick.derbauer2.game.resource.mergeToSingleChanges
import com.github.seepick.derbauer2.game.transaction.errorOnFail
import com.github.seepick.derbauer2.game.transaction.execTx
import io.github.oshai.kotlinlogging.KotlinLogging.logger

@Suppress("LongParameterList")
class Turner(
    private val user: User,
    private val resourceTurnSteps: List<ResourceTurnStep>,

    private val happeningTurner: HappeningTurner,
    private val featureTurner: FeatureTurner,
) {
    private val log = logger {}

    fun executeAndGenerateReport(): TurnReport {
        log.info { "Taking turn ${user.turn}" }
        val allChanges = buildList {
            addAll(resourceTurnSteps.execStepsAndMap(TurnPhase.First))
            addAll(resourceTurnSteps.execStepsAndMap(TurnPhase.Last))
        }
        return TurnReport(
            turn = user.turn,
            resourceChanges = allChanges.mergeToSingleChanges(),
            happenings = happeningTurner.buildHappeningMultiPages(),
            newFeatures = featureTurner.buildFeaturMultiPages(),
        )
    }

    private fun List<ResourceTurnStep>.execStepsAndMap(phase: TurnPhase) =
        this.filter { it.phase == phase && it.requiresEntities.all { required -> user.hasEntity(required) } }
            .flatMap { it.calcResourceChanges().also { it.mergeToSingleChanges().execute(user) } }
}

private fun ResourceChanges.execute(user: User) {
    user.execTx(changes.map { line ->
        TxResource(
            targetClass = line.resource::class,
            amount = line.changeAmount,
        )
    }).errorOnFail()
}
