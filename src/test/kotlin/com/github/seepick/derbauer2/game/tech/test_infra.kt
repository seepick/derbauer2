package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.resource.ResourceChanges
import kotlin.reflect.KClass

open class TestTech : Tech {
    override val label = "label"
    override val description = "description"
    override val requirements = emptySet<TechData>()
    override val costs = ResourceChanges.empty
    override fun deepCopy() = this
}

class TestTech1 : TestTech() {
    override val label = "tech1"
}

class TestTech2 : TestTech() {
    override val label = "tech2"
}

fun newTechItem(
    label: String = "tech label",
    techClass: KClass<out Tech> = TestTech::class,
    costs: ResourceChanges = ResourceChanges.empty,
    requirements: Set<TechData> = emptySet(),
) = object : TechRef {
    override val label = label
    override val description = "description"
    override val requirements = requirements
    override val costs = costs
    override val techClass = techClass

    override fun buildTech(): Tech {
        return object : Tech {
            override val label = label
            override val description = "description"
            override val requirements = requirements
            override val costs = costs
            override fun deepCopy() = this
        }
    }
}
