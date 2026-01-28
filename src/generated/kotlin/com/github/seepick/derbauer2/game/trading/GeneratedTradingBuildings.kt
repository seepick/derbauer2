package com.github.seepick.derbauer2.game.trading

import com.github.seepick.derbauer2.game.building.Building
import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.HasEmoji
import com.github.seepick.derbauer2.game.core.HasLabel
import com.github.seepick.derbauer2.game.core.emoji

/**
 * Marketplace - improves trade regeneration and prices.
 * Based on documentation/cleanup/trade.md
 */
class Marketplace : Building, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Marketplace"
        override val emojiOrNull = "🏪".emoji
    }
    
    override var _setOwnedInternal: Z = 0.z
    override val costsGold = 200.z
    override val landUse = 4.z
    val priceImprovement = 10 // 10% better prices
    val regenerationBonus = 20 // 20% faster regeneration
    
    override fun deepCopy() = Marketplace().also { it._setOwnedInternal = owned }
    override fun toString() = "Marketplace(owned=$owned)"
}

/**
 * Trading Post - enables advanced trading routes.
 * Based on documentation/cleanup/trade.md
 */
class TradingPost : Building, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Trading Post"
        override val emojiOrNull = "🛒".emoji
    }
    
    override var _setOwnedInternal: Z = 0.z
    override val costsGold = 250.z
    override val landUse = 3.z
    val tradeLimitIncrease = 50 // +50 to available trade amounts
    
    override fun deepCopy() = TradingPost().also { it._setOwnedInternal = owned }
    override fun toString() = "TradingPost(owned=$owned)"
}

/**
 * Harbor/Port - enables water trade routes and army trading.
 * Based on documentation/cleanup/trade.md
 */
class Harbor : Building, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Harbor"
        override val emojiOrNull = "⚓".emoji
    }
    
    override var _setOwnedInternal: Z = 0.z
    override val costsGold = 500.z
    override val landUse = 8.z
    
    override fun deepCopy() = Harbor().also { it._setOwnedInternal = owned }
    override fun toString() = "Harbor(owned=$owned)"
}
