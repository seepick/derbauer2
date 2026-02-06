package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.core.Entity
import com.github.seepick.derbauer2.game.resource.ResourceChanges
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

interface TechData {
    val label: String
    val requirements: Set<TechData>
    val costs: ResourceChanges
}

interface TechItem : TechData {
    val techClass: KClass<out Tech>
    fun buildTech(): Tech
}

interface Tech : Entity, TechData {
    override val labelSingular get() = label
}

sealed interface TechState {
    object Unresearched : TechState
    class Researched(val tech: Tech) : TechState
}

abstract class AbstractTechItem(
    data: TechData,
) : TechItem, TechData by data {
    override fun buildTech() = techClass.createInstance()
}
