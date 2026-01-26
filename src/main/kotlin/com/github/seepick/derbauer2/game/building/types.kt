package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.logic.Asset
import com.github.seepick.derbauer2.game.logic.Mechanics
import com.github.seepick.derbauer2.game.logic.Ownable
import com.github.seepick.derbauer2.game.logic.Zp
import com.github.seepick.derbauer2.game.logic.zp
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.ProducesResourceOwnable
import com.github.seepick.derbauer2.game.resource.StoresResource
import kotlin.reflect.KClass

interface OccupiesLand : Ownable {
    val landUse: Zp
    val totalLandUse get() = owned * landUse
}

interface Building : Asset, OccupiesLand {
    val costsGold: Zp
}

interface BuildingReference {
    val buildingClass: KClass<out Building>
}

class House(override var _setOwnedOnlyByTx: Zp = 0.zp) : Building, StoresResource {
    override val labelSingular = "House"
    override val costsGold = Mechanics.houseCostsGold.zp
    override val landUse = Mechanics.houseLandUse.zp

    override val storableResourceClass = Citizen::class
    override val storageAmount = Mechanics.houseStoreCitizen.zp
}

class Farm(override var _setOwnedOnlyByTx: Zp = 0.zp) : Building, ProducesResourceOwnable {
    override val labelSingular = "Farm"
    override val costsGold = Mechanics.farmCostsGold.zp
    override val landUse = Mechanics.farmLandUse.zp

    override val producingResourceClass = Food::class
    override val producingResourceAmount = Mechanics.farmProduceFood.zp
}

class Granary(override var _setOwnedOnlyByTx: Zp = 0.zp) : Building, StoresResource {
    override val labelSingular = "Granary"
    override val labelPlural = "Granaries"
    override val costsGold = Mechanics.granaryCostsGold.zp
    override val landUse = Mechanics.granaryLanduse.zp

    override val storableResourceClass = Food::class
    override val storageAmount = Mechanics.granaryCapacity.zp
}

// technology buildings
// military buildings
