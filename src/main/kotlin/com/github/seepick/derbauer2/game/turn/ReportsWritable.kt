package com.github.seepick.derbauer2.game.turn

interface Reports {
    val all: List<TurnReport>
    fun last(): TurnReport
}

/** Assessement of historical data */
class ReportsWritable : Reports {

    private val _all = mutableListOf<TurnReport>()
    override val all: List<TurnReport> = _all

    fun add(report: TurnReport) {
        _all += report
    }

    // ... can be asked in the future about complex insights ...

    override fun last() = all.last()
}
