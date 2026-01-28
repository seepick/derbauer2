package com.github.seepick.derbauer2.game.technology

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.HasEmoji
import com.github.seepick.derbauer2.game.core.HasLabel
import com.github.seepick.derbauer2.game.core.emoji

/**
 * Advanced Agriculture - increases farm production efficiency.
 * Based on documentation/cleanup/upgrade.md
 */
class AdvancedAgriculture : Technology, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Advanced Agriculture"
        override val emojiOrNull = "🌾".emoji
    }
    
    override var _setOwnedInternal: Z = 0.z
    val costsGold = 150.z
    val productionBonus = 20 // 20% increase
    
    override fun deepCopy() = AdvancedAgriculture().also { it._setOwnedInternal = owned }
    override fun toString() = "AdvancedAgriculture(owned=$owned)"
}

/**
 * Improved Storage - increases storage capacity for all resources.
 * Based on documentation/cleanup/upgrade.md
 */
class ImprovedStorage : Technology, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Improved Storage"
        override val emojiOrNull = "📦".emoji
    }
    
    override var _setOwnedInternal: Z = 0.z
    val costsGold = 200.z
    val capacityBonus = 25 // 25% increase
    
    override fun deepCopy() = ImprovedStorage().also { it._setOwnedInternal = owned }
    override fun toString() = "ImprovedStorage(owned=$owned)"
}

/**
 * Military Tactics - improves all army attack and defense.
 * Based on documentation/cleanup/upgrade.md and military.md
 */
class MilitaryTactics : Technology, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Military Tactics"
        override val emojiOrNull = "⚔️".emoji
    }
    
    override var _setOwnedInternal: Z = 0.z
    val costsGold = 300.z
    val attackBonus = 15 // 15% increase
    val defenseBonus = 15 // 15% increase
    
    override fun deepCopy() = MilitaryTactics().also { it._setOwnedInternal = owned }
    override fun toString() = "MilitaryTactics(owned=$owned)"
}

/**
 * Trade Routes - improves trade prices and regeneration.
 * Based on documentation/cleanup/upgrade.md and trade.md
 */
class TradeRoutes : Technology, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Trade Routes"
        override val emojiOrNull = "🛤️".emoji
    }
    
    override var _setOwnedInternal: Z = 0.z
    val costsGold = 250.z
    val priceBonus = 10 // 10% better prices
    
    override fun deepCopy() = TradeRoutes().also { it._setOwnedInternal = owned }
    override fun toString() = "TradeRoutes(owned=$owned)"
}

/**
 * Construction Techniques - reduces building costs.
 * Based on documentation/cleanup/upgrade.md
 */
class ConstructionTechniques : Technology, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Construction Techniques"
        override val emojiOrNull = "🏗️".emoji
    }
    
    override var _setOwnedInternal: Z = 0.z
    val costsGold = 180.z
    val buildingCostReduction = 15 // 15% cheaper buildings
    
    override fun deepCopy() = ConstructionTechniques().also { it._setOwnedInternal = owned }
    override fun toString() = "ConstructionTechniques(owned=$owned)"
}

/**
 * Happiness Initiatives - increases citizen happiness globally.
 * Based on documentation/cleanup/upgrade.md
 */
class HappinessInitiatives : Technology, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Happiness Initiatives"
        override val emojiOrNull = "😊".emoji
    }
    
    override var _setOwnedInternal: Z = 0.z
    val costsGold = 220.z
    val happinessBonus = 10 // +10 happiness
    
    override fun deepCopy() = HappinessInitiatives().also { it._setOwnedInternal = owned }
    override fun toString() = "HappinessInitiatives(owned=$owned)"
}

/**
 * Divine Favor - increases luck in happenings and karma effects.
 * Based on documentation/cleanup/upgrade.md
 */
class DivineFavor : Technology, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Divine Favor"
        override val emojiOrNull = "✨".emoji
    }
    
    override var _setOwnedInternal: Z = 0.z
    val costsGold = 400.z
    val karmaBonus = 20 // +20 karma/luck
    
    override fun deepCopy() = DivineFavor().also { it._setOwnedInternal = owned }
    override fun toString() = "DivineFavor(owned=$owned)"
}
