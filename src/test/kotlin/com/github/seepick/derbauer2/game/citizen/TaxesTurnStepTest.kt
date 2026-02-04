package com.github.seepick.derbauer2.game.citizen

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.common.zz
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.prob.PassThroughDiffuser
import com.github.seepick.derbauer2.game.prob.ProbDiffuserKey
import com.github.seepick.derbauer2.game.prob.ProbsImpl
import com.github.seepick.derbauer2.game.prob.StaticDiffuser
import com.github.seepick.derbauer2.game.prob.updateDiffuser
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.shouldContainChange
import com.github.seepick.derbauer2.game.testInfra.ownedForTest
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual

class TaxesTurnStepTest : DescribeSpec({
    lateinit var user: User
    lateinit var probs: ProbsImpl
    lateinit var step: TaxesTurnStep
    beforeTest {
        user = User()
        probs = ProbsImpl()
        step = TaxesTurnStep(user, probs)
        step.initProb()
    }

    fun calcChanges() = step.calcTurnChanges()

    context("Given 💰 and 🙎🏻‍♂️") {
        lateinit var gold: Gold
        lateinit var citizen: Citizen
        beforeTest {
            gold = user.add(Gold())
            citizen = user.add(Citizen())
        }
        describe("Given diffusing disabled") {
            beforeTest {
                probs.updateDiffuser(ProbDiffuserKey.taxKey, PassThroughDiffuser)
            }
            it("Given some 🙎🏻‍♂️ Then change increases 💰 But user's  💰 stays unchanged") {
                val goldBefore = gold.owned
                citizen.ownedForTest = 10.z

                calcChanges().shouldContainChange(Gold::class, (citizen.owned * Mechanics.taxRate).zz)
                gold.owned shouldBeEqual goldBefore
            }
        }
        describe("Edgecases") {
            it("Given negative diffused value Then limit 💰 to 0") {
                probs.updateDiffuser(ProbDiffuserKey.taxKey, StaticDiffuser(staticValue = (-1).zz))

                calcChanges().shouldContainChange(Gold::class, 0.zz)
            }
        }
    }
})
