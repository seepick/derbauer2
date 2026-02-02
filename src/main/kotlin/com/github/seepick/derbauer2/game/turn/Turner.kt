package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.hasEntity
import com.github.seepick.derbauer2.game.feature.FeatureTurner
import com.github.seepick.derbauer2.game.happening.HappeningTurner
import com.github.seepick.derbauer2.game.resource.ResourceChange
import com.github.seepick.derbauer2.game.resource.execTx
import com.github.seepick.derbauer2.game.resource.toSingleChangesObject
import com.github.seepick.derbauer2.game.transaction.errorOnFail
import io.github.oshai.kotlinlogging.KotlinLogging.logger

@Suppress("LongParameterList")
class Turner(
    private val user: User,
    private val turnSteps: List<TurnStep>,

    private val happeningTurner: HappeningTurner,
    private val featureTurner: FeatureTurner,
) {
    private val log = logger {}

    fun executeAndGenerateReport(): TurnReport {
        log.info { "Taking turn ${user.turn}" }
        val allChanges = buildList {
            addAll(turnSteps.execStepsAndMap(TurnPhase.First))
            addAll(turnSteps.execStepsAndMap(TurnPhase.Last))
        }
        return TurnReport(
            turn = user.turn,
            resourceChanges = allChanges.toSingleChangesObject(),
            happenings = happeningTurner.buildHappeningMultiPages(),
            newFeatures = featureTurner.buildFeaturMultiPages(),
        )
    }

    private fun List<TurnStep>.execStepsAndMap(phase: TurnPhase): List<ResourceChange> =
        filter {
            it.phase == phase && it.requiresEntities.all { required -> user.hasEntity(required) }
        }.flatMap { step ->
            step.calcResourceChanges().also { changes ->
                user.execTx(changes.toSingleChangesObject()).errorOnFail()
            }
        }
}
