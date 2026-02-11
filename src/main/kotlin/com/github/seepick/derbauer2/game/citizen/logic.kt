package com.github.seepick.derbauer2.game.citizen

import com.github.seepick.derbauer2.common.unit.Percent
import com.github.seepick.derbauer2.common.unit.Z
import com.github.seepick.derbauer2.common.unit.Zz
import com.github.seepick.derbauer2.common.unit.z
import com.github.seepick.derbauer2.common.unit.zz

object StarveCompute {

    fun howManyUnfed(citizens: Z, foodOwned: Zz, eatenFood: Z, eatRatio: Percent): Z = when {
        citizens == 0.z -> 0.z
        foodOwned <= 0.zz -> citizens
        (foodOwned - eatenFood) >= 0.zz -> 0.z
        else -> calculateUnfedCitizens(citizens, foodOwned, eatRatio)
    }

    private fun calculateUnfedCitizens(citizens: Z, foodOwned: Zz, eatRatio: Percent): Z {
        val citizensAmountFedByOneFood = eatRatio.neededToGetTo(1)
        val citizensMaxPossibleFed = foodOwned.toZAbs() * citizensAmountFedByOneFood
        return if (citizensMaxPossibleFed > citizens) {
            0.z
        } else {
            citizens - citizensMaxPossibleFed
        }
    }
}
