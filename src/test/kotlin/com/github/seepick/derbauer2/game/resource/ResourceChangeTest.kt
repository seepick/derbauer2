package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.User
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.spec.style.scopes.DescribeSpecContainerScope
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.string.shouldBeEmpty

class ResourceChangeTest : DescribeSpec({
    lateinit var user: User
    beforeTest {
        user = User()
    }
    suspend fun DescribeSpecContainerScope.test(testName: String, code: User.() -> Unit) {
        it(testName) {
            with(user) {
                code()
            }
        }
    }
    describe("toTextmapRendering") {
        test("Given nothing Then empty") {
            buildResourceChanges {}.toTextmapRendering().shouldBeEmpty()
        }
        test("Given single resource Then rendered") {
            add(Gold())

            buildResourceChanges {
                add(Gold::class, 2.z)
            }.toTextmapRendering() shouldBeEqual "${Gold.Data.emojiOrNull} 2"
        }
        test("Given two resources Then both rendered") {
            add(Gold())
            add(Food())

            buildResourceChanges {
                add(Gold::class, 2.z)
                add(Food::class, 3.z)
            }.toTextmapRendering() shouldBeEqual "${Gold.Data.emojiOrNull} 2, ${Food.Data.emojiOrNull} 3"
        }
    }
})
