package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.Texts
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.GlobalResourceProductionBonus
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Knowledge
import com.github.seepick.derbauer2.game.resource.buildResourceChanges
import com.github.seepick.derbauer2.game.resource.citizen
import com.github.seepick.derbauer2.game.stat.Happiness
import com.github.seepick.derbauer2.game.stat.PreStatModifier
import com.github.seepick.derbauer2.game.stat.StatKClass
import com.github.seepick.derbauer2.game.view.ViewOrder

/**
 * CAVE: Register Refs in [DefaultTechRefRegistry]!
 */
class AgricultureTech : Tech, TechData by Data, GlobalResourceProductionBonus {
    override val handlingResource = Food::class
    override fun productionBonus(user: User) = Mechanics.techAgricultureFoodProductionBonus

    override fun deepCopy() = this

    object Data : TechData {
        override val label = "Agriculture"
        override val description = Texts.techItemAgriculture
        override val requirements = emptySet<TechData>()
        override val costs = buildResourceChanges {
            add(Knowledge::class, Mechanics.techAgricultureCostsKnowledge)
            add(Gold::class, Mechanics.techAgricultureCostsGold)
        }
    }

    object Ref : AbstractTechRef(data = Data) {
        override val techClass = AgricultureTech::class
        override val viewOrder = ViewOrder.Tech.Agriculture
    }
}

class IrrigationTech : Tech, TechData by Data, GlobalResourceProductionBonus {
    override val handlingResource = Food::class
    override fun productionBonus(user: User) = Mechanics.techIrrigationFoodProductionBonus
    override fun deepCopy() = this

    object Data : TechData {
        override val label = "Irrigation"
        override val description = Texts.techItemIrrigation
        override val requirements = setOf(AgricultureTech.Data)
        override val costs = buildResourceChanges {
            add(Knowledge::class, Mechanics.techIrrigationCostsKnowledge)
            add(Gold::class, Mechanics.techIrrigationCostsGold)
        }
    }

    object Ref : AbstractTechRef(data = Data) {
        override val techClass = IrrigationTech::class
        override val viewOrder = ViewOrder.Tech.Irrigation
    }

}


class CapitalismTech : Tech, TechData by Data, PreStatModifier {

    override fun calcModifierOrNull(user: User, statClass: StatKClass): Double? =
        when (statClass) {
            Happiness::class -> user.citizen.toDouble() * Mechanics.techCapitalismHappinessPerCitizenMultiplier
            else -> null
        }

    override fun deepCopy() = this

    object Data : TechData {
        override val label = "Capitalism"
        override val description = Texts.techItemCapitalism
        override val requirements = emptySet<TechData>()
        override val costs = buildResourceChanges {
            add(Knowledge::class, Mechanics.techCapitalismCostsKnowledge)
            add(Gold::class, Mechanics.techCapitalismCostsGold)
        }
    }

    object Ref : AbstractTechRef(data = Data) {
        override val techClass = CapitalismTech::class
        override val viewOrder = ViewOrder.Tech.Capitalism
    }
}
