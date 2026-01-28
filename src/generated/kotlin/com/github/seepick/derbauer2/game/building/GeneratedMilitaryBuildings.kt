package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.HasEmoji
import com.github.seepick.derbauer2.game.core.HasLabel
import com.github.seepick.derbauer2.game.core.emoji

/**
 * Barracks - enables hiring soldiers, basic military building.
 * Based on documentation/cleanup/military.md and building.md
 */
class Barracks : Building, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Barracks"
        override val labelPlural = "Barracks"
        override val emojiOrNull = "⚔️".emoji
    }
    override var _setOwnedInternal: Z = 0.z
    override val costsGold = 250.z
    override val landUse = 4.z

    override fun deepCopy() = Barracks().also { it._setOwnedInternal = owned }
    override fun toString() = "Barracks(owned=$owned)"
}

/**
 * Stable - enables hiring knights, increases cavalry effectiveness.
 * Based on documentation/cleanup/military.md
 */
class Stable : Building, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Stable"
        override val emojiOrNull = "🐴".emoji
    }
    override var _setOwnedInternal: Z = 0.z
    override val costsGold = 300.z
    override val landUse = 5.z

    override fun deepCopy() = Stable().also { it._setOwnedInternal = owned }
    override fun toString() = "Stable(owned=$owned)"
}

/**
 * Archery Range - enables hiring archers, increases defensive capabilities.
 * Based on documentation/cleanup/military.md
 */
class ArcheryRange : Building, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Archery Range"
        override val emojiOrNull = "🏹".emoji
    }
    override var _setOwnedInternal: Z = 0.z
    override val costsGold = 200.z
    override val landUse = 3.z

    override fun deepCopy() = ArcheryRange().also { it._setOwnedInternal = owned }
    override fun toString() = "ArcheryRange(owned=$owned)"
}

/**
 * Smithy - for forging weapons and armor.
 * Based on documentation/cleanup/military.md and building.md
 */
class Smithy : Building, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Smithy"
        override val labelPlural = "Smithies"
        override val emojiOrNull = "⚒️".emoji
    }
    override var _setOwnedInternal: Z = 0.z
    override val costsGold = 180.z
    override val landUse = 3.z

    override fun deepCopy() = Smithy().also { it._setOwnedInternal = owned }
    override fun toString() = "Smithy(owned=$owned)"
}

/**
 * University - research and knowledge building.
 * Based on documentation/cleanup/technology.md and building.md
 */
class University : Building, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "University"
        override val labelPlural = "Universities"
        override val emojiOrNull = "🎓".emoji
    }
    override var _setOwnedInternal: Z = 0.z
    override val costsGold = 500.z
    override val landUse = 7.z

    override fun deepCopy() = University().also { it._setOwnedInternal = owned }
    override fun toString() = "University(owned=$owned)"
}
