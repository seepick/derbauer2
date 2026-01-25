package com.github.seepick.derbauer2.game.logic

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
