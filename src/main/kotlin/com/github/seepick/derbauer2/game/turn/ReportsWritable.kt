package com.github.seepick.derbauer2.game.turn

interface Reports {
    fun add(report: TurnReport)
    fun last(): TurnReport
}

/** Assessement of historical data */
class ReportsImpl : Reports {

    private val all = mutableListOf<TurnReport>()

    override fun add(report: TurnReport) {
        all += report
    }

    // ... can be asked in the future about complex insights ...

    override fun last() = all.last()
}
