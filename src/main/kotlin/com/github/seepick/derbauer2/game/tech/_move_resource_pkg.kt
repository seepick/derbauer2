package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.buildResourceChanges


class AgricultureTechTreeItem : AbstractTechTreeItem(
    data = AgricultureTech.Data,
    techBuilder = { AgricultureTech() },
)

//fun interface ResourceProductionModifier {
//    fun modify(user: User/*read-only*/, resource: Resource, source: Z): Z
//}

class AgricultureTech(
    data: Data = Data,
) : Tech, TechStaticData by data { // FIXME implement modifier which is considered in turner; can enable Features
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
