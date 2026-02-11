package com.github.seepick.derbauer2.game.stat

import com.github.seepick.derbauer2.common.unit.Z
import com.github.seepick.derbauer2.common.unit.z
import com.github.seepick.derbauer2.game.building.Building
import com.github.seepick.derbauer2.game.core.HasLabels
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.view.ViewOrder
import com.github.seepick.derbauer2.game.view.simpleNameEmojied

class Theater : Building, PreStatModifier, HasLabels by Data, ViewOrder by Data {

    override var _setOwnedInternal: Z = 0.z
    override val costsGold = Mechanics.theaterCostsGold
    override val landUse = Mechanics.theaterLanduse
    override val label = Data.label

    override fun calcModifierOrNull(user: User, statClass: StatKClass): Double? = when (statClass) {
        Happiness::class -> owned.toDouble() * Mechanics.theaterProducesHappiness
        else -> null
    }

    override fun deepCopy() = Theater().also { it._setOwnedInternal = owned }
    override fun toString() = "${this::class.simpleNameEmojied}(owned=$owned)"

    object Data : HasLabels, ViewOrder {
        override val labelSingular = "Theater"
        override val viewOrder = ViewOrder.Building.Theater
    }
}
