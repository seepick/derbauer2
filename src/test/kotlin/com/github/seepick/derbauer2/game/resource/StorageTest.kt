package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.User
import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.enableAndSet
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual

class StorageTest : DescribeSpec({
    lateinit var user: User
    beforeTest {
        user = User()
    }

    describe("storage capacity for") {
        it("increases") {
            val granary = user.enableAndSet(Granary(), 1.z)

            user.storageFor(Food::class) shouldBeEqual granary.totalStorageAmount
        }
    }
})
