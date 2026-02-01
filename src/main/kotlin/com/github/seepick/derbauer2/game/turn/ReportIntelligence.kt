package com.github.seepick.derbauer2.game.turn

/** Assessement of historical data */
class ReportIntelligence { // TODO move data storage into User; only logic here
    private val stored = mutableListOf<TurnReport>()

    fun addReport(report: TurnReport) {
        stored += report
    }

    // ... can be asked in the future about complex insights ...

    fun last() = stored.last()
}
