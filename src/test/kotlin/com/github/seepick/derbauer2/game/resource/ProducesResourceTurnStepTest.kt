package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.building.Farm
import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.building.enableAndSet
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.testInfra.ownedForTest
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeSingleton
import io.kotest.matchers.equals.shouldBeEqual

private data class SetupContext(
    val step: ProducesResourceTurnStep,
    val user: User,
    val food: Resource,
    val farm: Farm,
    val granary: Granary,
) {
    fun calcStepChanges() = step.calcResourceChanges().changes
}

private fun `user with 0 🍖, 1 granary, 1 farm`(
    test: SetupContext.() -> Unit,
) {
    val user = User()
    val food = user.enable(Food())
    val farm = user.enableAndSet(Farm(), 1.z)
    val granary = user.enableAndSet(Granary(), 1.z)

    test(SetupContext(ProducesResourceTurnStep(user), user, food, farm, granary))
}

class ProducesResourceTurnStepTest : DescribeSpec({
    describe("happy cases") {
        it("Given ok setup Then produce And resource untouched") {
            `user with 0 🍖, 1 granary, 1 farm` {
                calcStepChanges().shouldBeSingleton().first() shouldBeEqual ResourceChange(
                    food, 1.z * farm.producingResourceAmount
                )
            }
        }
        it("Given 2 farms Then produce both") {
            `user with 0 🍖, 1 granary, 1 farm` {
                farm.ownedForTest = 2.z

                calcStepChanges().shouldBeSingleton().first() shouldBeEqual ResourceChange(
                    food, 2.z * farm.producingResourceAmount
                )
            }
        }
    }
    describe("edge cases") {
        it("Given nothing Then do nothing") {
            val user = User()

            val actualChanges = ProducesResourceTurnStep(user).calcResourceChanges()

            actualChanges.shouldBeEmpty()
        }
        it("Given almost full storage Then produce diff") {
            `user with 0 🍖, 1 granary, 1 farm` {
                val diff = 1.z
                food.ownedForTest = granary.totalStorageAmount - diff

                calcStepChanges().shouldBeSingleton().first() shouldBeEqual ResourceChange(food, diff)
            }
        }
        it("Given full storage Then produce zero") {
            `user with 0 🍖, 1 granary, 1 farm` {
                food.ownedForTest = granary.totalStorageAmount

                calcStepChanges().shouldBeSingleton().first() shouldBeEqual ResourceChange(food, 0.z)
            }
        }
    }
    context("ResourceProductionModifier") {
        describe("Sunshine") {
            it("When modifier 2x Then produce 2x") {
                `user with 0 🍖, 1 granary, 1 farm` {
                    user.enable(foodProductionModifier(2.0))

                    calcStepChanges().shouldContainChange(
                        food, 2.z * farm.producingResourceAmount
                    )
                }
            }
        }
        // TODO implement more tests
    }
})
