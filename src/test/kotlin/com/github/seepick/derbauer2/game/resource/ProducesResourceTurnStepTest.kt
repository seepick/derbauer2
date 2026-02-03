package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.building.Farm
import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.building.addAndSet
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.testInfra.ownedForTest
import com.github.seepick.derbauer2.game.testInfra.zz
import com.github.seepick.derbauer2.game.transaction.DefaultTxValidatorRepo
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
    val user = User(DefaultTxValidatorRepo.validators)
    val food = user.add(Food())
    val farm = user.addAndSet(Farm(), 1.z)
    val granary = user.addAndSet(Granary(), 1.z)

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
        // TEST two distinct entities both producing same resource taken into account
    }
    describe("edge cases") {
        it("Given nothing Then do nothing") {
            val user = User()

            val actualChanges = ProducesResourceTurnStep(user).calcResourceChanges()

            actualChanges.shouldBeEmpty()
        }
        // TODO test for going over storage limit
    }

    describe("modifier interactions") {
        it("When modifier 2x Then produce 2x") {
            `user with 0 🍖, 1 granary, 1 farm` {
                val multiplier = 2
                user.add(ResourceProductionMultiplierStub(Food::class, multiplier.toDouble()))

                calcStepChanges().shouldContainChange(food, farm.producingResourceAmount * multiplier)
            }
        }
        // TODO test for going over limits (above and below)
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
