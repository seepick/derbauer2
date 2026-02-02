package com.github.seepick.derbauer2.game.transaction

import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.building.House
import com.github.seepick.derbauer2.game.building.enableAndSet
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.common.zz
import com.github.seepick.derbauer2.game.core.TxOwned
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.enableAndSet
import com.github.seepick.derbauer2.game.testInfra.User
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.should
import io.kotest.matchers.types.shouldBeInstanceOf

class TransactionTest : DescribeSpec({
    lateinit var user: User
    beforeTest {
        user = User()
    }

    describe("When changing resource and storage") {
        it("Given nothing When adding non-existing resource Then fail") {
            shouldThrow<IllegalArgumentException> {
                user.execTx(TxOwned(Land::class, 1.zz))
            }.message.shouldNotBeNull().should {
                it.contains("nothing found")
                it.contains("Land")
            }
        }

        it("Given zero gold When remove gold Then fail") {
            user.enable(Gold())

            user.execTx(TxOwned(Gold::class, (-1).zz))
                .shouldBeInstanceOf<TxResult.Fail>().message.should {
                    it.lowercase().contains("negative")
                }
        }

        it("Given zero land When build Then fail") {
            user.enable(Land())
            user.enable(House())

            user.execTx(TxOwned(House::class, 1.zz))
                .shouldBeInstanceOf<TxResult.Fail.LandOveruse>()
        }
        it("Given no storage When adding resource Then fail") {
            user.enable(Land())
            user.enable(Food())

            user.execTx(TxOwned(Food::class, 1.zz))
                .shouldBeInstanceOf<TxResult.Fail>().message.should {
                    it.lowercase().contains("not enough storage")
                }
        }
        it("Given enough storage When adding resource Then succeed") {
            user.enableAndSet(Land(), 50.z)
            user.enableAndSet(Granary(), 1.z)
            user.enable(Food())

            user.execTx(TxOwned(Food::class, 1.zz)).shouldBeSuccess()
        }
        it("Given enough storage When adding resource and removing storage Then fail") {
            user.enableAndSet(Land(), 20.z)
            user.enableAndSet(Granary(), 1.z)
            user.enable(Food())

            user.execTx(
                TxOwned(Food::class, 1.zz),
                TxOwned(Granary::class, (-1).zz)
            ).shouldBeFail("Not enough storage")
        }
    }
})
