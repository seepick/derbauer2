package com.github.seepick.derbauer2.game.feature

import com.github.seepick.derbauer2.game.core.UserTitleLordFeature
import com.github.seepick.derbauer2.game.stat.HappinessFeature
import com.github.seepick.derbauer2.game.tech.TechnologyFeature
import com.github.seepick.derbauer2.game.trade.FoodMerchantFeature
import com.github.seepick.derbauer2.game.trade.TradeFeature
import com.github.seepick.derbauer2.game.trade.TradeLandFeature
import com.github.seepick.derbauer2.game.turn.SeasonFeature

fun interface FeatureRefRegistry {
    fun getAll(): List<FeatureRef>
}

object DefaultFeatureRefRegistry : FeatureRefRegistry {
    override fun getAll() = listOf(
        TradeLandFeature.Ref,
        TechnologyFeature.Ref,
        HappinessFeature.Ref,
        TradeFeature.Ref,
        FoodMerchantFeature.Ref,
        SeasonFeature.Ref,
        UserTitleLordFeature.Ref,
        // ...
        // ..
        // .
    )
}
