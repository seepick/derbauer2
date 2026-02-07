package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.core.User

val User.turn: Turn get() = all.find<TurnStat>().turn
