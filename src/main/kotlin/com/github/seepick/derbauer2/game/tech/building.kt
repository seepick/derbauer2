package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.building.Building
import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.HasEmoji
import com.github.seepick.derbauer2.game.core.HasLabels
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.simpleNameEmojied
import com.github.seepick.derbauer2.game.resource.Knowledge
import com.github.seepick.derbauer2.game.resource.ProducesResource

class School : Building, ProducesResource, HasLabels by Data, HasEmoji by Data {
    object Data : HasLabels, HasEmoji {
        override val labelSingular = "School"
        override val labelPlural = "Schools"
    }

    override var _setOwnedInternal: Z = 0.z
    override val costsGold = Mechanics.schoolCostsGold
    override val landUse = Mechanics.schoolLanduse

    override fun deepCopy() = School().also { it._setOwnedInternal = owned }
    override fun toString() = "${this::class.simpleNameEmojied}(owned=$owned)"
    override val producingResourceClass = Knowledge::class
    override val producingResourceAmount = Mechanics.schoolProduceKnowledge
}
