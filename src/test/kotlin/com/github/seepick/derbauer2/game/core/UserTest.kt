package com.github.seepick.derbauer2.game.core

import com.github.seepick.derbauer2.common.unit.z
import com.github.seepick.derbauer2.common.unit.zz
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.addResource
import com.github.seepick.derbauer2.game.resource.gold
import com.github.seepick.derbauer2.game.testInfra.shouldContainInAnyOrder
import com.github.seepick.derbauer2.game.transaction.execTx
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.kotest.matchers.types.shouldNotBeSameInstanceAs

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

            shouldThrow<IllegalEntityException> {
                user.add(Citizen())
            }.message shouldContain "Citizen"
        }
        it("lambda fails") {
            shouldThrow<IllegalEntityException> {
                user.add(object : Entity {
                    override val label = "fail in lambda"
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
    describe("clone deepCopy") {
        it("When clone Then a distinct but equal copy is created") {
            val user1 = User()
            val user2 = user1.deepCopy()
            user1.shouldNotBeSameInstanceAs(user2)
            user1.shouldBeEqual(user2)
        }
        it("When modifying the clone Then original stays unchanged") {
            val user1 = User()
            user1.addResource(Gold(), 2.z)

            val user2 = user1.deepCopy()
            user2.execTx(TxOwnable(Gold::class, 40.zz))

            user2.gold shouldBeEqual 42.z
            user1.gold shouldBeEqual 2.z
        }
    }
})
