package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.resource.ResourceChanges
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainOnly
import io.kotest.matchers.equals.shouldBeEqual

class TechTreeTest : DescribeSpec({

    fun tree(vararg items: TechItem) =
        TechTree(items.toList())

    fun filteredTree(vararg items: TechItem) =
        tree(*items).filterResearchableItems()

    describe("init") {
        it("self reference fail") {
            shouldThrow<IllegalArgumentException> {
                tree(SelfItem)
            }
        }
        it("cycle references fail") {
            shouldThrow<IllegalArgumentException> {
                tree(Cycle1Item, Cycle2Item)
            }
        }
    }
    describe("When filterResearchableItems") {
        it("Given unresearched tech Then return it") {
            val item = newTechItem()

            filteredTree(item).shouldContainOnly(item)
        }
        it("Given researched tech Then is empty") {
            val item = newTechItem().updateResearchedState()

            filteredTree(item).shouldBeEmpty()
        }
        it("Given 2 dependent techs Then return only first") {
            val item1a = newTechItem(label = "tech1a")
            val item1b = newTechItem(label = "tech1b", requirements = setOf(item1a))

            filteredTree(item1a, item1b).shouldContainOnly(item1a)
        }
        it("Given 1 researched and dependent not Then return only second") {
            val item1a = newTechItem(label = "tech1a").updateResearchedState()
            val item1b = newTechItem(label = "tech1b", requirements = setOf(item1a))
            filteredTree(item1a, item1b).shouldContainOnly(item1b)
        }
    }
    describe("toPrettyString") { // WIP let AI implement this (once tree enum is refactored)
        it("tree with").config(enabled = false) {
            val item1 = newTechItem(label = "item1")
            val item2 = newTechItem(label = "item2")
            val item2a = newTechItem(label = "item2a", requirements = setOf(item2))
            val item2b = newTechItem(label = "item2b", requirements = setOf(item2))
            val item3 = newTechItem(label = "item3")
            tree(item1, item2, item2a, item2b, item3).toPrettyString() shouldBeEqual """
                <🤓TECH🔬TREE🤓>
                ├── ${item1.label} ✅
                ├── ${item2.label}
                │   ├── ${item2a.label}
                │   └── ${item2b.label}
                └── ${item3.label}
                """.trimIndent()
        }
    }
})

private object SelfData : TechStaticData {
    override val label = "Cycle1"
    override val requirements = setOf<TechStaticData>(SelfData)
    override val costs = ResourceChanges.empty
}

private object SelfItem : TechItem, TechStaticData by SelfData {
    override var state: TechState = TechState.Unresearched
    override fun buildTech() = SelfTech()
}

private class SelfTech : Tech, TechStaticData by SelfData {
    override fun deepCopy() = this
}

private object Cycle1Data : TechStaticData {
    override val label = "Cycle1"
    override val requirements = setOf<TechStaticData>(Cycle2Data)
    override val costs = ResourceChanges.empty
}

private object Cycle1Item : TechItem, TechStaticData by Cycle1Data {
    override var state: TechState = TechState.Unresearched
    override fun buildTech() = Cycle1Tech()
}

private class Cycle1Tech : Tech, TechStaticData by Cycle1Data {
    override fun deepCopy() = this
}

private object Cycle2Data : TechStaticData {
    override val label = "Cycle2"
    override val requirements = setOf<TechStaticData>(Cycle1Data)
    override val costs = ResourceChanges.empty
}

private object Cycle2Item : TechItem, TechStaticData by Cycle2Data {
    override var state: TechState = TechState.Unresearched
    override fun buildTech() = Cycle2Tech()
}

private class Cycle2Tech : Tech, TechStaticData by Cycle2Data {
    override fun deepCopy() = this
}

