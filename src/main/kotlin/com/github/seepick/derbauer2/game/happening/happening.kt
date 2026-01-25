package com.github.seepick.derbauer2.game.happening

import com.github.seepick.derbauer2.game.logic.Units
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.logic.units
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.textengine.Textmap
import kotlin.random.Random

interface Happening {
    fun render(textmap: Textmap)
    fun execute(user: User)
}

class FoundGoldHappening(val goldFound: Units) : Happening {
    override fun render(textmap: Textmap) {
        textmap.printLine("Found $goldFound GOLD!!!")
    }

    override fun execute(user: User) {
        user.resource(Gold::class).owned += goldFound
    }
}

class HappeningTurner(
    private val user: User,
) {
     fun turn(): List<FoundGoldHappening> {
        return buildList {
            // TODO use a cooldown mechanism
            if(Random.nextDouble() >= 0.2) {
                // TODO consider history, gold amount (max, current, avg over last x-turns), etc.
                add(FoundGoldHappening(goldFound = 20.units))
            }
        }
    }
}