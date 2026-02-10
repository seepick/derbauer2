package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.resource.ResourceChanges

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

    // a temporary solution, as order is: first executing logic (using this), before instantiating+adding report...
    override fun last() = all.lastOrNull() ?: TurnReport.empty()

    companion object // for extensions
}

fun TurnReport.Companion.empty() = TurnReport(
    turn = Turn(1),
    resourceChanges = ResourceChanges.empty,
    happenings = emptyList(),
    featurePages = emptyList(),
    actions = emptyList(),
)
