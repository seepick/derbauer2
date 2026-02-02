package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.tech.AbstractTechTreeItem
import com.github.seepick.derbauer2.game.tech.Tech
import com.github.seepick.derbauer2.game.tech.TechStaticData
import com.github.seepick.derbauer2.game.tech.TechType

class AgricultureTechTreeItem : AbstractTechTreeItem(
    data = AgricultureTech.Data,
    techBuilder = ::AgricultureTech,
)

class AgricultureTech(
    data: Data = Data,
) : Tech, TechStaticData by data, ResourceProductionModifier {

    override val handlingResource = Food::class
    override fun modifyAmount(user: User, source: Zz) =
        source * Mechanics.techAgricultureFoodProductionMultiplier

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

class IrrigationTechTreeItem : AbstractTechTreeItem(
    data = IrrigationTech.Data,
    techBuilder = ::IrrigationTech,
)

class IrrigationTech(
    data: Data = Data,
) : Tech, TechStaticData by data, ResourceProductionModifier {

    override val handlingResource = Food::class
    override fun modifyAmount(user: User, source: Zz) =
        source * Mechanics.techIrrigationFoodProductionMultiplier

    override fun deepCopy() = this

    object Data : TechStaticData {
        override val label = "Irrigation"
        override val type = TechType.IRRIGATION
        override val requirements = setOf(TechType.AGRICULTURE)
        override val costs = buildResourceChanges {
            add(Gold::class, Mechanics.techIrrigationCostsGold)
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

class JunkFoodTechTreeItem : AbstractTechTreeItem(
    data = JunkFoodTech.Data,
    techBuilder = ::JunkFoodTech,
)

class JunkFoodTech(
    data: Data = Data,
) : Tech, TechStaticData by data {
    override fun deepCopy() = this

    object Data : TechStaticData {
        override val label = "Junk Food"
        override val type = TechType.JUNK_FOOD
        override val requirements = setOf(TechType.AGRICULTURE)
        override val costs = buildResourceChanges {
            add(Gold::class, Mechanics.techJunkFoodCostsGold)
        }
    }
}

class WarfareTechTreeItem : AbstractTechTreeItem(
    data = WarfareTech.Data,
    techBuilder = ::WarfareTech,
)

class WarfareTech(
    data: Data = Data,
) : Tech, TechStaticData by data {
    override fun deepCopy() = this

    object Data : TechStaticData {
        override val label = "Warfare"
        override val type = TechType.WARFARE
        override val requirements = emptySet<TechType>()
        override val costs = buildResourceChanges {
            add(Gold::class, Mechanics.techWarfareCostsGold)
        }
    }
}
