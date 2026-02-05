package com.github.seepick.derbauer2.game.citizen

import com.github.seepick.derbauer2.game.common.Percent
import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.common.zz
import io.github.oshai.kotlinlogging.KotlinLogging.logger

object StarveCompute {
    private val log = logger {}
    fun howManyUnfed(citizens: Z, foodOwned: Zz, eatenFood: Z, eatRatio: Percent): Z {
        log.trace { "citizens: $citizens, foodOwned: $foodOwned, eatenFood: $eatenFood, eatRatio: $eatRatio" }
        if (citizens == 0.z) {
            return 0.z
        }
        if (foodOwned <= 0.zz) {
            return citizens
        }
        val foodDiff = foodOwned - eatenFood
        log.trace { "foodDiff: $foodDiff" }
        if (foodDiff >= 0.zz) {
            return 0.z
        }
        val citizensAmountFedByOneFood = eatRatio.neededToGetTo(1)
        val citizensFed = foodOwned.toZAbs() * citizensAmountFedByOneFood
        return citizens - citizensFed
    }
}
