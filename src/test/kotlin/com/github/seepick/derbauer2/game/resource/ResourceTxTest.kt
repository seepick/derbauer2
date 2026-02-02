package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.building.enableAndSet
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.common.zz
import com.github.seepick.derbauer2.game.core.TxOwnable
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.testInfra.User
import com.github.seepick.derbauer2.game.transaction.execTx
import com.github.seepick.derbauer2.game.transaction.shouldBeFail
import com.github.seepick.derbauer2.game.transaction.shouldBeSuccess
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual

class ResourceTxTest : DescribeSpec({
    lateinit var user: User
    beforeTest {
        user = User()
    }
    describe("simple non-storageable") {
        it("When add Then added") {
            val resource = user.enable(Gold())
            user.execTx(TxOwnable(resource::class, 1.zz)).shouldBeSuccess()
            resource.owned shouldBeEqual 1.z
        }
    }
    describe("storageable") {
        it("Given no storage When add Then fail") {
            val resource = user.enable(Food())

            user.execTx(TxOwnable(resource::class, 1.zz)).shouldBeFail("Not enough storage")
        }
        it("Given enough storage When add Then succeed") {
            user.enableAndSet(Granary(), 1.z)
            val resource = user.enable(Food())

            user.execTx(TxOwnable(resource::class, 1.zz)).shouldBeSuccess()
        }
    }
})
