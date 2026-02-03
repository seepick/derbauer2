package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.core.Entity
import com.github.seepick.derbauer2.game.resource.ResourceChanges

interface TechData {
    val id get() = label // nice hack ;)
    val label: String
    val requirements: Set<TechData>
    val costs: ResourceChanges
}

interface TechItem : TechData {
    var state: TechState
    val isResearched get() = state is TechState.Researched
    val isUnresearched get() = state is TechState.Unresearched

    fun buildTech(): Tech
    fun buildTechAndUpdateState(): Pair<TechItem, Tech> {
        require(state is TechState.Unresearched)
        val tech = buildTech()
        state = TechState.Researched(tech)
        return this to tech
    }
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
    private val techBuilder: () -> Tech,
) : TechItem, TechData by data {
    override var state: TechState = TechState.Unresearched
    override fun buildTech() = techBuilder()
}
