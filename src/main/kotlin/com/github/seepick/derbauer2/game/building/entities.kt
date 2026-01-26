package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.logic.Asset
import com.github.seepick.derbauer2.game.logic.Mechanics
import com.github.seepick.derbauer2.game.logic.Ownable
import com.github.seepick.derbauer2.game.logic.Units
import com.github.seepick.derbauer2.game.logic.units
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.ProducesResource
import com.github.seepick.derbauer2.game.resource.StoresResource

interface OccupiesLand : Ownable {
    val landUse: Units
    val totalLandUse get() = owned * landUse
}

interface Building : Asset, OccupiesLand {
    val costsGold: Units
}

class House(override var owned: Units = 0.units) : Building, StoresResource {
    override val labelSingular = "House"
    override val costsGold = Mechanics.houseCostsGold.units
    override val landUse = Mechanics.houseLandUse.units

    override val storableResource = Citizen::class
    override val storageAmount = Mechanics.houseStoreCitizen.units
}

class Farm(override var owned: Units = 0.units) : Building, ProducesResource {
    override val labelSingular = "Farm"
    override val costsGold = Mechanics.farmCostsGold.units
    override val landUse = Mechanics.farmLandUse.units

    override val producingResourceType = Food::class
    override val resourceProductionAmount get() = owned * Mechanics.farmProduceFood
}

class Granary(override var owned: Units = 0.units) : Building, StoresResource {
    override val labelSingular = "Granary"
    override val labelPlural = "Granaries"
    override val costsGold = Mechanics.granaryCostsGold.units
    override val landUse = Mechanics.granaryLanduse.units

    override val storableResource = Food::class
    override val storageAmount = Mechanics.granaryCapacity.units
}

// technology buildings
// military buildings
