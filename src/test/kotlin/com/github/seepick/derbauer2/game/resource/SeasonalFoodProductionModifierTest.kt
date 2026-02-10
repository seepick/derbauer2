package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.foodProductionBonus
import com.github.seepick.derbauer2.game.turn.CurrentTurn
import com.github.seepick.derbauer2.game.turn.Season
import com.github.seepick.derbauer2.game.turn.invoke
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class SeasonalFoodProductionModifierTest : StringSpec({
    lateinit var user: User
    beforeTest {
        user = User()
    }
    "Given spring Then modify by spring modifier" {
        val season = Season.Spring

        val actual = SeasonalFoodProductionBonus(CurrentTurn(season)).productionBonus(user)

        actual shouldBe season.foodProductionBonus
    }
})
