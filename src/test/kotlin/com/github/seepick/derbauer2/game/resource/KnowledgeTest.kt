package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.common.zz
import com.github.seepick.derbauer2.game.core.TxOwnable
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.transaction.DefaultTxValidatorRepo
import com.github.seepick.derbauer2.game.transaction.execTx
import com.github.seepick.derbauer2.game.transaction.shouldBeSuccess
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual

class KnowledgeTest : DescribeSpec({
    lateinit var user: User
    beforeTest {
        user = User(DefaultTxValidatorRepo.validators)
    }
    describe("Knowledge resource") {
        it("When add Then added") {
            val resource = user.add(Knowledge())
            user.execTx(TxOwnable(resource::class, 5.zz)).shouldBeSuccess()
            resource.owned shouldBeEqual 5.z
        }
        it("When deepCopy Then preserves owned") {
            val original = Knowledge().apply { _setOwnedInternal = 10.z }
            val copy = original.deepCopy()
            copy.owned shouldBeEqual 10.z
        }
    }
})
