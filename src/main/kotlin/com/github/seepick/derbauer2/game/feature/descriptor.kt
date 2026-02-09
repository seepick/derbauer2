package com.github.seepick.derbauer2.game.feature

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.stat.HappinessFeature
import com.github.seepick.derbauer2.game.tech.TechnologyFeature
import com.github.seepick.derbauer2.game.trading.FoodMerchantFeature
import com.github.seepick.derbauer2.game.trading.TradeLandFeature
import com.github.seepick.derbauer2.game.trading.TradingFeature
import com.github.seepick.derbauer2.game.turn.Reports
import com.github.seepick.derbauer2.game.view.AsciiArt

/** Enforce exhaustion despite FeatureDescriptor not being a sealed class */
enum class FeatureDescriptorType(val descriptor: FeatureDescriptor) {
    TradeLand(TradeLandFeature.Descriptor),
    Technology(TechnologyFeature.Descriptor),
    Happiness(HappinessFeature.Descriptor),
    Trading(TradingFeature.Descriptor),
    FoodMerchant(FoodMerchantFeature.Descriptor)
}

abstract class FeatureDescriptor(
    override val label: String,
    override val asciiArt: AsciiArt,
    override val multilineDescription: String,
) : FeatureData {

    @Suppress("unused")
    abstract val enumIdentifier: FeatureDescriptorType // only to enforce registration here ;)
    abstract fun check(user: User, reports: Reports): Boolean
    abstract fun build(): Feature

    companion object {
        val all: List<FeatureDescriptor> by lazy {
            FeatureDescriptorType.entries.toList().map { it.descriptor }
        }
    }
}
