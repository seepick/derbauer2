package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.building.OccupiesLand
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.logic.letIfExists
import com.github.seepick.derbauer2.game.logic.z

val User.totalLandUse
    get() = all.filterIsInstance<OccupiesLand>().sumOf { it.totalLandUse.value }.z

val User.landOwned
    get() = resources.letIfExists(Land::class) { it.owned } ?: 0.z

val User.landAvailable get() = landOwned - totalLandUse
