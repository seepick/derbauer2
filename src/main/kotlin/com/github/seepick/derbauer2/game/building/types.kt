package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Asset
import com.github.seepick.derbauer2.game.core.HasEmoji
import com.github.seepick.derbauer2.game.core.HasLabel
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.Ownable
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.ProducesResourceOwnable
import com.github.seepick.derbauer2.game.resource.StoresResource
import kotlin.reflect.KClass

interface Building : Asset, OccupiesLand {
    val costsGold: Z
}

interface OccupiesLand : Ownable {
    val landUse: Z
    val totalLandUse get() = owned * landUse
}

interface BuildingReference {
    val buildingClass: KClass<out Building>
}

class House(override var _setOwnedOnlyByTx: Z = 0.z) : Building, StoresResource, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "House"
    }

    override val costsGold = Mechanics.houseCostsGold.z
    override val landUse = Mechanics.houseLandUse.z
    override val storableResourceClass = Citizen::class
    override val storageAmount = Mechanics.houseStoreCitizen.z
}

class Farm(override var _setOwnedOnlyByTx: Z = 0.z) : Building, ProducesResourceOwnable, HasLabel by Data,
    HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Farm"
    }

    override val costsGold = Mechanics.farmCostsGold.z
    override val landUse = Mechanics.farmLandUse.z
    override val producingResourceClass = Food::class
    override val producingResourceAmount = Mechanics.farmProduceFood.z
}

class Granary(override var _setOwnedOnlyByTx: Z = 0.z) : Building, StoresResource, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Granary"
        override val labelPlural = "Granaries"
    }

    override val costsGold = Mechanics.granaryCostsGold.z
    override val landUse = Mechanics.granaryLanduse.z

    override val storableResourceClass = Food::class
    override val storageAmount = Mechanics.granaryCapacity.z
}

// technology buildings
// military buildings
