package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.logic.UserResult
import com.github.seepick.derbauer2.game.logic.landAvailable
import com.github.seepick.derbauer2.game.resource.Gold

val User.buildings get() = all.filterIsInstance<Building>()

fun User.build(building: Building): UserResult {
    val gold = resource(Gold::class)
    if (gold.owned < building.costsGold) {
        return UserResult.Fail.InsufficientResources("Not enough gold")
    }
    if(landAvailable < building.landUse) {
        return UserResult.Fail.InsufficientResources("Not enough land")
    }
    building.owned += 1
    gold.owned -= building.costsGold
    return UserResult.Success
}
