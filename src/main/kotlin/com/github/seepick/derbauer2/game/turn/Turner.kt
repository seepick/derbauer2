package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.hasEntity
import com.github.seepick.derbauer2.game.feature.FeatureTurner
import com.github.seepick.derbauer2.game.happening.HappeningTurner
import com.github.seepick.derbauer2.game.resource.ResourceChanges
import com.github.seepick.derbauer2.game.resource.execTx
import com.github.seepick.derbauer2.game.resource.resourceChangesOf
import com.github.seepick.derbauer2.game.resource.toResourceChanges
import com.github.seepick.derbauer2.game.transaction.errorOnFail

@Suppress("LongParameterList")
class Turner(
    private val user: User,
    private val turnSteps: List<TurnStep>,
    private val happeningTurner: HappeningTurner,
    private val featureTurner: FeatureTurner,
) {
    fun executeAndGenerateReport() = TurnReport(
        turn = user.turn,
        resourceChanges = resourceChangesOf(
            TurnPhase.entries.map {
                turnSteps.execStepsAndMap(it)
            }
        ),
        happenings = happeningTurner.buildHappeningMultiPages(),
        newFeatures = featureTurner.buildFeaturMultiPages(),
    )

    private fun List<TurnStep>.execStepsAndMap(phase: TurnPhase): ResourceChanges =
        filter {
            it.phase == phase && it.requiresEntities.all { required -> user.hasEntity(required) }
        }.flatMap { step ->
            val stepChanges = step.calcResourceChanges()
            user.execTx(stepChanges).errorOnFail()
            stepChanges.changes
        }.toResourceChanges()
}
