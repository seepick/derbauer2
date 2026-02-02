package com.github.seepick.derbauer2.game.tech

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainOnly
import io.kotest.matchers.equals.shouldBeEqual

class TechTreeTest : DescribeSpec({
    lateinit var agri: TechTreeItem
    lateinit var irri: TechTreeItem
    lateinit var capitalism: TechTreeItem
    lateinit var junk: TechTreeItem
    lateinit var warfare: TechTreeItem
    beforeTest {
        agri = TechType.AGRICULTURE.treeItemBuilder()
        irri = TechType.IRRIGATION.treeItemBuilder()
        capitalism = TechType.CAPITALISM.treeItemBuilder()
        junk = TechType.JUNK_FOOD.treeItemBuilder()
        warfare = TechType.WARFARE.treeItemBuilder()
    }

    fun tree(vararg items: TechTreeItem) =
        TechTree(items.toList())

    fun filteredTree(vararg items: TechTreeItem) =
        tree(*items).filterResearchableItems()

    describe("filterResearchableItems") {
        it("Given agri Then return it") {
            filteredTree(agri).shouldContainOnly(agri)
        }
        it("Given agri researched Then empty") {
            agri.updateResearchedState()
            filteredTree(agri).shouldBeEmpty()
        }
        it("Given agri and irri Then only agri") {
            filteredTree(agri, irri).shouldContainOnly(agri)
        }
        it("Given agri researched and irri Then only irri") {
            agri.updateResearchedState()
            filteredTree(agri, irri).shouldContainOnly(irri)
        }
    }
    describe("toPrettyString") { // WIP let AI implement this (once tree enum is refactored)
        it("tree with").config(enabled = false) {
            tree(capitalism, agri, irri, junk, warfare).toPrettyString() shouldBeEqual """
                <TECH TREE>
                ├── ${capitalism.label}
                ├── ${agri.label}
                │   ├── ${irri.label}
                │   └── ${junk.label}
                └── ${warfare.label}
            """.trimIndent()
        }
    }
})
