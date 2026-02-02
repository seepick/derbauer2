package com.github.seepick.derbauer2.game.happening.happenings

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.TxOwnable
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.emojiAndLabelFor
import com.github.seepick.derbauer2.game.core.hasEntity
import com.github.seepick.derbauer2.game.happening.Happening
import com.github.seepick.derbauer2.game.happening.HappeningData
import com.github.seepick.derbauer2.game.happening.HappeningDescriptor
import com.github.seepick.derbauer2.game.happening.HappeningNature
import com.github.seepick.derbauer2.game.happening.HappeningType
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.transaction.errorOnFail
import com.github.seepick.derbauer2.game.transaction.execTx
import com.github.seepick.derbauer2.game.view.AsciiArt
import com.github.seepick.derbauer2.textengine.Textmap

object FoundGoldDescriptor : HappeningDescriptor(HappeningNature.Positive) {
    override val type = HappeningType.FoundGold

    override fun canHappen(user: User) =
        user.hasEntity(Gold::class)

    override fun buildHappening(user: User) =
        FoundGoldHappening(goldFound = 20.z, this)
}

class FoundGoldHappening(val goldFound: Z, private val descriptor: HappeningData = FoundGoldDescriptor) :
    Happening, HappeningData by descriptor {

    override val asciiArt = AsciiArt.goldPot

    override fun render(textmap: Textmap) {
        textmap.line("Found $goldFound ${Gold.Data.emojiAndLabelFor(goldFound)}")
    }

    override fun execute(user: User) {
        user.execTx(TxOwnable(Gold::class, goldFound.zz)).errorOnFail()
    }
}

@Suppress("ObjectPropertyName")
private val _goldPot = AsciiArt(
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
val AsciiArt.Companion.goldPot get() = _goldPot
