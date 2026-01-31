package com.github.seepick.derbauer2.game.happening.happenings

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.emojiAndLabelFor
import com.github.seepick.derbauer2.game.happening.Happening
import com.github.seepick.derbauer2.game.happening.HappeningData
import com.github.seepick.derbauer2.game.happening.HappeningId
import com.github.seepick.derbauer2.game.happening.HappeningNature
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.execTxResource
import com.github.seepick.derbauer2.game.resource.resource
import com.github.seepick.derbauer2.game.transaction.errorOnFail
import com.github.seepick.derbauer2.game.view.AsciiArt
import com.github.seepick.derbauer2.textengine.Textmap

object RatsEatFoodDescriptor : HappeningDescriptor(HappeningNature.Negative) {
    override val id = HappeningId.RatsEatFood

    override fun canHappen(user: User) = user.hasEntity(Food::class) && user.resource<Food>().owned > 0

    override fun buildHappening(user: User): RatsEatFoodHappening {
        require(canHappen(user))
        return RatsEatFoodHappening(amountFoodEaten = user.resource<Food>().owned.minOf(15.z))
    }
}

class RatsEatFoodHappening(
    val amountFoodEaten: Z, private val descriptor: HappeningData = RatsEatFoodDescriptor
) : Happening, HappeningData by descriptor {
    init {
        require(amountFoodEaten > 0) { "amountFoodEaten must be positive: $amountFoodEaten" }
    }

    override val asciiArt = AsciiArt.rat

    override fun render(textmap: Textmap) {
        textmap.line("Oh noes, rats have eaten ${-amountFoodEaten} ${Food.Data.emojiAndLabelFor(amountFoodEaten)}.")
    }

    override fun execute(user: User) {
        user.execTxResource(Food::class, -amountFoodEaten).errorOnFail()
    }
}

@Suppress("ObjectPropertyName")
private val _rat = AsciiArt(
    """
         .---.
      (\./)     \.......-
      >' '<  (__.'""${'"'}${'"'}
      " ` " "
      """.trimIndent()
)
val AsciiArt.Companion.rat get() = _rat
