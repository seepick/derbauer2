package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Asset
import com.github.seepick.derbauer2.game.core.HasEmoji
import com.github.seepick.derbauer2.game.core.HasLabel
import com.github.seepick.derbauer2.game.core.emoji
import kotlin.reflect.KClass

interface Resource : Asset {
    val emojiAndOwned: String get() = "${emojiSpaceOrEmpty}${owned}"
}

interface StorableResource : Resource

interface ResourceReference {
    val resourceClass: KClass<out Resource>
}

class Citizen : StorableResource, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Citizen"
        override val emojiOrNull = "🧑".emoji
    }

    override var _setOwnedInternal: Z = 0.z
    override fun deepCopy() = Citizen().also { it._setOwnedInternal = owned }
    override fun toString() = "Citizen(owned=$owned)"
}

class Gold : Resource, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Gold"
        override val labelPlural = labelSingular
        override val emojiOrNull = "💰".emoji
    }

    override var _setOwnedInternal: Z = 0.z
    override fun deepCopy() = Gold().also { it._setOwnedInternal = owned }
    override fun toString() = "Gold(owned=$owned)"
}

class Food : StorableResource, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Food"
        override val labelPlural = labelSingular
        override val emojiOrNull = "🍖".emoji
    }

    override var _setOwnedInternal: Z = 0.z
    override fun deepCopy() = Food().also { it._setOwnedInternal = owned }
    override fun toString() = "Food(owned=$owned)"
}

class Land : Resource, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Land"
        override val labelPlural = labelSingular
        override val emojiOrNull = "🌍".emoji
    }

    override var _setOwnedInternal: Z = 0.z
    override fun deepCopy() = Land().also { it._setOwnedInternal = owned }
    override fun toString() = "Land(owned=$owned)"
}
