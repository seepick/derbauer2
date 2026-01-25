package com.github.seepick.derbauer2.game.logic

import com.github.seepick.derbauer2.game.building.Building

class Game {
    var turn = 1
        private set
    val user = User()

    fun nextTurn() {
        user.buildings.filterIsInstance<ProducesResource>().forEach { producer ->
            user.resource(producer.resourceType).units += producer.produce()
        }
        // consume food per citizen
        turn++
    }

    fun buyBuilding(building: Building): BuyResult {
        // FIXME building costs Resource_s_ (not Gold)
        val gold = user.resources.filterIsInstance<Gold>().first()
        if(gold.units < building.costsGold) {
            return BuyResult.NotEnoughGold
        }
        building.units += 1
        gold.units -= building.costsGold
        return BuyResult.Success
    }
}