package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.common.TreePrinter
import com.github.seepick.derbauer2.game.common.validCycleFree
import com.github.seepick.derbauer2.game.resource.requireAllZeroOrPositive

class TechTree(
    val all: List<TechItem>,
) {
    private val byId = all.associateBy { it.id }
    private val dataToItemFinder: (TechData) -> TechItem = { data ->
        byId[data.id] ?: error("Woops, no tech tree item for tech data ID: $data")
    }

    init {
        all.flatMap { it.costs.changes }.requireAllZeroOrPositive()
        val unknownReq = all.flatMap { it.requirements.filter { !byId.containsKey(it.id) } }
        require(unknownReq.isEmpty()) {
            "Some tech items have requirements not present in the tech tree: $unknownReq"
        }
        validCycleFree(all, all.associateWith { item ->
            item.requirements.map { dataToItemFinder(it) }
        })
    }

    fun filterResearchableItems(): List<TechItem> =
        all.filter { it.isUnresearched && hasResearchedAll(it.requirements) }

    private fun hasResearchedAll(requirements: Set<TechData>): Boolean =
        requirements.all { reqData ->
            val reqItem = dataToItemFinder(reqData)
            reqItem.isResearched && hasResearchedAll(reqItem.requirements)
        }

    private fun rootsAndChildren(): Pair<List<TechItem>, Map<TechItem, MutableList<TechItem>>> {
        val children = all.associateWith { mutableListOf<TechItem>() }
        all.forEach { item ->
            item.requirements.forEach { reqData ->
                val parentItem = dataToItemFinder(reqData)
                children[parentItem]?.add(item) ?: error("Impossible inconsistency occured!")
            }
        }
        val roots = all.filter { it.requirements.isEmpty() }
        return roots to children
    }

    fun toPrettyString(): String {
        val (roots, children) = rootsAndChildren()
        return TreePrinter.print(
            "<🤓TECH🔬TREE🤓>", roots, children,
            isChecked = { state is TechState.Researched },
            label = { label },
        )
    }
}
