package com.github.seepick.derbauer2.game.happening

import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.logic.Z
import com.github.seepick.derbauer2.game.logic.z
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.txResource
import com.github.seepick.derbauer2.game.transaction.errorOnFail
import com.github.seepick.derbauer2.game.view.AsciiArt
import com.github.seepick.derbauer2.textengine.Textmap

sealed class HappeningDescriptor(
    override val nature: HappeningNature
) : HappeningData {

    companion object {
        val all: List<HappeningDescriptor> by lazy {
            listOf(
                FoundGold,
                RatsEatFood,
                // !!!!!!!!!!!!!!!!!!!!!
                // keep MANUALLY in sync
                // !!!!!!!!!!!!!!!!!!!!!
            )
        }
    }

    abstract fun build(user: User): Happening

    object FoundGold : HappeningDescriptor(HappeningNature.Positive) {
        override fun build(user: User): FoundGoldHappening {
            // TODO consider history, gold amount (max, current, avg over last x-turns), etc.
            return FoundGoldHappening(goldFound = 20.z, this)
        }
    }

    object RatsEatFood : HappeningDescriptor(HappeningNature.Negative) {
        override fun build(user: User): RatsEatFoodHappening {
            // TODO check food available; if nothing, maybe turn into luck message ;)
            return RatsEatFoodHappening(foodEaten = 15.z)
        }
    }
}

class FoundGoldHappening(val goldFound: Z, private val descriptor: HappeningData = HappeningDescriptor.FoundGold) :
    Happening, HappeningData by descriptor {
    override fun render(textmap: Textmap) {
        textmap.multiLine(AsciiArt.goldPot)
        textmap.emptyLine()
        textmap.line("Found $goldFound ${Gold.EMOJI_N_LABEL}")
    }

    override fun execute(user: User) {
        user.txResource(Gold::class, goldFound.asZ).errorOnFail()
    }
}

class RatsEatFoodHappening(
    val foodEaten: Z,
    private val descriptor: HappeningData = HappeningDescriptor.RatsEatFood
) : Happening, HappeningData by descriptor {
    override fun render(textmap: Textmap) {
        textmap.multiLine(AsciiArt.rat)
        textmap.emptyLine()
        textmap.line("Eaten $foodEaten ${Food.EMOJI}")
    }

    override fun execute(user: User) {
        // TODO add Happening.prerequisites (during happening selection phase), to filter those who need specific stuff (resources) to be existing; e.g. Food
        user.txResource(Food::class, -foodEaten).errorOnFail()
    }
}
