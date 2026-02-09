package com.github.seepick.derbauer2.game.stat

import com.github.seepick.derbauer2.game.common.double11
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.turn.turnReport
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.property.Arb
import io.kotest.property.arbitrary.next

class StatCompositeGlobalTurnStepTest : StringSpec({
    lateinit var user: User
    val anyReport = Arb.turnReport().next()
    val statChangeAmount = 0.42

    beforeTest {
        user = User()
    }

    fun newTurner(
        preModifer: PreStatModifier? = null,
        postModifer: PostStatModifier? = null,
    ) = StatCompositeGlobalTurnStep(
        user,
        GlobalPreStatModifierRepoImpl(preModifer?.let { listOf(it) } ?: emptyList()),
        GlobalPostStatModifierRepoImpl(postModifer?.let { listOf(it) } ?: emptyList()),
    )

    fun User.addStatWith(value: Double) = add(Happiness(value.double11))

    "Given global pre-modifier When executed Then stat changed" {
        val stat = user.addStatWith(0.0)
        val turner = newTurner(preModifer = PreStatModifier(stat, statChangeAmount))

        turner.execPreTurn()

        stat.value.number shouldBeEqual statChangeAmount
    }
    "Given global post-modifier When executed Then stat changed" {
        val stat = user.addStatWith(0.0)
        val turner = newTurner(postModifer = PostStatModifier(stat, statChangeAmount))

        turner.execPostTurn(anyReport)

        stat.value.number shouldBeEqual statChangeAmount
    }
    "Given local pre-modifier When executed Then stat changed" {
        val stat = user.addStatWith(0.0)
        user.add(PreStatModifierEntity(PreStatModifier(stat, statChangeAmount)))

        newTurner().execPreTurn()

        stat.value.number shouldBeEqual statChangeAmount
    }
    "Given local post-modifier When executed Then stat changed" {
        val stat = user.addStatWith(0.0)
        user.add(PostStatModifierEntity(PostStatModifier(stat, statChangeAmount)))

        newTurner().execPostTurn(anyReport)

        stat.value.number shouldBeEqual statChangeAmount
    }
})
