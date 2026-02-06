package com.github.seepick.derbauer2.game.citizen

import com.github.seepick.derbauer2.game.common.Percent
import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.common.zz

object StarveCompute {
    fun howManyUnfed(citizens: Z, foodOwned: Zz, eatenFood: Z, eatRatio: Percent): Z = when {
        citizens == 0.z -> 0.z
        foodOwned <= 0.zz -> citizens
        (foodOwned - eatenFood) >= 0.zz -> 0.z
        else -> calculateUnfedCitizens(citizens, foodOwned, eatRatio)
    }

    private fun calculateUnfedCitizens(citizens: Z, foodOwned: Zz, eatRatio: Percent): Z {
        val citizensAmountFedByOneFood = eatRatio.neededToGetTo(1)
        val citizensFed = foodOwned.toZAbs() * citizensAmountFedByOneFood
        return citizens - citizensFed
    }
}
