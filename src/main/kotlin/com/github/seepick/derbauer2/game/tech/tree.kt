package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.common.printTree
import com.github.seepick.derbauer2.game.common.validCycleFree
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.requireAllZeroOrPositive

class TechTree(
    val items: List<TechItem>,
    val user: User,
) {
    private val itemsByLabel = items.associateBy { it.label }
    private val dataToItemByLabel: (TechData) -> TechItem = { data ->
        itemsByLabel[data.label] ?: error("Woops, no tech tree item for tech data ID: $data")
    }

    init {
        items.flatMap { it.costs.changes }.requireAllZeroOrPositive()
        val unknownReq = items.flatMap { it.requirements.filter { !itemsByLabel.containsKey(it.label) } }
        require(unknownReq.isEmpty()) {
            "Some tech items have requirements not present in the tech tree: $unknownReq"
        }
        validCycleFree(items, items.associateWith { item ->
            item.requirements.map { dataToItemByLabel(it) }
        })
    }

    fun filterResearchableItems(): List<TechItem> =
        items.filter { !user.hasTech(it.techClass) && hasResearchedAll(it.requirements) }

    private fun hasResearchedAll(requirements: Set<TechData>): Boolean =
        requirements.all { reqData ->
            val reqItem = dataToItemByLabel(reqData)
            user.hasTech(reqItem.techClass) && hasResearchedAll(reqItem.requirements)
        }

    private fun rootsAndChildren(): Pair<List<TechItem>, Map<TechItem, List<TechItem>>> {
        val children = items.associateWith { mutableListOf<TechItem>() }
        items.forEach { item ->
            item.requirements.forEach { reqData ->
                val parentItem = dataToItemByLabel(reqData)
                children[parentItem]?.add(item) ?: error("Impossible inconsistency occured!")
            }
        }
        val roots = items.filter { it.requirements.isEmpty() }
        return roots to children
    }

    fun toPrettyString(): String {
        val (roots, children) = rootsAndChildren()
        return printTree(
            "<🤓TECH🔬TREE🤓>", roots, children,
            isChecked = { user.hasTech(it.techClass) },
            label = { label },
        )
    }
}
