package com.github.seepick.derbauer2.game.happening.happenings

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.TxOwnable
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.hasEntity
import com.github.seepick.derbauer2.game.happening.Happening
import com.github.seepick.derbauer2.game.happening.HappeningData
import com.github.seepick.derbauer2.game.happening.HappeningNature
import com.github.seepick.derbauer2.game.happening.HappeningRef
import com.github.seepick.derbauer2.game.prob.Probs
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.gold
import com.github.seepick.derbauer2.game.stat.Happiness
import com.github.seepick.derbauer2.game.stat.findStat
import com.github.seepick.derbauer2.game.transaction.errorOnFail
import com.github.seepick.derbauer2.game.transaction.execTx
import com.github.seepick.derbauer2.game.view.AsciiArt
import com.github.seepick.derbauer2.game.view.Texts
import com.github.seepick.derbauer2.textengine.textmap.Textmap
import com.github.seepick.derbauer2.textengine.textmap.multiLine

data class PoorPhilosopherHappening(
    private val goldGiving: Z,
    private val happinessGaining: Double,
    private val user: User,
    private val ref: Ref = Ref,
) : Happening, HappeningData by ref {

    override val asciiArt = AsciiArt.manHat

    override fun render(textmap: Textmap) {
        textmap.multiLine(Texts.poorPhilosopherHappening(user.cityTitle, goldGiving))
    }

    override fun execute(user: User) {
        user.execTx(TxOwnable(Gold::class, goldGiving.zz)).errorOnFail()
        user.findStat(Happiness::class).changeBy(happinessGaining)
    }

    object Ref : HappeningRef {
        override val nature = HappeningNature.Positive
        private val goldGiving = 20.z
        private const val HAPPINESS_GAINING = 0.2

        override fun canHappen(user: User, probs: Probs) =
            user.hasEntity(Gold::class) &&
                    user.gold >= goldGiving &&
                    user.hasEntity(Happiness::class)

        override fun buildHappening(user: User) = PoorPhilosopherHappening(
            goldGiving, HAPPINESS_GAINING, user, this
        )
    }
}
