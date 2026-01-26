package com.github.seepick.derbauer2.game

import com.github.seepick.derbauer2.game.logic.Ownable
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.resource

var User.gold
    get() = resource(Gold::class).owned
    set(value) { // FIXME make val
        resource(Gold::class).ownedForTest = value
    }

val User.food
    get() = resource(Food::class).owned

@Suppress("DEPRECATION")
var Ownable.ownedForTest
    get() = owned
    set(value) {
        _setOwnedOnlyByTx = value
    }
