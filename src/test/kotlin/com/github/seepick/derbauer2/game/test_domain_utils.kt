package com.github.seepick.derbauer2.game

import com.github.seepick.derbauer2.game.logic.Units
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Resource
import com.github.seepick.derbauer2.game.resource.ResourceChange
import io.kotest.matchers.collections.shouldContain

var User.gold: Units
    get() = resource(Gold::class).owned
    set(value) {
        resource(Gold::class).owned = value
    }

infix fun List<ResourceChange>.shouldContainChange(pair: Pair<Resource, Units>) {
    this.shouldContain(ResourceChange(pair.first, pair.second))
}
