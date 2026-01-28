package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.HasEmoji
import com.github.seepick.derbauer2.game.core.HasLabel
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.emoji
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.ProducesResourceOwnable
import com.github.seepick.derbauer2.game.resource.StoresResource

/**
 * Castle - multi-purpose building: stores citizens and food, provides features.
 * Based on documentation/cleanup/building.md
 */
class Castle : Building, StoresResource, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Castle"
        override val emojiOrNull = "🏰".emoji
    }
    override var _setOwnedInternal: Z = 0.z
    override val costsGold = 500.z
    override val landUse = 10.z
    override val storableResourceClass = Citizen::class
    override val storageAmount = 50.z

    override fun deepCopy() = Castle().also { it._setOwnedInternal = owned }
    override fun toString() = "Castle(owned=$owned)"
}

/**
 * Pub - increases citizen happiness.
 * Based on documentation/cleanup/building.md
 */
class Pub : Building, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Pub"
        override val emojiOrNull = "🍺".emoji
    }
    override var _setOwnedInternal: Z = 0.z
    override val costsGold = 100.z
    override val landUse = 2.z

    override fun deepCopy() = Pub().also { it._setOwnedInternal = owned }
    override fun toString() = "Pub(owned=$owned)"
}

/**
 * Church - increases citizen happiness and happening luck.
 * Based on documentation/cleanup/building.md
 */
class Church : Building, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Church"
        override val emojiOrNull = "⛪".emoji
    }
    override var _setOwnedInternal: Z = 0.z
    override val costsGold = 200.z
    override val landUse = 5.z

    override fun deepCopy() = Church().also { it._setOwnedInternal = owned }
    override fun toString() = "Church(owned=$owned)"
}

/**
 * Alchemy - enables upgrades and reduces upgrade prices.
 * Based on documentation/cleanup/building.md
 */
class Alchemy : Building, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Alchemy"
        override val labelPlural = "Alchemies"
        override val emojiOrNull = "⚗️".emoji
    }
    override var _setOwnedInternal: Z = 0.z
    override val costsGold = 300.z
    override val landUse = 3.z

    override fun deepCopy() = Alchemy().also { it._setOwnedInternal = owned }
    override fun toString() = "Alchemy(owned=$owned)"
}

/**
 * Workshop - for crafting and production.
 * Based on documentation/cleanup/building.md
 */
class Workshop : Building, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Workshop"
        override val emojiOrNull = "🔨".emoji
    }
    override var _setOwnedInternal: Z = 0.z
    override val costsGold = 150.z
    override val landUse = 3.z

    override fun deepCopy() = Workshop().also { it._setOwnedInternal = owned }
    override fun toString() = "Workshop(owned=$owned)"
}

/**
 * Town Hall - central administrative building.
 * Based on documentation/cleanup/building.md
 */
class TownHall : Building, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Town Hall"
        override val emojiOrNull = "🏛️".emoji
    }
    override var _setOwnedInternal: Z = 0.z
    override val costsGold = 400.z
    override val landUse = 8.z

    override fun deepCopy() = TownHall().also { it._setOwnedInternal = owned }
    override fun toString() = "TownHall(owned=$owned)"
}

/**
 * Monument - prestigious building.
 * Based on documentation/cleanup/building.md
 */
class Monument : Building, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Monument"
        override val emojiOrNull = "🗿".emoji
    }
    override var _setOwnedInternal: Z = 0.z
    override val costsGold = 600.z
    override val landUse = 6.z

    override fun deepCopy() = Monument().also { it._setOwnedInternal = owned }
    override fun toString() = "Monument(owned=$owned)"
}
