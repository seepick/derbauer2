package com.github.seepick.derbauer2.game.logic

import com.github.seepick.derbauer2.game.building.OccupiesLand
import com.github.seepick.derbauer2.game.resource.Land

val User.totalLandUse
    get() = all.filterIsInstance<OccupiesLand>().sumOf { it.totalLandUse.single }.units

val User.landOwned
    get() = letIfExists(Land::class) { it.owned } ?: 0.units

val User.landAvailable get() = landOwned - totalLandUse
