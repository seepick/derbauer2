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
import com.github.seepick.derbauer2.game.transaction.errorOnFail
import com.github.seepick.derbauer2.game.transaction.execTx
import com.github.seepick.derbauer2.game.turn.CurrentTurn
import com.github.seepick.derbauer2.game.view.AsciiArt
import com.github.seepick.derbauer2.game.view.emojiAndLabelFor
import com.github.seepick.derbauer2.textengine.textmap.Textmap

data class FoundGoldHappening(
    val goldFound: Z,
    private val data: HappeningData = Ref,
) : Happening, HappeningData by data {

    override val asciiArt = AsciiArt.goldPot

    override fun render(textmap: Textmap) {
        textmap.line(
            "Lucky you, you found ${goldFound.toPrefixedString()} ${
                Gold.Data.emojiAndLabelFor(goldFound)
            }"
        )
    }

    override fun execute(user: User) {
        user.execTx(TxOwnable(Gold::class, goldFound.zz)).errorOnFail()
    }

    object Ref : HappeningRef {
        override val nature = HappeningNature.Positive
        override fun canHappen(user: User, probs: Probs) = user.hasEntity(Gold::class)

        override fun willHappen(user: User, probs: Probs) = true

        override fun buildHappening(user: User): FoundGoldHappening = FoundGoldHappening(goldFound = 100.z, this)

        override fun initProb(probs: Probs, user: User, turn: CurrentTurn) {
            // not using probs
        }
    }
}

private val goldPotAscii = AsciiArt(
    """
        _oOoOoOo_  
       (oOoOoOoOo) 
        )`""${'"'}${'"'}${'"'}`(  
       /         \ 
      | #         |
      \           /
       `=========`
       """.trimIndent()
)

val AsciiArt.Companion.goldPot get() = goldPotAscii
