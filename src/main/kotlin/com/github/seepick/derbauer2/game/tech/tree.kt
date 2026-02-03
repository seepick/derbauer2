package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.resource.requireAllZeroOrPositive

class TechTree(
    val all: List<TechItem>,
) {
    private val byLabel = all.associateBy { it.label } // nice hack ;)
    private val finder: (TechStaticData) -> TechItem = { data ->
        byLabel[data.label] ?: error("woops, no tech tree item for tech label: $data")
    }

    init {
        all.flatMap { it.costs.changes }.requireAllZeroOrPositive()
        validateRequirements(finder, all)
    }

    fun filterResearchableItems(): List<TechItem> =
        all.filter {
            it.state is TechState.Unresearched &&
                    hasResearchedAll(it.requirements)
        }

    private fun hasResearchedAll(requirements: Set<TechStaticData>): Boolean =
        requirements.all { requirementData ->
            val requirementItem = finder(requirementData)
            requirementItem.state is TechState.Researched && hasResearchedAll(requirementItem.requirements)
        }

    companion object {
        private fun validateRequirements(finder: (TechStaticData) -> TechItem, all: List<TechItem>) {
            val adjacency = all.associateWith { item ->
                item.requirements.map { finder(it) }
            }
            val visiting = mutableSetOf<TechItem>()
            val visited = mutableSetOf<TechItem>()
            fun dfs(node: TechItem) {
                if (node in visited) {
                    return
                }
                if (node in visiting) {
                    throw IllegalArgumentException("cycle detected in tech requirements at: ${node.label}")
                }
                visiting += node
                adjacency[node]?.forEach { dfs(it) }
                visiting -= node
                visited += node
            }
            all.forEach { dfs(it) }
        }
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