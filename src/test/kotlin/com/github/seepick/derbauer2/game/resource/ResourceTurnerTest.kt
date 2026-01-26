package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.building.Farm
import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.logic.units
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldBeSingleton
import io.kotest.matchers.equals.shouldBeEqual

private data class SetupContext(
    val user: User,
    val resource: Resource,
    val producer: ProducesResourceOwnable,
    val storage: Granary,
)

class ResourceTurnerTest : DescribeSpec({
    describe("executeAndReturnReport") {
        fun withOkSetup(test: SetupContext.() -> Unit) {
            val user = User()
            val resource = user.add(Food(0.units))
            val producer = user.add(Farm(1.units))
            val storage = user.add(Granary(1.units))

            test(SetupContext(user, resource, producer, storage))
        }
        it("Given nothing Then do nothing") {
            val user = User()

            val report = ResourceTurner(user).buildTurnReport()

            report.lines.shouldBeEmpty()
            user.all.shouldBeEmpty()
        }
        it("Given ok setup Then produce And resource untouched") {
            withOkSetup {
                val resourcesBefore = resource.owned
                val report = ResourceTurner(user).buildTurnReport()

                val change = producer.owned * producer.producingResourceAmount
                report.lines.shouldBeSingleton().first() shouldBeEqual ResourceReportLine(resource, change)
                resource.owned shouldBeEqual resourcesBefore
            }
        }
        it("Given two forms Then produce both") {
            withOkSetup {
                producer.owned = 2.units
                val report = ResourceTurner(user).buildTurnReport()

                val change = producer.owned * producer.producingResourceAmount
                report.lines.shouldBeSingleton().first() shouldBeEqual ResourceReportLine(resource, change)
            }
        }
        it("Given full storage Then still included as 0") {
            withOkSetup {
                resource.owned = storage.totalStorageAmount
                val report = ResourceTurner(user).buildTurnReport()

                val change = 0.units
                report.lines.shouldBeSingleton().first() shouldBeEqual ResourceReportLine(resource, change)
            }
        }
    }
})