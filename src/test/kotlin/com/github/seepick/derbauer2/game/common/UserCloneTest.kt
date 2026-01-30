package com.github.seepick.derbauer2.game.common

import com.github.seepick.derbauer2.game.core.gold
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.execTxResource
import com.github.seepick.derbauer2.game.testInfra.User
import com.github.seepick.derbauer2.game.testInfra.enableAndSet
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.types.shouldNotBeSameInstanceAs

class UserCloneTest : StringSpec({
    "When clone Then a distinct but equal copy is created" {
        val user1 = User()
        val user2 = user1.deepCopy()
        user1.shouldNotBeSameInstanceAs(user2)
        user1.shouldBeEqual(user2)
    }
    "When modifying the clone Then original stays unchanged" {
        val user1 = User()
        user1.enableAndSet(Gold(), 2.z)

        val user2 = user1.deepCopy()
        user2.execTxResource(Gold::class, 40.z)

        user2.gold shouldBeEqual 42.z
        user1.gold shouldBeEqual 2.z
    }
})
