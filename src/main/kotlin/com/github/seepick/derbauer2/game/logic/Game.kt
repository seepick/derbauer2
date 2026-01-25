package com.github.seepick.derbauer2.game.logic

import com.github.seepick.derbauer2.game.resource.Resource
import com.github.seepick.derbauer2.game.turn.TurnTaker

data class TurnReport(
    val turn: Int,
    val resourceProduction: List<Pair<Resource, Units>>,
)

class Game {
    val user = User()

    private val turner = TurnTaker()
    val turn = turner.turn
    val reports = mutableListOf<TurnReport>()

    fun nextTurn() {
        reports += turner.takeTurn(this)
    }
}
