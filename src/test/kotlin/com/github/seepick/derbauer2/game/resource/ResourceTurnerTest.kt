package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.building.Farm
import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.testInfra.User
import com.github.seepick.derbauer2.game.testInfra.enableAndSet
import com.github.seepick.derbauer2.game.testInfra.ownedForTest
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldBeSingleton
import io.kotest.matchers.equals.shouldBeEqual

private data class SetupContext(
    val resource: Resource,
    val producer: ProducesResourceOwnable,
    val storage: Granary,
)

class ResourceTurnerTest : DescribeSpec({
    lateinit var user: User
    beforeTest {
        user = User()
    }

    describe("executeAndReturnReport") {
        fun withOkSetup(test: SetupContext.() -> Unit) {
            val resource = user.enable(Food())
            val producer = user.enableAndSet(Farm(), 1.z)
            val storage = user.enableAndSet(Granary(), 1.z)

            test(SetupContext(resource, producer, storage))
        }
        it("Given nothing Then do nothing") {
            val report = ResourceTurner(user).buildTurnReport()

            report.lines.shouldBeEmpty()
            user.all.shouldBeEmpty()
        }
        it("Given ok setup Then produce And resource untouched") {
            withOkSetup {
                val resourcesBefore = resource.owned
                val report = ResourceTurner(user).buildTurnReport()

                val change = producer.owned * producer.producingResourceAmount
                report.lines.shouldBeSingleton().first() shouldBeEqual ResourceChange(resource, change)
                resource.owned shouldBeEqual resourcesBefore
            }
        }
        it("Given two forms Then produce both") {
            withOkSetup {
                producer.ownedForTest = 2.z
                val report = ResourceTurner(user).buildTurnReport()

                val change = producer.owned * producer.producingResourceAmount
                report.lines.shouldBeSingleton().first() shouldBeEqual ResourceChange(resource, change)
            }
        }
        it("Given full storage Then still included as 0") {
            withOkSetup {
                resource.ownedForTest = storage.totalStorageAmount
                val report = ResourceTurner(user).buildTurnReport()

                val change = 0.z
                report.lines.shouldBeSingleton().first() shouldBeEqual ResourceChange(resource, change)
            }
        }
    }
})
