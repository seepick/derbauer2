package com.github.seepick.derbauer2.game.trade

import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.emojiAndLabelPlural
import com.github.seepick.derbauer2.game.core.hasEntity
import com.github.seepick.derbauer2.game.feature.FeatureImpl
import com.github.seepick.derbauer2.game.feature.FeatureRef
import com.github.seepick.derbauer2.game.feature.hasFeature
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.landAvailable
import com.github.seepick.derbauer2.game.resource.storageUsage
import com.github.seepick.derbauer2.game.turn.Reports
import com.github.seepick.derbauer2.game.view.AsciiArt

class TradeFeature(descriptor: Ref = Ref) : FeatureImpl(descriptor) {

    override fun deepCopy() = this // immutable
    override fun mutate(user: User) {}

    object Ref : FeatureRef(
        label = "Trading",
        asciiArt = AsciiArt.coin,
        multilineDescription = "A marketplace has opened for you. Enjoy you darn capitalist! 🤑",
    ) {
        override fun check(user: User, reports: Reports) =
            user.hasEntity<Food>() &&
                    user.storageUsage<Food>() >= Mechanics.featureTradeThresholdFoodStorageUsedBigger

        override fun build() = TradeFeature()
    }
}


class TradeLandFeature(descriptor: Ref = Ref) : FeatureImpl(descriptor) {
    override fun deepCopy() = this // immutable
    override fun mutate(user: User) {
        // nothing to do, the feature just unlocks the option to trade land in the trading page
    }

    object Ref : FeatureRef(
        label = "Trade Land",
        asciiArt = AsciiArt.island,
        multilineDescription = "You can now buy ${Land.Data.emojiAndLabelPlural} for some other stuff.\n" +
                "And some more... hehe 😅",
    ) {
        override fun check(user: User, reports: Reports) =
            user.hasFeature<TradeFeature>() &&
                    user.hasEntity<Land>() &&
                    user.landAvailable <= Mechanics.featureTradeLandThresholdLandAvailableLesser

        override fun build() = TradeLandFeature()
    }
}

class FoodMerchantFeature(descriptor: Ref = Ref) : FeatureImpl(descriptor) {

    override fun mutate(user: User) {}
    override fun deepCopy() = this // immutable

    object Ref : FeatureRef(
        label = "Food Merchant",
        asciiArt = AsciiArt.coin,
        multilineDescription = "You have been busy trading your food 🍖 😋 🍖\n"
                + "You shall be rewarded by even moooooar cheesy, faty, yummy food trading! 💰 🤑 💰",
    ) {
        /** Check if within one round >=X times food traded && only if that happened >=Y times. */
        override fun check(user: User, reports: Reports) =
            reports.all.count { report ->
                report.actions.filterIsInstance<ResourcesTradedAction>().count { trade ->
                    trade.requests.any { request -> request.resourceClass == Food::class } // bought or sold
                } >= Mechanics.featureFoodMerchantThresholdFoodTradesAmount
            } >= Mechanics.featureFoodMerchantThresholdFoodTradesAmountTimes

        override fun build() = FoodMerchantFeature()
    }
}
