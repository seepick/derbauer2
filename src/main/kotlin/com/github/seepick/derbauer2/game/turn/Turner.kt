package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.citizen.CitizenTurner
import com.github.seepick.derbauer2.game.happening.HappeningTurner
import com.github.seepick.derbauer2.game.logic.TurnReport
import com.github.seepick.derbauer2.game.resource.ResourceTurner
import io.github.oshai.kotlinlogging.KotlinLogging.logger

class Turner(
    private val happeningTurner: HappeningTurner,
    private val resourceTurner: ResourceTurner,
    private val citizenTurner: CitizenTurner,
) {
    private val log = logger {}
    val reports = mutableListOf<TurnReport>()

    var turn = 1
        private set

    fun collectAndExecuteNextTurnReport(): TurnReport {
        turn++
        log.info { "Taking turn $turn" }
        val resourceChanges = resourceTurner.executeAndReturnReport() // first resources, then happenings
        val citizenReport = citizenTurner.executeAndReturnReport()
        val happenings = happeningTurner.turn()
        return TurnReport(
            // FIXME clean list: if two different types, then accumulaten result!
            resourceChanges = resourceChanges.merge(citizenReport).changes,
            happenings = happenings,
        ).also { result ->
            reports += result
        }
    }

}
