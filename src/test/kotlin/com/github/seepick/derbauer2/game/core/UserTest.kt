package com.github.seepick.derbauer2.game.core

import com.github.seepick.derbauer2.game.resource.Citizen
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.types.shouldBeSameInstanceAs

class UserTest : DescribeSpec({
    var user = User()
    beforeTest { user = User() }

    describe("add entity") {
        it("stores resource") {
            val citizen = user.enable(Citizen())
            user.all.find<Citizen>() shouldBeSameInstanceAs citizen
        }
        it("duplicate fail") {
            user.enable(Citizen())
            shouldThrow<Exception> { user.enable(Citizen()) }
                .message shouldContain Citizen::class.simpleName!!
        }
    }
})
