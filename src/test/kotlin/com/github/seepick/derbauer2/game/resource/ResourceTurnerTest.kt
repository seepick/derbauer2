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
    val food: Resource,
    val farm: Farm,
    val granary: Granary,
)

class ResourceTurnerTest : DescribeSpec({
    lateinit var user: User
    beforeTest {
        user = User()
    }
    describe("executeAndReturnReport") {
        fun userWithFarmAndGranary(test: SetupContext.() -> Unit) {
            val food = user.enable(Food())
            val farm = user.enableAndSet(Farm(), 1.z)
            val granary = user.enableAndSet(Granary(), 1.z)

            test(SetupContext(food, farm, granary))
        }
        it("Given nothing Then do nothing") {
            val actualChanges = ResourceProducingResourceTurnStep(user).calcResourceChanges()

            actualChanges.shouldBeEmpty()
        }
        it("Given ok setup Then produce And resource untouched") {
            userWithFarmAndGranary {
                val actualChanges = ResourceProducingResourceTurnStep(user).calcResourceChanges()

                actualChanges.shouldBeSingleton().first() shouldBeEqual ResourceChange(
                    food, farm.owned * farm.producingResourceAmount
                )
            }
        }
        it("Given two forms Then produce both") {
            userWithFarmAndGranary {
                farm.ownedForTest = 2.z

                val actualChanges = ResourceProducingResourceTurnStep(user).calcResourceChanges()

                actualChanges.shouldBeSingleton().first() shouldBeEqual ResourceChange(
                    food, farm.owned * farm.producingResourceAmount
                )
            }
        }
        it("Given almost full storage Then produce diff") {
            userWithFarmAndGranary {
                food.ownedForTest = granary.totalStorageAmount - 1

                val actualChanges = ResourceProducingResourceTurnStep(user).calcResourceChanges()

                actualChanges.shouldBeSingleton().first() shouldBeEqual ResourceChange(food, 1.z)
            }
        }
        it("Given full storage Then produce zero") {
            userWithFarmAndGranary {
                food.ownedForTest = granary.totalStorageAmount

                val actualChanges = ResourceProducingResourceTurnStep(user).calcResourceChanges()

                actualChanges.shouldBeSingleton().first() shouldBeEqual ResourceChange(food, 0.z)
            }
        }
    }
})
