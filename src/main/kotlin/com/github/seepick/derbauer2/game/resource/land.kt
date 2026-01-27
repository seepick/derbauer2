package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.building.OccupiesLand
import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.User

val User.totalLandUse: Z
    get() {
        require(hasEntity(Land::class)) { "User has no Land resource" }
        return all.filterIsInstance<OccupiesLand>().sumOf { it.totalLandUse.value }.z
    }

val User.landOwned: Z
    get() = resources.find(Land::class).owned

val User.landAvailable get() = landOwned - totalLandUse
