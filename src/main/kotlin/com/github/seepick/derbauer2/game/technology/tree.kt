package com.github.seepick.derbauer2.game.technology

import com.github.seepick.derbauer2.game.core.Entity
import com.github.seepick.derbauer2.game.resource.ResourceChanges

/*
develop a format based on YAML

---
technologies:
    - id: advanced_agriculture
        label: Advanced Agriculture
        prerequisites: []
    - id: irrigation
        label: Irrigation
        prerequisites:
        - advanced_agriculture
    - id: wheel
        label: Wheel
        prerequisites:
        - advanced_agriculture
---

tree structure, outgoing nodes

 */

enum class TechType {
    AGRICULTURE, // +food production
    HOUSES,      // upgrade from tents
    POTTERY,     // enable granaries
}

interface Technology : Entity, TechnologyStaticData {
    // check end turn, enable if not yet enabled
    // used as precondition filter for actions/etc.
    override val labelSingular get() = label
}

interface TechnologyStaticData {
    val label: String
    val type: TechType
    val requirements: Set<TechType>
    val costs: ResourceChanges
}

class Agriculture(data: Data = Data) : Technology, TechnologyStaticData by data {
    override fun deepCopy() = this // immutable

    object Data : TechnologyStaticData {
        override val label = "Agriculture"
        override val type = TechType.AGRICULTURE
        override val requirements = emptySet<TechType>()
        override val costs = TODO("")
    }
}

interface TechnologyDescriptor

class TechTree {
    fun getAvailable(): List<TechnologyDescriptor> {
        TODO("Not yet implemented")
    }
}
