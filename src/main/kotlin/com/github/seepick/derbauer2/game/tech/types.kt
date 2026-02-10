package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.core.Entity
import com.github.seepick.derbauer2.game.resource.ResourceChanges
import com.github.seepick.derbauer2.game.view.ViewOrder
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

interface Tech : Entity, TechData {
    override val labelSingular get() = label
}

interface TechData {
    val label: String
    val description: String
    val requirements: Set<TechData>
    val costs: ResourceChanges
}

interface TechRef : TechData, ViewOrder {
    val techClass: KClass<out Tech>
    fun buildTech(): Tech
}

abstract class AbstractTechRef(
    data: TechData,
) : TechRef, TechData by data {
    override fun buildTech() = techClass.createInstance()
}
