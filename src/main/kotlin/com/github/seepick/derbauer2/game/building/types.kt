package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Asset
import com.github.seepick.derbauer2.game.core.HasEmoji
import com.github.seepick.derbauer2.game.core.HasLabels
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

class House : Building, StoresResource, HasLabels by Data, HasEmoji by Data {
    object Data : HasLabels, HasEmoji {
        override val labelSingular = "House"
    }

    override var _setOwnedInternal: Z = 0.z
    override val costsGold = Mechanics.houseCostsGold
    override val landUse = Mechanics.houseLandUse
    override val storableResourceClass = Citizen::class
    override val storageAmount = Mechanics.houseStoreCitizen.z

    override fun deepCopy() = House().also { it._setOwnedInternal = owned }
    override fun toString() = "House(owned=$owned)"
}

class Farm : Building, ProducesResourceOwnable, HasLabels by Data,
    HasEmoji by Data {
    object Data : HasLabels, HasEmoji {
        override val labelSingular = "Farm"
    }

    override var _setOwnedInternal: Z = 0.z
    override val costsGold = Mechanics.farmCostsGold
    override val landUse = Mechanics.farmLandUse
    override val producingResourceClass = Food::class
    override val producingResourceAmount = Mechanics.farmProduceFood

    override fun deepCopy() = Farm().also { it._setOwnedInternal = owned }
    override fun toString() = "Farm(owned=$owned)"
}

class Granary : Building, StoresResource, HasLabels by Data, HasEmoji by Data {
    object Data : HasLabels, HasEmoji {
        override val labelSingular = "Granary"
        override val labelPlural = "Granaries"
    }

    override var _setOwnedInternal: Z = 0.z
    override val costsGold = Mechanics.granaryCostsGold.z
    override val landUse = Mechanics.granaryLanduse.z
    override val storableResourceClass = Food::class
    override val storageAmount = Mechanics.granaryCapacity

    override fun deepCopy() = Granary().also { it._setOwnedInternal = owned }
    override fun toString() = "Granary(owned=$owned)"
}

// technology buildings
// military buildings
