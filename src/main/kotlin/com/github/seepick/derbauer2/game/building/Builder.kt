package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.logic.landAvailable
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land

class Builder(
    private val user: User
) {
    // TODO unify with Trader
    fun build(building: Building): BuildResult {
        // TODO building costs Resource_s_ (not Gold)
        val gold = user.resource(Gold::class)
        if (gold.owned < building.costsGold) {
            return BuildResult.InsufficientResources
        }
        val land = user.resource(Land::class)
        if(user.landAvailable < building.landUse) {
            return BuildResult.InsufficientResources
        }
        building.owned += 1
        gold.owned -= building.costsGold
        return BuildResult.Success
    }
}

sealed interface BuildResult {
    object Success : BuildResult
    object InsufficientResources : BuildResult
}
