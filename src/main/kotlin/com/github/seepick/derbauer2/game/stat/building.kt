package com.github.seepick.derbauer2.game.stat

import com.github.seepick.derbauer2.game.building.Building
import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.HasLabels
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.simpleNameEmojied
import com.github.seepick.derbauer2.game.view.ViewOrder

class Theater : Building, PreStatModifier, HasLabels by Data, ViewOrder by Data {
    object Data : HasLabels, ViewOrder {
        override val labelSingular = "Theater"
        override val viewOrder = ViewOrder.Building.Theater
    }

    override var _setOwnedInternal: Z = 0.z
    override val costsGold = Mechanics.theaterCostsGold
    override val landUse = Mechanics.theaterLanduse

    override fun calcModifierOrNull(user: User, statClass: StatKClass): Double? = when (statClass) {
        Happiness::class -> owned.toDouble() * Mechanics.theaterProducesHappiness
        else -> null
    }

    override fun deepCopy() = Theater().also { it._setOwnedInternal = owned }
    override fun toString() = "${this::class.simpleNameEmojied}(owned=$owned)"
}
