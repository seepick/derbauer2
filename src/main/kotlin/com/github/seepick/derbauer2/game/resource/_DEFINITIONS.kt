@file:Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")

package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.common.emoji.Emoji
import com.github.seepick.derbauer2.common.unit.Z
import com.github.seepick.derbauer2.common.unit.z
import com.github.seepick.derbauer2.game.core.HasLabels
import com.github.seepick.derbauer2.game.tech.`knowledge 🧪`
import com.github.seepick.derbauer2.game.view.HasEmoji
import com.github.seepick.derbauer2.game.view.ViewOrder
import com.github.seepick.derbauer2.game.view.emojiAndLabelSingular

class Citizen : StorableResource, HasLabels by Data, HasEmoji by Data, ViewOrder by Data {
    object Data : HasLabels, HasEmoji, ViewOrder {
        override val labelSingular = "Citizen"
        override val emoji = Emoji.`citizen 🙎🏻‍♂️`
        override val viewOrder = ViewOrder.Resource.Citizen
    }

    override var _setOwnedInternal: Z = 0.z
    override fun deepCopy() = Citizen().also { it._setOwnedInternal = owned }
    override fun toString() = "${this.emojiAndLabelSingular}(owned=$owned)"
}

class Gold : Resource, HasLabels by Data, HasEmoji by Data, ViewOrder by Data {
    object Data : HasLabels, HasEmoji, ViewOrder {
        override val labelSingular = "Gold"
        override val labelPlural = labelSingular
        override val emoji = Emoji.`gold 💰`
        override val viewOrder = ViewOrder.Resource.Gold
    }

    override var _setOwnedInternal: Z = 0.z
    override fun deepCopy() = Gold().also { it._setOwnedInternal = owned }
    override fun toString() = "${this.emojiAndLabelSingular}(owned=$owned)"
}

class Food : StorableResource, HasLabels by Data, HasEmoji by Data, ViewOrder by Data {
    object Data : HasLabels, HasEmoji, ViewOrder {
        override val labelSingular = "Food"
        override val labelPlural = labelSingular
        override val emoji = Emoji.`food 🍖`
        override val viewOrder = ViewOrder.Resource.Food
    }

    override var _setOwnedInternal: Z = 0.z
    override fun deepCopy() = Food().also { it._setOwnedInternal = owned }
    override fun toString() = "${this.emojiAndLabelSingular}(owned=$owned)"
}

class Land : Resource, HasLabels by Data, HasEmoji by Data, ViewOrder by Data {
    object Data : HasLabels, HasEmoji, ViewOrder {
        override val labelSingular = "Land"
        override val labelPlural = labelSingular
        override val emoji = Emoji.`land 🌍`
        override val viewOrder = ViewOrder.Resource.Land
    }

    override var _setOwnedInternal: Z = 0.z
    override fun deepCopy() = Land().also { it._setOwnedInternal = owned }
    override fun toString() = "${this.emojiAndLabelSingular}(owned=$owned)"
}

class Knowledge : Resource, HasLabels by Data, HasEmoji by Data, ViewOrder by Data {
    object Data : HasLabels, HasEmoji, ViewOrder {
        override val labelSingular = "Knowledge"
        override val labelPlural = labelSingular
        override val emoji = Emoji.`knowledge 🧪`
        override val viewOrder = ViewOrder.Resource.Knowledge
    }

    override var _setOwnedInternal: Z = 0.z
    override fun deepCopy() = Knowledge().also { it._setOwnedInternal = owned }
    override fun toString() = "${this.emojiAndLabelSingular}(owned=$owned)"
}
