package com.github.seepick.derbauer2.game

import com.github.seepick.derbauer2.game.logic.Units
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.resource.Gold

var User.gold: Units
    get() = resource(Gold::class).owned
    set(value) {
        resource(Gold::class).owned = value
    }
