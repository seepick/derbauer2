package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.Texts
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.tech.AbstractTechRef
import com.github.seepick.derbauer2.game.tech.Tech
import com.github.seepick.derbauer2.game.tech.TechData

/**
 * CAVE: Register in [com.github.seepick.derbauer2.game.tech.DefaultTechItemRepo]
 */
object AgricultureTechRef : AbstractTechRef(data = AgricultureTech.Data) {
    override val techClass = AgricultureTech::class
}

class AgricultureTech : Tech, TechData by Data, GlobalResourceProductionModifier {
    override val handlingResource = Food::class
    override fun modifyAmount(user: User, source: Zz) =
        source * Mechanics.techAgricultureFoodProductionMultiplier

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
}

object IrrigationTechRef : AbstractTechRef(data = IrrigationTech.Data) {
    override val techClass = IrrigationTech::class
}

class IrrigationTech : Tech, TechData by Data, GlobalResourceProductionModifier {
    override val handlingResource = Food::class
    override fun modifyAmount(user: User, source: Zz) =
        source * Mechanics.techIrrigationFoodProductionMultiplier

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
}

object CapitalismTechRef : AbstractTechRef(data = CapitalismTech.Data) {
    override val techClass = CapitalismTech::class
}

class CapitalismTech : Tech, TechData by Data {
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
}
