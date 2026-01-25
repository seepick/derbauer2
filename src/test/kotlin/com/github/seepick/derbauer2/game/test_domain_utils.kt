package com.github.seepick.derbauer2.game

import com.github.seepick.derbauer2.game.logic.Game
import com.github.seepick.derbauer2.game.logic.Gold
import com.github.seepick.derbauer2.game.logic.Units

var Game.gold: Units
    get() = user.resource(Gold::class).owned
    set(value) {
        user.resource(Gold::class).owned = value
    }
