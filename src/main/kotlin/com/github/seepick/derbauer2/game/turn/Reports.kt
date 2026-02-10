package com.github.seepick.derbauer2.game.turn

interface Reports {
    val all: List<TurnReport>

    fun add(report: TurnReport)
    fun last(): TurnReport

    companion object // for extensions
}

/** Assessement of historical data */
class ReportsImpl : Reports {

    override val all = mutableListOf<TurnReport>()

    override fun add(report: TurnReport) {
        all += report
    }


    // ... can be asked in the future about complex insights ...

    override fun last() = all.last()

    companion object // for extensions
}

fun Reports.lastTurnNumber() = all.lastOrNull()?.turn?.number ?: 0
