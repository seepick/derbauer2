package com.github.seepick.derbauer2.game.common

import com.github.seepick.derbauer2.game.core.User
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.types.shouldNotBeSameInstanceAs

class DeepCopyTest : StringSpec( {
    "User deep copy creates a distinct but equal copy" {
        val user1 = User()
        val user2 = user1.deepCopy()
        user1.shouldNotBeSameInstanceAs(user2)
        user1.shouldBeEqual(user2)
    }
})
