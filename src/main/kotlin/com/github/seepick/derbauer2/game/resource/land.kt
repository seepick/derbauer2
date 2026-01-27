package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.building.OccupiesLand
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.logic.Z
import com.github.seepick.derbauer2.game.logic.find
import com.github.seepick.derbauer2.game.logic.z

val User.totalLandUse: Z
    get() {
        require(hasEntity(Land::class)) { "User has no Land resource" }
        return all.filterIsInstance<OccupiesLand>().sumOf { it.totalLandUse.value }.z
    }

val User.landOwned
    get() = resources.find<Land>().owned

val User.landAvailable get() = landOwned - totalLandUse
