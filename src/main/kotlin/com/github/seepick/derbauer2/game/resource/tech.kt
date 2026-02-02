package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.tech.AbstractTechItem
import com.github.seepick.derbauer2.game.tech.Tech
import com.github.seepick.derbauer2.game.tech.TechStaticData

/**
 * CAVE: Register in [com.github.seepick.derbauer2.game.tech.DefaultTechItemRepo]
 */
object AgricultureTechItem : AbstractTechItem(
    data = AgricultureTech.Data,
    techBuilder = ::AgricultureTech,
)

class AgricultureTech : Tech, TechStaticData by Data, ResourceProductionModifier {
    override val handlingResource = Food::class
    override fun modifyAmount(user: User, source: Zz) =
        source * Mechanics.techAgricultureFoodProductionMultiplier

    override fun deepCopy() = this

    object Data : TechStaticData {
        override val label = "Agriculture"
        override val requirements = emptySet<TechStaticData>()
        override val costs = buildResourceChanges {
            add(Gold::class, Mechanics.techAgricultureCostsGold)
        }
    }
}

object IrrigationTechItem : AbstractTechItem(
    data = IrrigationTech.Data,
    techBuilder = ::IrrigationTech,
)

class IrrigationTech : Tech, TechStaticData by Data, ResourceProductionModifier {
    override val handlingResource = Food::class
    override fun modifyAmount(user: User, source: Zz) =
        source * Mechanics.techIrrigationFoodProductionMultiplier

    override fun deepCopy() = this

    object Data : TechStaticData {
        override val label = "Irrigation"
        override val requirements = setOf(AgricultureTech.Data)
        override val costs = buildResourceChanges {
            add(Gold::class, Mechanics.techIrrigationCostsGold)
        }
    }
}

object CapitalismTechItem : AbstractTechItem(
    data = CapitalismTech.Data,
    techBuilder = ::CapitalismTech,
)

class CapitalismTech : Tech, TechStaticData by Data {
    override fun deepCopy() = this

    object Data : TechStaticData {
        override val label = "Capitalism"
        override val requirements = emptySet<TechStaticData>()
        override val costs = buildResourceChanges {
            add(Gold::class, Mechanics.techCapitalismCostsGold)
        }
    }
}

object JunkFoodTechItem : AbstractTechItem(
    data = JunkFoodTech.Data,
    techBuilder = ::JunkFoodTech,
)

class JunkFoodTech : Tech, TechStaticData by Data {
    override fun deepCopy() = this

    object Data : TechStaticData {
        override val label = "Junk Food"
        override val requirements = setOf(AgricultureTech.Data)
        override val costs = buildResourceChanges {
            add(Gold::class, Mechanics.techJunkFoodCostsGold)
        }
    }
}

object WarfareTechItem : AbstractTechItem(
    data = WarfareTech.Data,
    techBuilder = ::WarfareTech,
)

class WarfareTech : Tech, TechStaticData by Data {
    override fun deepCopy() = this

    object Data : TechStaticData {
        override val label = "Warfare"
        override val requirements = emptySet<TechStaticData>()
        override val costs = buildResourceChanges {
            add(Gold::class, Mechanics.techWarfareCostsGold)
        }
    }
}
