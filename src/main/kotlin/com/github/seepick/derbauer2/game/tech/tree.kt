package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.requireAllNonNegative

class TechTree(
    private val user: User,
    val all: List<TechTreeItem>,
) {
    init {
        all.flatMap { it.costs.changes }.requireAllNonNegative()
    }

    fun filterResearchableItems(): List<TechTreeItem> =
        all.filter {
            it.state is TechState.Unresearched
            // TODO && all prerequisites researched
        }
}

interface TechTreeItem : TechStaticData {
    var state: TechState

    fun buildTech(): Tech
}

abstract class AbstractTechTreeItem(
    data: TechStaticData,
    private val techBuilder: () -> Tech,
) : TechTreeItem, TechStaticData by data {

    override var state: TechState = TechState.Unresearched

    override fun buildTech(): Tech {
        val tech = techBuilder()
        state = TechState.Researched(tech)
        return tech
    }
}

sealed interface TechState {
    object Unresearched : TechState
    class Researched(val tech: Tech) : TechState
}
