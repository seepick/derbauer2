package com.github.seepick.derbauer2.game.core

import com.github.seepick.derbauer2.game.resource.Citizen
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldContainIgnoringCase
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
})

fun String?.shouldContainInAnyOrder(vararg strings: String, ignoreCase: Boolean = false) {
    this.shouldNotBeNull()
    strings.forEach { string ->
        if (ignoreCase) {
            this.shouldContainIgnoringCase(string)
        } else {
            this.shouldContain(string)
        }
    }
}
