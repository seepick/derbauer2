package com.github.seepick.derbauer2.game.core

import com.github.seepick.derbauer2.game.common.z
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
            val citizen = Citizen(0.z)
            user.add(citizen)
            user.all.find<Citizen>() shouldBeSameInstanceAs citizen
        }
        it("duplicate fail") {
            user.add(Citizen(0.z))
            shouldThrow<Exception> { user.add(Citizen(0.z)) }
                .message shouldContain Citizen::class.simpleName!!
        }
    }
})
