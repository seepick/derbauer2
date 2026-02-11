package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.game.building.`build 🛠️`
import com.github.seepick.derbauer2.game.common.Emoji
import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.core.CityTitle
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.stat.Happiness
import com.github.seepick.derbauer2.game.tech.`tech 🔬`
import com.github.seepick.derbauer2.game.trade.`trade 💸`
import com.github.seepick.derbauer2.game.turn.Season

@Suppress("MayBeConstant", "VariableMaxLength", "ClassOrdering")
object Texts {

    val buildPage = "Your builders are ready for work ${Emoji.Companion.`build 🛠️`}"
    val tradePage = "The good-humored merchant is here to do business with you ${Emoji.Companion.`trade 💸`}"
    fun techPage(emojiAndOwned: String) =
        "What is your philosopher heart desiring next? ${Emoji.`tech 🔬`}\n\n" +
                "Your genius wisdom expands $emojiAndOwned units of knowledge."

    fun poorPhilosopherHappening(cityTitle: CityTitle, goldGiving: Z) =
        "The old, poor philosopher from your ${cityTitle.label} approaches you, begging for some money 👴🏻 🏛️\n" +
                "\n" + "You give him some $goldGiving ${
            Gold.Data.emojiAndLabelFor(goldGiving)
        } and increase thereby the ${Happiness.Data.emojiAndLabel} 🙇🏻 🙏🏻"

    val techPageEmpty = "Your mind is empty...\nGo ahead and read some books first."
    val techItemAgriculture = "more food, more happy 🌽"
    val techItemIrrigation = "use little canals to make more mjam-mjam 🍗"
    val techItemCapitalism = "make the poor poorer, and yourself richer 🤑"

    val featureSeasonMultilineDescription =
        "By observing nature you discovered a reoccuring pattern " +
                Season.entries.joinToString(" ") { it.emoji.string }

}
