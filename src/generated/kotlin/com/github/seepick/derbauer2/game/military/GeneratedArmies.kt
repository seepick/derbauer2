package com.github.seepick.derbauer2.game.military

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Asset
import com.github.seepick.derbauer2.game.core.HasEmoji
import com.github.seepick.derbauer2.game.core.HasLabel
import com.github.seepick.derbauer2.game.core.emoji

/**
 * Base interface for military units/armies.
 * Based on documentation/cleanup/military.md
 */
interface Army : Asset {
    val costsGold: Z
    val costsCitizens: Z
    val attack: Z
    val defense: Z
}

/**
 * Soldier - basic military unit from barracks.
 * Based on documentation/cleanup/military.md
 */
class Soldier : Army, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Soldier"
        override val emojiOrNull = "⚔️".emoji
    }
    
    override var _setOwnedInternal: Z = 0.z
    override val costsGold = 50.z
    override val costsCitizens = 1.z
    override val attack = 10.z
    override val defense = 8.z
    
    override fun deepCopy() = Soldier().also { it._setOwnedInternal = owned }
    override fun toString() = "Soldier(owned=$owned)"
}

/**
 * Knight - cavalry unit from stable, stronger against wildlings.
 * Based on documentation/cleanup/military.md
 */
class Knight : Army, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Knight"
        override val emojiOrNull = "🐴".emoji
    }
    
    override var _setOwnedInternal: Z = 0.z
    override val costsGold = 120.z
    override val costsCitizens = 2.z
    override val attack = 20.z
    override val defense = 15.z
    
    override fun deepCopy() = Knight().also { it._setOwnedInternal = owned }
    override fun toString() = "Knight(owned=$owned)"
}

/**
 * Archer - ranged unit from archery, high defense.
 * Based on documentation/cleanup/military.md
 */
class Archer : Army, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Archer"
        override val emojiOrNull = "🏹".emoji
    }
    
    override var _setOwnedInternal: Z = 0.z
    override val costsGold = 70.z
    override val costsCitizens = 1.z
    override val attack = 12.z
    override val defense = 16.z
    
    override fun deepCopy() = Archer().also { it._setOwnedInternal = owned }
    override fun toString() = "Archer(owned=$owned)"
}

/**
 * Catapult - siege weapon, strong against empire targets.
 * Based on documentation/cleanup/military.md
 */
class Catapult : Army, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Catapult"
        override val emojiOrNull = "🎯".emoji
    }
    
    override var _setOwnedInternal: Z = 0.z
    override val costsGold = 200.z
    override val costsCitizens = 3.z
    override val attack = 30.z
    override val defense = 5.z
    
    override fun deepCopy() = Catapult().also { it._setOwnedInternal = owned }
    override fun toString() = "Catapult(owned=$owned)"
}

/**
 * AngryFarmer - cheap, weak militia that can be converted back.
 * Based on documentation/cleanup/military.md
 */
class AngryFarmer : Army, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Angry Farmer"
        override val emojiOrNull = "👨‍🌾".emoji
    }
    
    override var _setOwnedInternal: Z = 0.z
    override val costsGold = 20.z
    override val costsCitizens = 1.z
    override val attack = 5.z
    override val defense = 5.z
    
    override fun deepCopy() = AngryFarmer().also { it._setOwnedInternal = owned }
    override fun toString() = "AngryFarmer(owned=$owned)"
}

/**
 * Ram - siege unit, effective only against buildings/empire.
 * Based on documentation/cleanup/military.md
 */
class Ram : Army, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Ram"
        override val emojiOrNull = "🐏".emoji
    }
    
    override var _setOwnedInternal: Z = 0.z
    override val costsGold = 150.z
    override val costsCitizens = 2.z
    override val attack = 25.z
    override val defense = 10.z
    
    override fun deepCopy() = Ram().also { it._setOwnedInternal = owned }
    override fun toString() = "Ram(owned=$owned)"
}

/**
 * TrapBuilder - defensive specialist with zero attack.
 * Based on documentation/cleanup/military.md
 */
class TrapBuilder : Army, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Trap Builder"
        override val emojiOrNull = "🪤".emoji
    }
    
    override var _setOwnedInternal: Z = 0.z
    override val costsGold = 80.z
    override val costsCitizens = 1.z
    override val attack = 0.z
    override val defense = 20.z
    
    override fun deepCopy() = TrapBuilder().also { it._setOwnedInternal = owned }
    override fun toString() = "TrapBuilder(owned=$owned)"
}

/**
 * Wizard - magical unit with special abilities.
 * Based on documentation/cleanup/military.md
 */
class Wizard : Army, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Wizard"
        override val emojiOrNull = "🧙".emoji
    }
    
    override var _setOwnedInternal: Z = 0.z
    override val costsGold = 300.z
    override val costsCitizens = 1.z
    override val attack = 25.z
    override val defense = 20.z
    
    override fun deepCopy() = Wizard().also { it._setOwnedInternal = owned }
    override fun toString() = "Wizard(owned=$owned)"
}

/**
 * Scout - non-combat unit for reconnaissance.
 * Based on documentation/cleanup/military.md
 */
class Scout : Army, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Scout"
        override val emojiOrNull = "🔭".emoji
    }
    
    override var _setOwnedInternal: Z = 0.z
    override val costsGold = 40.z
    override val costsCitizens = 1.z
    override val attack = 2.z
    override val defense = 5.z
    
    override fun deepCopy() = Scout().also { it._setOwnedInternal = owned }
    override fun toString() = "Scout(owned=$owned)"
}
