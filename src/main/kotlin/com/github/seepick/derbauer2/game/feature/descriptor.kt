package com.github.seepick.derbauer2.game.feature

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.stat.HappinessFeatureDescriptor
import com.github.seepick.derbauer2.game.tech.TechnologyFeatureDescriptor
import com.github.seepick.derbauer2.game.trading.TradeLandFeatureDescriptor
import com.github.seepick.derbauer2.game.trading.TradingFeature
import com.github.seepick.derbauer2.game.view.AsciiArt

/** Enforce exhaustion despite FeatureDescriptor not being a sealed class */
enum class FeatureDescriptorType(val descriptor: FeatureDescriptor) {
    TradeLand(TradeLandFeatureDescriptor),
    Technology(TechnologyFeatureDescriptor),
    Happiness(HappinessFeatureDescriptor),
    Trading(TradingFeature.Descriptor),
}

abstract class FeatureDescriptor(
    override val label: String,
    override val asciiArt: AsciiArt,
    override val multilineDescription: String,
) : FeatureData {

    @Suppress("unused")
    abstract val enumIdentifier: FeatureDescriptorType // only to enforce registration here ;)
    abstract fun check(user: User): Boolean
    abstract fun build(): Feature

    companion object {
        val all: List<FeatureDescriptor> by lazy {
            FeatureDescriptorType.entries.toList().map { it.descriptor }
        }
    }
}
