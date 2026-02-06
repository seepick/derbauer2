package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.emoji
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Asset
import com.github.seepick.derbauer2.game.core.HasEmoji
import com.github.seepick.derbauer2.game.core.HasLabels

interface Resource : Asset {
    val emojiAndOwned: String get() = "${emojiSpaceOrEmpty}${owned}"
}

interface StorableResource : Resource

class Citizen : StorableResource, HasLabels by Data, HasEmoji by Data {
    object Data : HasLabels, HasEmoji {
        override val labelSingular = "Citizen"
        override val emojiOrNull = "🙎🏻‍♂️".emoji
    }

    override var _setOwnedInternal: Z = 0.z
    override fun deepCopy() = Citizen().also { it._setOwnedInternal = owned }
    override fun toString() = "Citizen(owned=$owned)"
}

class Gold : Resource, HasLabels by Data, HasEmoji by Data {
    object Data : HasLabels, HasEmoji {
        override val labelSingular = "Gold"
        override val labelPlural = labelSingular
        override val emojiOrNull = "💰".emoji
    }

    override var _setOwnedInternal: Z = 0.z
    override fun deepCopy() = Gold().also { it._setOwnedInternal = owned }
    override fun toString() = "Gold(owned=$owned)"
}

class Food : StorableResource, HasLabels by Data, HasEmoji by Data {
    object Data : HasLabels, HasEmoji {
        override val labelSingular = "Food"
        override val labelPlural = labelSingular
        override val emojiOrNull = "🍖".emoji
    }

    override var _setOwnedInternal: Z = 0.z
    override fun deepCopy() = Food().also { it._setOwnedInternal = owned }
    override fun toString() = "Food(owned=$owned)"
}

class Land : Resource, HasLabels by Data, HasEmoji by Data {
    object Data : HasLabels, HasEmoji {
        override val labelSingular = "Land"
        override val labelPlural = labelSingular
        override val emojiOrNull = "🌍".emoji
    }

    override var _setOwnedInternal: Z = 0.z
    override fun deepCopy() = Land().also { it._setOwnedInternal = owned }
    override fun toString() = "Land(owned=$owned)"
}

class Knowledge : Resource, HasLabels by Data, HasEmoji by Data {
    object Data : HasLabels, HasEmoji {
        override val labelSingular = "Knowledge"
        override val labelPlural = labelSingular
        override val emojiOrNull = "🧪".emoji
    }

    override var _setOwnedInternal: Z = 0.z
    override fun deepCopy() = Knowledge().also { it._setOwnedInternal = owned }
    override fun toString() = "Knowledge(owned=$owned)"
}
