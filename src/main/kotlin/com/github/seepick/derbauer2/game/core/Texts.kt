package com.github.seepick.derbauer2.game.core

import com.github.seepick.derbauer2.game.building.`build 🛠️`
import com.github.seepick.derbauer2.game.common.Emoji
import com.github.seepick.derbauer2.game.tech.`tech 🔬`
import com.github.seepick.derbauer2.game.trade.`trade 💸`

@Suppress("MayBeConstant", "VariableMaxLength")
object Texts {

    val buildPage = "Your builders are ready for work ${Emoji.`build 🛠️`}"
    val tradePage = "The good-humored merchant is here to do business with you ${Emoji.`trade 💸`}"
    fun techPage(emojiAndOwned: String) =
        "${Emoji.`tech 🔬`} What is your philosopher heart desiring next?\n\n" +
                "Your genius wisdom expands $emojiAndOwned units of knowledge."

    val techPageEmpty = "Your mind is empty...\nGo ahead and read some books first."
    val techItemAgriculture = "more food, more happy 🌽"
    val techItemIrrigation = "use little canals to make more mjam-mjam 🍗"
    val techItemCapitalism = "make the poor poorer, and yourself richer 🤑"

}
