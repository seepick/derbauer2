package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.common.printTree
import com.github.seepick.derbauer2.game.common.validCycleFree
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.requireAllZeroOrPositive

class TechTree(
    val techs: List<TechRef>,
    val user: User,
) {
    private val itemsByLabel = techs.associateBy { it.label }
    private val dataToItemByLabel: (TechData) -> TechRef = { data ->
        itemsByLabel[data.label] ?: error("Woops, no tech tree item for tech data ID: $data")
    }

    init {
        techs.flatMap { it.costs.changes }.requireAllZeroOrPositive()
        val unknownReq = techs.flatMap { it.requirements.filter { !itemsByLabel.containsKey(it.label) } }
        require(unknownReq.isEmpty()) {
            "Some tech items have requirements not present in the tech tree: $unknownReq"
        }
        validCycleFree(techs, techs.associateWith { item ->
            item.requirements.map { dataToItemByLabel(it) }
        })
    }

    fun filterResearchableItems(): List<TechRef> =
        techs.filter { !user.hasTech(it.techClass) && hasResearchedAll(it.requirements) }

    private fun hasResearchedAll(requirements: Set<TechData>): Boolean =
        requirements.all { reqData ->
            val reqItem = dataToItemByLabel(reqData)
            user.hasTech(reqItem.techClass) && hasResearchedAll(reqItem.requirements)
        }

    private fun rootsAndChildren(): Pair<List<TechRef>, Map<TechRef, List<TechRef>>> {
        val children = techs.associateWith { mutableListOf<TechRef>() }
        techs.forEach { item ->
            item.requirements.forEach { reqData ->
                val parentItem = dataToItemByLabel(reqData)
                children[parentItem]?.add(item) ?: error("Impossible inconsistency occured!")
            }
        }
        val roots = techs.filter { it.requirements.isEmpty() }
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
