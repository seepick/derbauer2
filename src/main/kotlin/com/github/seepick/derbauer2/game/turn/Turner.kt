package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.citizen.CitizenTurner
import com.github.seepick.derbauer2.game.feature.FeatureTurner
import com.github.seepick.derbauer2.game.happening.HappeningTurner
import com.github.seepick.derbauer2.game.logic.TurnReport
import com.github.seepick.derbauer2.game.resource.ResourceTurner
import io.github.oshai.kotlinlogging.KotlinLogging.logger

class Turner(
    private val happeningTurner: HappeningTurner,
    private val resourceTurner: ResourceTurner,
    private val citizenTurner: CitizenTurner,
    private val featureTurner: FeatureTurner,
) {
    private val log = logger {}
    val reports = mutableListOf<TurnReport>() // FIXME store in ReportIntelligence (historical auswertung)

    var turn = 1
        private set

    fun collectAndExecuteNextTurnReport(): TurnReport {
        turn++
        log.info { "Taking turn $turn" }
        val resourceReport = resourceTurner.executeAndReturnReport() // first resources, then happenings
        val citizenReport = citizenTurner.executeAndReturnReport()
        val happenings = happeningTurner.turn()
        val newFeatures = featureTurner.turn()
        return TurnReport(
            turn = turn - 1,
            resourceChanges = resourceReport.merge(citizenReport).changes,
            happenings = happenings,
            newFeatures = newFeatures,
        ).also { result ->
            reports += result
        }
    }

}
