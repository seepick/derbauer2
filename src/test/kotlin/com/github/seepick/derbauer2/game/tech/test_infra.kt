package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.resource.ResourceChanges

fun newTechItem(
    label: String = "tech label",
    costs: ResourceChanges = ResourceChanges.empty,
    requirements: Set<TechData> = emptySet(),
) = object : TechItem {
    override var state: TechState = TechState.Unresearched
    override val label = label
    override val requirements = requirements
    override val costs = costs
    override fun buildTech(): Tech {
        return object : Tech {
            override fun deepCopy() = this
            override val label = label
            override val requirements = requirements
            override val costs = costs
        }
    }
}
