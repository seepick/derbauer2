package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.tech.AbstractTechTreeItem
import com.github.seepick.derbauer2.game.tech.Tech
import com.github.seepick.derbauer2.game.tech.TechStaticData
import com.github.seepick.derbauer2.game.tech.TechType

fun interface ResourceProductionModifier {
    fun modify(user: User, resource: Resource, source: Z): Z
}

class AgricultureTechTreeItem : AbstractTechTreeItem(
    data = AgricultureTech.Data,
    techBuilder = ::AgricultureTech,
)

class AgricultureTech(
    data: Data = Data,
) : Tech, TechStaticData by data {
    override fun deepCopy() = this

    object Data : TechStaticData {
        override val label = "Agriculture"
        override val type = TechType.AGRICULTURE
        override val requirements = emptySet<TechType>()
        override val costs = buildResourceChanges {
            add(Gold::class, Mechanics.techAgricultureCostsGold)
        }
    }
}

class CapitalismTechTreeItem : AbstractTechTreeItem(
    data = CapitalismTech.Data,
    techBuilder = ::CapitalismTech,
)

class CapitalismTech(
    data: Data = Data,
) : Tech, TechStaticData by data {
    override fun deepCopy() = this

    object Data : TechStaticData {
        override val label = "Capitalism"
        override val type = TechType.CAPITALISM
        override val requirements = emptySet<TechType>()
        override val costs = buildResourceChanges {
            add(Gold::class, Mechanics.techCapitalismCostsGold)
        }
    }
}
