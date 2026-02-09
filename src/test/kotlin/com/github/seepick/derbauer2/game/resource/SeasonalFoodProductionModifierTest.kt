package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.zz
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.foodProductionModifier
import com.github.seepick.derbauer2.game.testInfra.zz
import com.github.seepick.derbauer2.game.turn.CurrentTurn
import com.github.seepick.derbauer2.game.turn.Season
import com.github.seepick.derbauer2.game.turn.invoke
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlin.math.floor

class SeasonalFoodProductionModifierTest : StringSpec({
    lateinit var user: User
    beforeTest {
        user = User()
    }
    "Given spring and some 🍖 Then modify by spring modifier" {
        val season = Season.Spring
        val sourceFood = 100

        val actual = SeasonalFoodProductionModifier(CurrentTurn(season)).modifyAmount(user, sourceFood.zz)

        actual shouldBe floor(sourceFood * season.foodProductionModifier).zz
    }
})
