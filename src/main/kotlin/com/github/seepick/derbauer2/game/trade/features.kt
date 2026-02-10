package com.github.seepick.derbauer2.game.trade

import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.hasEntity
import com.github.seepick.derbauer2.game.feature.FeatureImpl
import com.github.seepick.derbauer2.game.feature.FeatureRef
import com.github.seepick.derbauer2.game.feature.hasFeature
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.landAvailable
import com.github.seepick.derbauer2.game.resource.storageUsage
import com.github.seepick.derbauer2.game.view.AsciiArt
import com.github.seepick.derbauer2.game.view.emojiAndLabelPlural

class TradeFeature(ref: Ref = Ref) : FeatureImpl(ref) {
    object Ref : FeatureRef(
        label = "Trading",
        asciiArt = AsciiArt.coin,
        multilineDescription = "A marketplace has opened for you. Enjoy you darn capitalist! 🤑",
        checkIt = { user, _ ->
            user.hasEntity<Food>() &&
                    user.storageUsage<Food>() >= Mechanics.featureTradeThresholdFoodStorageUsedBigger
        },
        buildIt = { TradeFeature() },
    )
}

class TradeLandFeature(ref: Ref = Ref) : FeatureImpl(ref) {
    object Ref : FeatureRef(
        label = "Trade Land",
        asciiArt = AsciiArt.island,
        multilineDescription = "You can now buy ${Land.Data.emojiAndLabelPlural} for some other stuff.\n" +
                "And some more... hehe 😅",
        checkIt = { user, _ ->
            user.hasFeature<TradeFeature>() &&
                    user.hasEntity<Land>() &&
                    user.landAvailable <= Mechanics.featureTradeLandThresholdLandAvailableLesser
        },
        buildIt = { TradeLandFeature() }
    )
}

class FoodMerchantFeature(ref: Ref = Ref) : FeatureImpl(ref) {
    object Ref : FeatureRef(
        label = "Food Merchant",
        asciiArt = AsciiArt.coin,
        multilineDescription = "You have been busy trading your food 🍖 😋 🍖\n"
                + "You shall be rewarded by even moooooar cheesy, faty, yummy food trading! 💰 🤑 💰",
        /** Check if within one round >=X times food traded && only if that happened >=Y times. */
        checkIt = { _, reports ->
            reports.all.count { report ->
                report.actions.filterIsInstance<ResourcesTradedAction>().count { trade ->
                    trade.requests.any { request -> request.resourceClass == Food::class } // bought or sold
                } >= Mechanics.featureFoodMerchantThresholdFoodTradesAmount
            } >= Mechanics.featureFoodMerchantThresholdFoodTradesAmountTimes
        },
        buildIt = { FoodMerchantFeature() }
    )
}
