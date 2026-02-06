package com.github.seepick.derbauer2.game.core

import com.github.seepick.derbauer2.game.building.`building 🛠️`
import com.github.seepick.derbauer2.game.common.Emoji
import com.github.seepick.derbauer2.game.tech.`tech 🔬`
import com.github.seepick.derbauer2.game.trading.`trade 💸`

@Suppress("MayBeConstant", "VariableMaxLength")
object Texts {

    val buildingPage = "Your builders are ready for work ${Emoji.`building 🛠️`}"
    val tradingPage = "The good-humored merchant is here to do business with you ${Emoji.`trade 💸`}"
    val techPage = "What is your philosopher heart desiring next ${Emoji.`tech 🔬`}"
    val techPageEmpty = "Your mind is empty...\nGo ahead and read some books first."
    val techItemAgriculture = "more food, more happy 🌽"
    val techItemIrrigation = "use little canals to make more mjam-mjam 🍗"
    val techItemCapitalism = "make the poor poorer, and yourself richer 🤑"

}
