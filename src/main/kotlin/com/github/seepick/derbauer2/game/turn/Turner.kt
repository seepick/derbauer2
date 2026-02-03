package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.common.zz
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.hasEntity
import com.github.seepick.derbauer2.game.feature.FeatureTurner
import com.github.seepick.derbauer2.game.happening.HappeningTurner
import com.github.seepick.derbauer2.game.resource.Resource
import com.github.seepick.derbauer2.game.resource.ResourceChanges
import com.github.seepick.derbauer2.game.resource.StorableResource
import com.github.seepick.derbauer2.game.resource.buildResourceChanges
import com.github.seepick.derbauer2.game.resource.execTx
import com.github.seepick.derbauer2.game.resource.findResource
import com.github.seepick.derbauer2.game.resource.freeStorageFor
import com.github.seepick.derbauer2.game.resource.resourceChangesOf
import com.github.seepick.derbauer2.game.resource.toResourceChanges
import com.github.seepick.derbauer2.game.transaction.errorOnFail

@Suppress("LongParameterList")
class Turner(
    private val user: User,
    private val steps: List<TurnStep>,
    private val happeningTurner: HappeningTurner,
    private val featureTurner: FeatureTurner,
) {
    fun executeAndGenerateReport() = TurnReport(
        turn = user.turn,
        resourceChanges = resourceChangesOf(
            TurnPhase.entries.map {
                steps.execStepsAndMap(it)
            }),
        happenings = happeningTurner.buildHappeningMultiPages(),
        newFeatures = featureTurner.buildFeaturMultiPages(),
    )

    private fun List<TurnStep>.execStepsAndMap(phase: TurnPhase): ResourceChanges = filter {
        it.phase == phase && it.requiresEntities.all { required -> user.hasEntity(required) }
    }.flatMap { step ->
        val stepChanges = step.calcResourceChanges()
        stepChanges.changes
    }.toResourceChanges().let { mergedStepChanges ->
        buildResourceChanges {
            mergedStepChanges.changes.forEach { change ->
                val resource = user.findResource(change.resourceClass)
                val limitedAmount = limitAmount(resource, change.changeAmount)
                add(resource, limitedAmount)
            }
        }
    }.also {
        user.execTx(it).errorOnFail()
    }

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
