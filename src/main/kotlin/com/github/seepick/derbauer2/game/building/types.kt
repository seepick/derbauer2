package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Asset
import com.github.seepick.derbauer2.game.core.HasLabels
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.Ownable
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.ProducesResource
import com.github.seepick.derbauer2.game.resource.StoresResource
import kotlin.reflect.KClass

interface Building : Asset, OccupiesLand {
    // TODO change `costsGold` to a more generic `costs` that can include multiple resources
    val costsGold: Z
}

interface OccupiesLand : Ownable {
    val landUse: Z
    val totalLandUse get() = owned * landUse
}

interface BuildingReference {
    val buildingClass: KClass<out Building>
}

class Tent : Building, StoresResource, HasLabels by Data {
    object Data : HasLabels {
        override val labelSingular = "Tent"
    }

    override var _setOwnedInternal: Z = 0.z
    override val costsGold = Mechanics.tentCostsGold
    override val landUse = Mechanics.tentLandUse
    override val storableResourceClass = Citizen::class
    override val storageAmount = Mechanics.tentStoresCitizen.z

    override fun deepCopy() = Tent().also { it._setOwnedInternal = owned }
    override fun toString() = "Tent(owned=$owned)"
}

class Farm : Building, ProducesResource, HasLabels by Data {
    object Data : HasLabels {
        override val labelSingular = "Farm"
    }

    override var _setOwnedInternal: Z = 0.z
    override val costsGold = Mechanics.farmCostsGold
    override val landUse = Mechanics.farmLandUse
    override val producingResourceClass = Food::class
    override val produceResourceAmount = Mechanics.farmProduceFood

    override fun deepCopy() = Farm().also { it._setOwnedInternal = owned }
    override fun toString() = "Farm(owned=$owned)"
}

class Granary : Building, StoresResource, HasLabels by Data {
    object Data : HasLabels {
        override val labelSingular = "Granary"
        override val labelPlural = "Granaries"
    }

    override var _setOwnedInternal: Z = 0.z
    override val costsGold = Mechanics.granaryCostsGold
    override val landUse = Mechanics.granaryLanduse
    override val storableResourceClass = Food::class
    override val storageAmount = Mechanics.granaryCapacity

    override fun deepCopy() = Granary().also { it._setOwnedInternal = owned }
    override fun toString() = "Granary(owned=$owned)"
}
