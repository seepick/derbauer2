package com.github.seepick.derbauer2.game.logic

import com.github.seepick.derbauer2.game.building.OccupiesLand
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.resources

val User.totalLandUse
    get() = all.filterIsInstance<OccupiesLand>().sumOf { it.totalLandUse.value }.z

val User.landOwned
    get() = resources.letIfExists(Land::class) { it.owned } ?: 0.z

val User.landAvailable get() = landOwned - totalLandUse
