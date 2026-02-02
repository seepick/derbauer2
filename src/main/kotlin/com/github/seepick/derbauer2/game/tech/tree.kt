package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.core.Entity
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.Resource
import com.github.seepick.derbauer2.game.resource.ResourceChanges

enum class TechType {
    AGRICULTURE, // +food production
    HOUSES,      // upgrade from tents
    POTTERY,     // enable granaries
}

interface Tech : Entity, TechStaticData {
    // check end turn, enable if not yet enabled
    // used as precondition filter for actions/etc.
    override val labelSingular get() = label
}

interface TechStaticData {
    val label: String
    val type: TechType
    val requirements: Set<TechType>
    val costs: ResourceChanges
}

fun interface ResourceProductionModifier {
    fun modify(user: User/*read-only*/, resource: Resource, source: Z): Z
}

class AgricultureTech(data: Data = Data) : Tech, TechStaticData by data {
    override fun deepCopy() = this // immutable

    object Data : TechStaticData {
        override val label = "Agriculture"
        override val type = TechType.AGRICULTURE
        override val requirements = emptySet<TechType>()
        override val costs = TODO("")
    }
}

interface TechDescriptor

class TechTree {
    fun getAvailable(): List<TechDescriptor> {
        TODO("Not yet implemented")
    }
}
