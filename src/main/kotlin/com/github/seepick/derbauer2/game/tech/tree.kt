package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.resource.requireAllNonNegative

class TechTree(
    val all: List<TechTreeItem>,
) {
    private val byType = all.associateBy { it.type }

    init {
        all.flatMap { it.costs.changes }.requireAllNonNegative()
        // TODO check for cycles in prerequisites
    }

    fun filterResearchableItems(): List<TechTreeItem> =
        all.filter {
            it.state is TechState.Unresearched &&
                    hasResearchedAll(it.requirements)
        }

    private fun hasResearchedAll(requirements: Set<TechType>): Boolean =
        requirements.all { req ->
            val item = byType[req] ?: error("woops, no tech tree item for tech type $req")
            item.state is TechState.Researched && hasResearchedAll(item.requirements)
        }
}

interface TechTreeItem : TechStaticData {
    var state: TechState

    fun buildTech(): Tech

    fun updateResearchedState() {
        require(state is TechState.Unresearched)
        state = TechState.Researched(buildTech())
    }
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

fun TechTree.toPrettyString(): String {
    this.all.forEach {
        it.requirements
    }
    // FIXME let AI implement this
    return ""
}