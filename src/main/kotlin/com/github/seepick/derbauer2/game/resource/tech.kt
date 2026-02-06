package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.tech.AbstractTechItem
import com.github.seepick.derbauer2.game.tech.Tech
import com.github.seepick.derbauer2.game.tech.TechData

/**
 * CAVE: Register in [com.github.seepick.derbauer2.game.tech.DefaultTechItemRepo]
 */
object AgricultureTechItem : AbstractTechItem(data = AgricultureTech.Data) {
    override val techClass = AgricultureTech::class
}

class AgricultureTech : Tech, TechData by Data, ResourceProductionModifier {
    override val handlingResource = Food::class
    override fun modifyAmount(user: User, source: Zz) =
        source * Mechanics.techAgricultureFoodProductionMultiplier

    override fun deepCopy() = this

    object Data : TechData {
        override val label = "Agriculture"
        override val requirements = emptySet<TechData>()
        override val costs = buildResourceChanges {
            add(Gold::class, Mechanics.techAgricultureCostsGold)
        }
    }
}

object IrrigationTechItem : AbstractTechItem(data = IrrigationTech.Data) {
    override val techClass = IrrigationTech::class
}

class IrrigationTech : Tech, TechData by Data, ResourceProductionModifier {
    override val handlingResource = Food::class
    override fun modifyAmount(user: User, source: Zz) =
        source * Mechanics.techIrrigationFoodProductionMultiplier

    override fun deepCopy() = this

    object Data : TechData {
        override val label = "Irrigation"
        override val requirements = setOf(AgricultureTech.Data)
        override val costs = buildResourceChanges {
            add(Gold::class, Mechanics.techIrrigationCostsGold)
        }
    }
}

object CapitalismTechItem : AbstractTechItem(data = CapitalismTech.Data) {
    override val techClass = CapitalismTech::class
}

class CapitalismTech : Tech, TechData by Data {
    override fun deepCopy() = this

    object Data : TechData {
        override val label = "Capitalism"
        override val requirements = emptySet<TechData>()
        override val costs = buildResourceChanges {
            add(Gold::class, Mechanics.techCapitalismCostsGold)
        }
    }
}

object JunkFoodTechItem : AbstractTechItem(data = JunkFoodTech.Data) {
    override val techClass = JunkFoodTech::class
}

class JunkFoodTech : Tech, TechData by Data {
    override fun deepCopy() = this

    object Data : TechData {
        override val label = "Junk Food"
        override val requirements = setOf(AgricultureTech.Data)
        override val costs = buildResourceChanges {
            add(Gold::class, Mechanics.techJunkFoodCostsGold)
        }
    }
}

object WarfareTechItem : AbstractTechItem(data = WarfareTech.Data) {
    override val techClass = WarfareTech::class
}

class WarfareTech : Tech, TechData by Data {
    override fun deepCopy() = this

    object Data : TechData {
        override val label = "Warfare"
        override val requirements = emptySet<TechData>()
        override val costs = buildResourceChanges {
            add(Gold::class, Mechanics.techWarfareCostsGold)
        }
    }
}
