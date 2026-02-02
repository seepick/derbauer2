package com.github.seepick.derbauer2.game.trading

import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.emojiAndLabelPlural
import com.github.seepick.derbauer2.game.core.gold
import com.github.seepick.derbauer2.game.core.hasEntity
import com.github.seepick.derbauer2.game.feature.Feature
import com.github.seepick.derbauer2.game.feature.FeatureDescriptor
import com.github.seepick.derbauer2.game.feature.FeatureDescriptorType
import com.github.seepick.derbauer2.game.feature.hasFeature
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.landAvailable
import com.github.seepick.derbauer2.game.view.AsciiArt

// ================= Trading Feature =================

class TradingFeature(descriptor: Descriptor = Descriptor) : Feature(descriptor) {
    override val discriminator = Discriminator.Trading(this)
    override fun deepCopy() = this // immutable

    object Descriptor : FeatureDescriptor(
        label = "Trading",
        asciiArt = AsciiArt.coin,
        multilineDescription = "A marketplace has opened for you. Enjoy you darn capitalist! 🤑",
    ) {
        override val enumIdentifier = FeatureDescriptorType.Trading
        override fun check(user: User) =
            user.hasEntity<Gold>() &&
                    user.gold <= Mechanics.featureTradingThresholdGoldLesser
        // FIXME check for turns, and/or money owned
//                user.storageUsage<Food>() >= 0.8.`%`

        override fun build() = TradingFeature()
    }
}

// ================= Trade Land Feature =================

object TradeLandFeatureDescriptor : FeatureDescriptor(
    label = "Trade Land",
    asciiArt = AsciiArt.island,
    multilineDescription = "You can now buy ${Land.Data.emojiAndLabelPlural} for some other stuff.\n" +
            "And some more... hehe 😅",
) {
    override val enumIdentifier = FeatureDescriptorType.TradeLand
    override fun check(user: User) =
        user.hasFeature<TradingFeature>() &&
                user.hasEntity<Land>() &&
                user.landAvailable <= Mechanics.featureTradeLandThresholdLandAvailableLesser

    override fun build() = TradeLandFeature(this)
}

class TradeLandFeature(descriptor: TradeLandFeatureDescriptor) : Feature(descriptor) {
    override val discriminator = Discriminator.TradeLand(this)
    override fun deepCopy() = this // immutable
}
