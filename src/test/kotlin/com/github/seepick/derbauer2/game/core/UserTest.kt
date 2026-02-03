package com.github.seepick.derbauer2.game.core

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.addResource
import com.github.seepick.derbauer2.game.testInfra.shouldContainInAnyOrder
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.types.shouldBeSameInstanceAs

class UserTest : DescribeSpec({
    var user = User()
    beforeTest { user = User() }

    describe("enable") {
        it("stores resource") {
            val citizen = user.add(Citizen())

            user.all.find<Citizen>() shouldBeSameInstanceAs citizen
        }
        it("duplicate fail") {
            user.add(Citizen())

            shouldThrow<UserEnableException> {
                user.add(Citizen())
            }.message shouldContain "Citizen"
        }
        it("lambda fails") {
            shouldThrow<UserEnableException> {
                user.add(object : Entity {
                    override val labelSingular = "fail in lambda"
                    override fun deepCopy() = this
                })
            }.message.shouldContainInAnyOrder("fail in lambda", "anonymous class")
        }
    }
    describe("game over") {
        it("Given no 🙎🏻‍♂️ Then game cant be over") {
            user.isGameOver() shouldBeEqual false
        }
        it("Given 1 🙎🏻‍♂️ When take turn Then game is not over") {
            user.addResource(Citizen(), 1.z)

            user.isGameOver() shouldBeEqual false
        }
        it("Given 0 🙎🏻‍♂️ Then game is over ☠️") {
            user.addResource(Citizen(), 0.z)

            user.isGameOver() shouldBeEqual true
        }
    }
})

