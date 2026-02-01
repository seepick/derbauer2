package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.citizen.CitizenTurner
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.citizens
import com.github.seepick.derbauer2.game.core.hasEntity
import com.github.seepick.derbauer2.game.feature.FeatureTurner
import com.github.seepick.derbauer2.game.happening.HappeningTurner
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.ResourceReport
import com.github.seepick.derbauer2.game.resource.ResourceTurner
import com.github.seepick.derbauer2.game.resource.TxResource
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
) {
    private val log = logger {}

    var turn = 1
        private set

    fun collectAndExecuteNextTurnReport(): TurnReport {
        turn++
        log.info { "Taking turn $turn" }
        val resourceReport = resourceTurner.buildTurnReport()
        resourceReport.execute(user)
        val citizenReport = citizenTurner.buildReport()
        citizenReport.execute(user)
        return TurnReport(
            turn = turn - 1,
            resourceChanges = resourceReport.merge(citizenReport).lines,
            happenings = happeningTurner.buildHappeningMultiPages(),
            newFeatures = featureTurner.buildFeaturMultiPages(),
            isGameOver = user.isGameOver(),
        ).also { result ->
            reports.addReport(result)
        }
    }

    private fun User.isGameOver(): Boolean =
        if (!hasEntity<Citizen>()) {
            false
        } else {
            citizens == 0.z
        }
}

private fun ResourceReport.execute(user: User) {
    user.execTx(lines.map { line ->
        TxResource(
            targetClass = line.resource::class,
            amount = line.changeAmount,
        )
    }).errorOnFail()
}
