package com.github.seepick.derbauer2.game.happening.happenings

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.TxOwnable
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.emojiAndLabelFor
import com.github.seepick.derbauer2.game.core.hasEntity
import com.github.seepick.derbauer2.game.core.ratsWillEatFoodProb
import com.github.seepick.derbauer2.game.happening.Happening
import com.github.seepick.derbauer2.game.happening.HappeningData
import com.github.seepick.derbauer2.game.happening.HappeningNature
import com.github.seepick.derbauer2.game.happening.HappeningRef
import com.github.seepick.derbauer2.game.prob.ProbThresholderKey
import com.github.seepick.derbauer2.game.prob.Probs
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.findResource
import com.github.seepick.derbauer2.game.transaction.errorOnFail
import com.github.seepick.derbauer2.game.transaction.execTx
import com.github.seepick.derbauer2.game.turn.CurrentTurn
import com.github.seepick.derbauer2.game.view.AsciiArt
import com.github.seepick.derbauer2.textengine.textmap.Textmap

private val ratsWillHappenForSeasonKey = ProbThresholderKey("ratsWillHappenForSeason")
val ProbThresholderKey.Companion.ratsWillHappenForSeason get() = ratsWillHappenForSeasonKey

data class RatsEatFoodHappening(
    val amountFoodEaten: Z,
    private val descriptor: HappeningData = Ref,
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

    object Ref : HappeningRef {
        override val nature = HappeningNature.Negative

        override fun initProb(probs: Probs, user: User, turn: CurrentTurn) {
            probs.setThresholder(ProbThresholderKey.ratsWillHappenForSeason) {
                turn.current.season.ratsWillEatFoodProb
            }
        }

        override fun canHappen(user: User, probs: Probs) =
            user.hasEntity(Food::class) &&
                    user.findResource<Food>().owned > 0

        override fun willHappen(user: User, probs: Probs) =
            probs.isThresholdReached(ProbThresholderKey.ratsWillHappenForSeason)

        override fun buildHappening(user: User): RatsEatFoodHappening {
            val foodEaten = user.findResource<Food>().owned.coerceAtMost(15.z)
            return RatsEatFoodHappening(amountFoodEaten = foodEaten)
        }
    }

}

private val ratAscii = AsciiArt(
    """
         .---.
      (\./)     \.......-
      >' '<  (__.'""${'"'}${'"'}
      " ` " "
      """.trimIndent()
)

val AsciiArt.Companion.rat get() = ratAscii
