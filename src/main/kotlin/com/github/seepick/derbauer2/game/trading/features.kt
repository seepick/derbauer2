package com.github.seepick.derbauer2.game.trading

import com.github.seepick.derbauer2.game.core.Action
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
import com.github.seepick.derbauer2.game.turn.Reports
import com.github.seepick.derbauer2.game.view.AsciiArt

class TradingFeature(descriptor: Descriptor = Descriptor) : Feature(descriptor) {

    override val discriminator = Discriminator.Trading(this)
    override fun mutate(user: User) {
        // nothing to do
    }

    override fun deepCopy() = this // immutable

    object Descriptor : FeatureDescriptor(
        label = "Trading",
        asciiArt = AsciiArt.coin,
        multilineDescription = "A marketplace has opened for you. Enjoy you darn capitalist! 🤑",
    ) {
        override val enumIdentifier = FeatureDescriptorType.Trading
        override fun check(user: User, reports: Reports) =
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
    override fun check(user: User, reports: Reports) =
        user.hasFeature<TradingFeature>() &&
                user.hasEntity<Land>() &&
                user.landAvailable <= Mechanics.featureTradeLandThresholdLandAvailableLesser

    override fun build() = TradeLandFeature()
}

class TradeLandFeature(descriptor: TradeLandFeatureDescriptor = TradeLandFeatureDescriptor) : Feature(descriptor) {
    override val discriminator = Discriminator.TradeLand(this)
    override fun deepCopy() = this // immutable
    override fun mutate(user: User) {
        // nothing to do, the feature just unlocks the option to trade land in the trading page
    }
    // TODO refactor inline descriptor
}

class FoodMerchantFeature(descriptor: Descriptor = Descriptor) : Feature(descriptor) {
    override val discriminator = Discriminator.FoodMerchant(this)
    override fun mutate(user: User) {
        // nothing to do
    }

    override fun deepCopy() = this // immutable

    object Descriptor : FeatureDescriptor(
        label = "Food Merchant",
        asciiArt = AsciiArt.coin,
        multilineDescription = "You have been busy trading your food 🍖 😋 🍖\n"
                + "You shall be rewarded by even moooooar cheesy, faty, yummy food trading! 💰 🤑 💰",
    ) {
        override val enumIdentifier = FeatureDescriptorType.FoodMerchant

        override fun check(user: User, reports: Reports) =
            reports.filterAllActionInstanceOf<ResourcesTradedAction>()
                .sumOf { action ->
                    action.requests.count { trade ->
                        trade.resourceClass == Food::class // bought or sold
                    }
                } >= Mechanics.featureFoodMerchantThresholdFoodTrades

        override fun build() = FoodMerchantFeature()
    }
}

inline fun <reified A : Action> Reports.filterAllActionInstanceOf(): List<A> =
    all.flatMap { it.actions }.filterIsInstance<A>()

