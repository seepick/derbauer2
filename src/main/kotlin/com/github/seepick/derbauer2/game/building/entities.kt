package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.logic.Asset
import com.github.seepick.derbauer2.game.logic.Food
import com.github.seepick.derbauer2.game.logic.Mechanics
import com.github.seepick.derbauer2.game.logic.ProducesResource
import com.github.seepick.derbauer2.game.logic.StoresResource
import com.github.seepick.derbauer2.game.logic.Units
import com.github.seepick.derbauer2.game.logic.units

interface Building : Asset {
    var owned: Units
    val costsGold: Int
}

class House(override var owned: Units = 0.units) : Building {
    override val labelSingular = "House"
    override val costsGold = Mechanics.houseCostsGold
    // TODO holds X amount citizens
}

class Farm(override var owned: Units = 0.units) : Building, ProducesResource {
    override val labelSingular = "Farm"
    override val costsGold = Mechanics.farmCostsGold
    override val resourceType = Food::class
    override fun produce() = owned * Mechanics.farmProduceFood
}

class Granary(override var owned: Units = 0.units) : Building, StoresResource {
    override val labelSingular = "Granary"
    override val labelPlural = "Granaries"
    override val costsGold = Mechanics.granaryCostsGold
    override val storableResource = Food::class
    override val capacity = Mechanics.granaryCapacity
    override val totalCapacity get() = owned * capacity
}

// storage buildings
// technology buildings
// military buildings
