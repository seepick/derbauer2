package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.building.Farm
import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.building.enableAndSet
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.testInfra.ownedForTest
import com.github.seepick.derbauer2.game.testInfra.zz
import io.kotest.core.spec.style.DescribeSpec

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
    val food = user.add(Food())
    val farm = user.enableAndSet(Farm(), 1.z)
    val granary = user.enableAndSet(Granary(), 1.z)

    test(SetupContext(ProducesResourceTurnStep(user), user, food, farm, granary))
}

class ProducesResourceTurnStepTest : DescribeSpec({
    describe("happy cases") {
        it("Given ok setup Then produce And resource untouched") {
            `user with 0 🍖, 1 granary, 1 farm` {
                calcStepChanges().shouldContainChange(
                    food, 1.z * farm.producingResourceAmount
                )
            }
        }
        it("Given 2 farms Then produce both") {
            `user with 0 🍖, 1 granary, 1 farm` {
                farm.ownedForTest = 2.z

                calcStepChanges().shouldContainChange(
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

                calcStepChanges().shouldContainChange(food, diff)
            }
        }
        it("Given full storage Then produce zero") {
            `user with 0 🍖, 1 granary, 1 farm` {
                food.ownedForTest = granary.totalStorageAmount

                calcStepChanges().shouldContainChange(food, 0.z)
            }
        }
    }

    describe("modifier interactions") {
        it("When modifier 2x Then produce 2x") {
            `user with 0 🍖, 1 granary, 1 farm` {
                val multiplier = 2
                user.add(ResourceProductionMultiplierStub(Food::class, multiplier.toDouble()))

                calcStepChanges().shouldContainChange(food, farm.producingResourceAmount * multiplier)
            }
        }
        it("When 2x but storage limited Then produce up to free storage") {
            `user with 0 🍖, 1 granary, 1 farm` {
                val diff = 1.z
                food.ownedForTest = granary.totalStorageAmount - diff
                user.add(ResourceProductionMultiplierStub(Food::class, 2.0))

                calcStepChanges().shouldContainChange(food, diff)
            }
        }
        it("When modifier makes negative beyond owned Then clamp to -owned") {
            `user with 0 🍖, 1 granary, 1 farm` {
                farm.ownedForTest = 1.z
                val ownedFood = 1.z
                food.ownedForTest = ownedFood
                user.add(ResourceProductionMultiplierStub(Food::class, -2.0))

                calcStepChanges().shouldContainChange(food, -ownedFood)
            }
        }

        it("When multiple modifiers fold sequentially Then apply both") {
            `user with 0 🍖, 1 granary, 1 farm` {
                val baseProduction = farm.totalProducingResourceAmount
                user.add(ResourceProductionMultiplierStub1(Food::class, 2.0))
                user.add(ResourceProductionMultiplierStub2(Food::class, 0.5))

                calcStepChanges().shouldContainChange(food, (baseProduction.toDouble() * 2.0 * 0.5).zz)
            }
        }
    }
})
