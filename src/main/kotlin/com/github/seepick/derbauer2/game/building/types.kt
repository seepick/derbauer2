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
import com.github.seepick.derbauer2.game.view.ViewOrder
import kotlin.reflect.KClass

interface Building : Asset, OccupiesLand, ViewOrder {
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

class Tent : Building, StoresResource, HasLabels by Data, ViewOrder by Data {
    object Data : HasLabels, ViewOrder {
        override val labelSingular = "Tent"
        override val viewOrder = ViewOrder.Building.Tent
    }

    override var _setOwnedInternal: Z = 0.z
    override val costsGold = Mechanics.tentCostsGold
    override val landUse = Mechanics.tentLandUse
    override val storableResourceClass = Citizen::class
    override val storageAmount = Mechanics.tentStoresCitizen.z

    override fun deepCopy() = Tent().also { it._setOwnedInternal = owned }
    override fun toString() = "Tent(owned=$owned)"
}

class Field : Building, ProducesResource, HasLabels by Data, ViewOrder by Data {
    object Data : HasLabels, ViewOrder {
        override val labelSingular = "Field"
        override val viewOrder = ViewOrder.Building.Field
    }

    override var _setOwnedInternal: Z = 0.z
    override val costsGold = Mechanics.fieldCostsGold
    override val landUse = Mechanics.fieldLandUse
    override val producingResourceClass = Food::class
    override val produceResourceAmount = Mechanics.fieldProduceFood

    override fun deepCopy() = Field().also { it._setOwnedInternal = owned }
    override fun toString() = "Field(owned=$owned)"
}

class Granary : Building, StoresResource, HasLabels by Data, ViewOrder by Data {
    object Data : HasLabels, ViewOrder {
        override val labelSingular = "Granary"
        override val labelPlural = "Granaries"
        override val viewOrder = ViewOrder.Building.Granary
    }

    override var _setOwnedInternal: Z = 0.z
    override val costsGold = Mechanics.granaryCostsGold
    override val landUse = Mechanics.granaryLanduse
    override val storableResourceClass = Food::class
    override val storageAmount = Mechanics.granaryCapacity

    override fun deepCopy() = Granary().also { it._setOwnedInternal = owned }
    override fun toString() = "Granary(owned=$owned)"
}
