package com.github.seepick.derbauer2.game.trading

import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.emojiAndLabelPlural
import com.github.seepick.derbauer2.game.core.hasEntity
import com.github.seepick.derbauer2.game.feature.Feature
import com.github.seepick.derbauer2.game.feature.FeatureDescriptor
import com.github.seepick.derbauer2.game.feature.FeatureDescriptorType
import com.github.seepick.derbauer2.game.feature.hasFeature
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.landAvailable
import com.github.seepick.derbauer2.game.resource.storageUsage
import com.github.seepick.derbauer2.game.view.AsciiArt

class TradingFeature(descriptor: Descriptor = Descriptor) : Feature(descriptor) {

    override val discriminator = Discriminator.Trading(this)
    override fun execute(user: User) {}

    override fun deepCopy() = this // immutable

    object Descriptor : FeatureDescriptor(
        label = "Trading",
        asciiArt = AsciiArt.coin,
        multilineDescription = "A marketplace has opened for you. Enjoy you darn capitalist! 🤑",
    ) {
        override val enumIdentifier = FeatureDescriptorType.Trading
        override fun check(user: User) =
            user.hasEntity<Food>() &&
                    user.storageUsage<Food>() >= Mechanics.featureTradingThresholdFoodStorageUsedBigger

        override fun build() = TradingFeature()
    }
}

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
    override fun execute(user: User) {}
}
