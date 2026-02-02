package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.core.Entity
import com.github.seepick.derbauer2.game.resource.ResourceChanges

interface Tech : Entity, TechStaticData {
    override val labelSingular get() = label
}

interface TechStaticData {
    val label: String
    val requirements: Set<TechStaticData>
    val costs: ResourceChanges
}
