package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.logic.Entity
import com.github.seepick.derbauer2.game.logic.Food
import com.github.seepick.derbauer2.game.logic.Mechanics
import com.github.seepick.derbauer2.game.logic.ProducesResource
import com.github.seepick.derbauer2.game.logic.Units
import com.github.seepick.derbauer2.game.logic.units

interface Building : Entity {
    var units: Units
    val costsGold: Int
}

class House : Building {
    override var units = 0.units
    override val labelSingular = "House"
    override val costsGold = Mechanics.houseCostsGold
    // TODO holds X amount citizens
}

class Farm : Building, ProducesResource {
    override var units = 0.units
    override val labelSingular = "Farm"
    override val costsGold = Mechanics.farmCostsGold
    override val resourceType = Food::class
    override fun produce() = units.single * Mechanics.farmProduceFood
}

// TODO storage?
