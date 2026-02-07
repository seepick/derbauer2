package com.github.seepick.derbauer2.game.stat

import com.github.seepick.derbauer2.game.building.Building
import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.HasEmoji
import com.github.seepick.derbauer2.game.core.HasLabels
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.simpleNameEmojied

class Theater : Building, StatModifier, HasLabels by Data, HasEmoji by Data {
    object Data : HasLabels, HasEmoji {
        override val labelSingular = "Theater"
    }

    override var _setOwnedInternal: Z = 0.z
    override val costsGold = Mechanics.theaterCostsGold
    override val landUse = Mechanics.theaterLanduse

    override fun deepCopy() = Theater().also { it._setOwnedInternal = owned }
    override fun toString() = "${this::class.simpleNameEmojied}(owned=$owned)"
    override fun modification(statClass: StatKClass): Double? {
        if (statClass != Happiness::class) {
            return null
        }
        return owned.toDouble() * Mechanics.theaterProducesHappiness
    }
}
