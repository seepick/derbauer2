package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.building.Farm
import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.building.enableAndSet
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.testInfra.User
import com.github.seepick.derbauer2.game.testInfra.ownedForTest
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldBeSingleton
import io.kotest.matchers.equals.shouldBeEqual

private data class SetupContext(
    val user: User,
    val food: Resource,
    val farm: Farm,
    val granary: Granary,
)

private fun userWithFarmAndGranary(test: SetupContext.() -> Unit) {
    val user = User()
    val food = user.enable(Food())
    val farm = user.enableAndSet(Farm(), 1.z)
    val granary = user.enableAndSet(Granary(), 1.z)

    test(SetupContext(user, food, farm, granary))
}

class ResourceTurnerTest : DescribeSpec({
    describe("executeAndReturnReport") {
        it("Given nothing Then do nothing") {
            val user = User()
            val actualChanges = ProducesResourceTurnStep(user).calcResourceChanges()

            actualChanges.shouldBeEmpty()
        }
        it("Given ok setup Then produce And resource untouched") {
            userWithFarmAndGranary {
                val actualChanges = ProducesResourceTurnStep(user).calcResourceChanges()

                actualChanges.shouldBeSingleton().first() shouldBeEqual ResourceChange(
                    food, farm.owned * farm.producingResourceAmount
                )
            }
        }
        it("Given two forms Then produce both") {
            userWithFarmAndGranary {
                farm.ownedForTest = 2.z

                val actualChanges = ProducesResourceTurnStep(user).calcResourceChanges()

                actualChanges.shouldBeSingleton().first() shouldBeEqual ResourceChange(
                    food, farm.owned * farm.producingResourceAmount
                )
            }
        }
        it("Given almost full storage Then produce diff") {
            userWithFarmAndGranary {
                food.ownedForTest = granary.totalStorageAmount - 1

                val actualChanges = ProducesResourceTurnStep(user).calcResourceChanges()

                actualChanges.shouldBeSingleton().first() shouldBeEqual ResourceChange(food, 1.z)
            }
        }
        it("Given full storage Then produce zero") {
            userWithFarmAndGranary {
                food.ownedForTest = granary.totalStorageAmount

                val actualChanges = ProducesResourceTurnStep(user).calcResourceChanges()

                actualChanges.shouldBeSingleton().first() shouldBeEqual ResourceChange(food, 0.z)
            }
        }
    }
})
