package com.github.seepick.derbauer2.game.feature

import com.github.seepick.derbauer2.game.stat.HappinessFeature
import com.github.seepick.derbauer2.game.tech.TechnologyFeature
import com.github.seepick.derbauer2.game.trading.FoodMerchantFeature
import com.github.seepick.derbauer2.game.trading.TradeLandFeature
import com.github.seepick.derbauer2.game.trading.TradingFeature

fun interface FeatureRefRegistry {
    fun getAll(): List<FeatureRef>
}

object DefaultFeatureRefRegistry : FeatureRefRegistry {
    override fun getAll() = listOf(
        TradeLandFeature.Ref,
        TechnologyFeature.Ref,
        HappinessFeature.Ref,
        TradingFeature.Ref,
        FoodMerchantFeature.Ref,
        // ...
        // ..
        // .
    )
}
