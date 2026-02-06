package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.AgricultureTech
import com.github.seepick.derbauer2.game.resource.ResourceChanges
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainOnly
import io.kotest.matchers.equals.shouldBeEqual

class TechTreeTest : DescribeSpec({
    lateinit var user: User
    beforeTest {
        user = User()
    }
    fun tree(vararg items: TechItem) =
        TechTree(items.toList(), user)

    fun treeResearchableItems(vararg items: TechItem) =
        tree(*items).filterResearchableItems()

    describe("invalid construction") {
        it("require self fails") {
            shouldThrow<IllegalArgumentException> {
                tree(SelfReferenceItem)
            }
        }
        it("require unknown fails") {
            shouldThrow<IllegalArgumentException> {
                val item = newTechItem(requirements = setOf(AgricultureTech.Data))
                tree(item)
            }
        }
        it("cycle reference fails") {
            shouldThrow<IllegalArgumentException> {
                tree(Cycle1Item, Cycle2Item)
            }
        }
    }
    describe("When filterResearchableItems") {
        it("Given unresearched tech Then return it") {
            val item = newTechItem()

            treeResearchableItems(item).shouldContainOnly(item)
        }
        it("Given researched tech Then is empty") {
            val item = newTechItem(techClass = TestTech::class)
            user.add(TestTech())

            treeResearchableItems(item).shouldBeEmpty()
        }
        it("Given 2 dependent techs Then return only first") {
            val item1a = newTechItem(label = "tech1a")
            val item1b = newTechItem(label = "tech1b", requirements = setOf(item1a))

            treeResearchableItems(item1a, item1b).shouldContainOnly(item1a)
        }
        it("Given 1 researched and dependent not Then return only second") {
            val item1a = newTechItem(label = "tech1a", techClass = TestTech1::class)
            user.add(TestTech1())
            val item1b = newTechItem(label = "tech1b", requirements = setOf(item1a))
            treeResearchableItems(item1a, item1b).shouldContainOnly(item1b)
        }
    }
    describe("toPrettyString") {
        val techTreeHeader = "<🤓TECH🔬TREE🤓>"
        it("single item") {
            val item = newTechItem()
            tree(item).toPrettyString() shouldBeEqual """
                $techTreeHeader
                └── ${item.label}
                """.trimIndent()
        }
        it("complex tree") {
            val item1 = newTechItem(label = "item1", techClass = TestTech1::class)
            user.add(TestTech1())
            val item2 = newTechItem(label = "item2")
            val item2a = newTechItem(label = "item2a", requirements = setOf(item2))
            val item2b = newTechItem(label = "item2b", requirements = setOf(item2))
            val item3 = newTechItem(label = "item3")
            tree(item1, item2, item2a, item2b, item3).toPrettyString() shouldBeEqual """
                $techTreeHeader
                ├── ${item1.label} ✅
                ├── ${item2.label}
                │   ├── ${item2a.label}
                │   └── ${item2b.label}
                └── ${item3.label}
                """.trimIndent()
        }
    }
})

private object SelfData : TechData {
    override val label = "SelfData"
    override val requirements = setOf<TechData>(SelfData)
    override val costs = ResourceChanges.empty
}

private object SelfReferenceItem : TechItem, TechData by SelfData {
    override val techClass = TestTech::class
    override fun buildTech() = SelfTech()
}

private class SelfTech : Tech, TechData by SelfData {
    override fun deepCopy() = this
}

private object Cycle1Data : TechData {
    override val label = "Cycle1"
    override val requirements = setOf<TechData>(Cycle2Data)
    override val costs = ResourceChanges.empty
}

private object Cycle1Item : TechItem, TechData by Cycle1Data {
    override val techClass = TestTech1::class
    override fun buildTech() = Cycle1Tech()
}

private class Cycle1Tech : Tech, TechData by Cycle1Data {
    override fun deepCopy() = this
}

private object Cycle2Data : TechData {
    override val label = "Cycle2"
    override val requirements = setOf<TechData>(Cycle1Data)
    override val costs = ResourceChanges.empty
}

private object Cycle2Item : TechItem, TechData by Cycle2Data {
    override val techClass = TestTech2::class
    override fun buildTech() = Cycle2Tech()
}

private class Cycle2Tech : Tech, TechData by Cycle2Data {
    override fun deepCopy() = this
}
