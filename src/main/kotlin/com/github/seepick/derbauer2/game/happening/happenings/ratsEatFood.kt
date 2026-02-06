package com.github.seepick.derbauer2.game.happening.happenings

import com.github.seepick.derbauer2.game.common.Percent
import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.TxOwnable
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.emojiAndLabelFor
import com.github.seepick.derbauer2.game.core.hasEntity
import com.github.seepick.derbauer2.game.happening.Happening
import com.github.seepick.derbauer2.game.happening.HappeningData
import com.github.seepick.derbauer2.game.happening.HappeningDescriptor
import com.github.seepick.derbauer2.game.happening.HappeningNature
import com.github.seepick.derbauer2.game.happening.HappeningType
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.findResource
import com.github.seepick.derbauer2.game.transaction.errorOnFail
import com.github.seepick.derbauer2.game.transaction.execTx
import com.github.seepick.derbauer2.game.turn.Season
import com.github.seepick.derbauer2.game.view.AsciiArt
import com.github.seepick.derbauer2.textengine.textmap.Textmap
import kotlin.random.Random

object RatsEatFoodDescriptor : HappeningDescriptor(HappeningNature.Negative) {
    override val type = HappeningType.RatsEatFood

    override fun canHappen(user: User): Boolean {
        if (!user.hasEntity(Food::class) || user.findResource<Food>().owned <= 0) {
            return false
        }
        return passesSeasonalProbability(user.turn.season)
    }

    override fun buildHappening(user: User): RatsEatFoodHappening {
        require(user.hasEntity(Food::class) && user.findResource<Food>().owned > 0)
        return RatsEatFoodHappening(amountFoodEaten = user.findResource<Food>().owned.coerceAtMost(15.z))
    }
}

private fun passesSeasonalProbability(season: Season): Boolean {
    val probability = seasonalProbability(season)
    return Random.nextDouble(0.0, 1.0) < probability.value
}

private fun seasonalProbability(season: Season): Percent = when (season) {
    Season.Winter -> Mechanics.ratsEatFoodProbabilityWinter
    Season.Autumn -> Mechanics.ratsEatFoodProbabilityAutumn
    Season.Spring -> Mechanics.ratsEatFoodProbabilitySpring
    Season.Summer -> Mechanics.ratsEatFoodProbabilitySummer
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
        user.execTx(TxOwnable(Food::class, -amountFoodEaten)).errorOnFail()
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
