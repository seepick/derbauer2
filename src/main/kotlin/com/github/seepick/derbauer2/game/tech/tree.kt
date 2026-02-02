package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.resource.requireAllNonNegative

class TechTree(
    val all: List<TechItem>,
) {
    private val byLabel = all.associateBy { it.label } // nice hack ;)

    init {
        all.flatMap { it.costs.changes }.requireAllNonNegative()

        all.forEach {
            it.requirements // TODO check for cycles in requirements
        }
    }

    fun filterResearchableItems(): List<TechItem> =
        all.filter {
            it.state is TechState.Unresearched &&
                    hasResearchedAll(it.requirements)
        }

    private fun hasResearchedAll(requirements: Set<TechStaticData>): Boolean =
        requirements.all { req ->
            val item = byLabel[req.label] ?: error("woops, no tech tree item for tech label: $req")
            item.state is TechState.Researched && hasResearchedAll(item.requirements)
        }
}

interface TechItem : TechStaticData {
    var state: TechState

    fun buildTech(): Tech

    fun updateResearchedState() = apply {
        require(state is TechState.Unresearched)
        state = TechState.Researched(buildTech())
    }
}

abstract class AbstractTechItem(
    data: TechStaticData,
    private val techBuilder: () -> Tech,
) : TechItem, TechStaticData by data {

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