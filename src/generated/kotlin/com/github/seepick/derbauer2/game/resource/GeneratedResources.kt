package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.HasEmoji
import com.github.seepick.derbauer2.game.core.HasLabel
import com.github.seepick.derbauer2.game.core.emoji

/**
 * Wood resource - used for advanced buildings and certain armies.
 * Based on documentation/cleanup/resources.md
 */
class Wood : StorableResource, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Wood"
        override val labelPlural = labelSingular
        override val emojiOrNull = "🪵".emoji
    }

    override var _setOwnedInternal: Z = 0.z
    override fun deepCopy() = Wood().also { it._setOwnedInternal = owned }
    override fun toString() = "Wood(owned=$owned)"
}

/**
 * Stone resource - used for advanced buildings and ammunition for catapults.
 * Based on documentation/cleanup/resources.md
 */
class Stone : StorableResource, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Stone"
        override val labelPlural = labelSingular
        override val emojiOrNull = "🪨".emoji
    }

    override var _setOwnedInternal: Z = 0.z
    override fun deepCopy() = Stone().also { it._setOwnedInternal = owned }
    override fun toString() = "Stone(owned=$owned)"
}

/**
 * Knowledge resource - research points for technology.
 * Based on documentation/cleanup/resources.md and technology.md
 */
class Knowledge : Resource, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Knowledge"
        override val labelPlural = labelSingular
        override val emojiOrNull = "📚".emoji
    }

    override var _setOwnedInternal: Z = 0.z
    override fun deepCopy() = Knowledge().also { it._setOwnedInternal = owned }
    override fun toString() = "Knowledge(owned=$owned)"
}
