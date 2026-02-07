package com.github.seepick.derbauer2.game.happening.happenings

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.TxOwnable
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.emojiAndLabelSingular
import com.github.seepick.derbauer2.game.core.hasEntity
import com.github.seepick.derbauer2.game.happening.Happening
import com.github.seepick.derbauer2.game.happening.HappeningData
import com.github.seepick.derbauer2.game.happening.HappeningDescriptor
import com.github.seepick.derbauer2.game.happening.HappeningNature
import com.github.seepick.derbauer2.game.prob.Probs
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.findResource
import com.github.seepick.derbauer2.game.transaction.errorOnFail
import com.github.seepick.derbauer2.game.transaction.execTx
import com.github.seepick.derbauer2.game.turn.CurrentTurn
import com.github.seepick.derbauer2.game.view.AsciiArt
import com.github.seepick.derbauer2.textengine.textmap.Textmap

object RottenFoodDescriptor : HappeningDescriptor {

    override val nature = HappeningNature.Negative

    override fun canHappen(user: User, probs: Probs) =
        user.hasEntity(Food::class) &&
                user.findResource<Food>().owned > 0

    override fun willHappen(user: User, probs: Probs) = true

    override fun buildHappening(user: User): RottenFoodHappening {
        val rottenFood = user.findResource<Food>().owned.coerceAtMost(5.z)
        return RottenFoodHappening(rottenFood = rottenFood)
    }

    override fun initProb(probs: Probs, user: User, turn: CurrentTurn) {
        // not using probs
    }
}

data class RottenFoodHappening(
    val rottenFood: Z,
    private val descriptor: HappeningData = RottenFoodDescriptor,
) : Happening, HappeningData by descriptor {

    override val asciiArt = AsciiArt.rottenFood

    override fun render(textmap: Textmap) {
        textmap.line("We lost ${-rottenFood} ${Food.Data.emojiAndLabelSingular} by rotting away 🤢")
    }

    override fun execute(user: User) {
        user.execTx(TxOwnable(Food::class, -rottenFood)).errorOnFail()
    }
}

@Suppress("ObjectPropertyName")
private val _rottenFood = AsciiArt(
    """
       <},
       .-'\'-.
      ;_,._.,_;
        } ,'{ 
      ;`'-'-'`;
       '--'--'
      """.trimIndent()
)
val AsciiArt.Companion.rottenFood get() = _rottenFood
