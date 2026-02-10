package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.Emoji
import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Asset
import com.github.seepick.derbauer2.game.core.HasEmoji
import com.github.seepick.derbauer2.game.core.HasLabels
import com.github.seepick.derbauer2.game.core.emojiAndLabelSingular
import com.github.seepick.derbauer2.game.tech.`knowledge 🧪`

interface Resource : Asset, HasLabels, HasEmoji

interface StorableResource : Resource

class Citizen : StorableResource, HasLabels by Data, HasEmoji by Data {
    object Data : HasLabels, HasEmoji {
        override val labelSingular = "Citizen"
        override val emoji = Emoji.`citizen 🙎🏻‍♂️`
    }

    override var _setOwnedInternal: Z = 0.z
    override fun deepCopy() = Citizen().also { it._setOwnedInternal = owned }
    override fun toString() = "${this.emojiAndLabelSingular}(owned=$owned)"
}

class Gold : Resource, HasLabels by Data, HasEmoji by Data {
    object Data : HasLabels, HasEmoji {
        override val labelSingular = "Gold"
        override val labelPlural = labelSingular
        override val emoji = Emoji.`gold 💰`
    }

    override var _setOwnedInternal: Z = 0.z
    override fun deepCopy() = Gold().also { it._setOwnedInternal = owned }
    override fun toString() = "${this.emojiAndLabelSingular}(owned=$owned)"
}

class Food : StorableResource, HasLabels by Data, HasEmoji by Data {
    object Data : HasLabels, HasEmoji {
        override val labelSingular = "Food"
        override val labelPlural = labelSingular
        override val emoji = Emoji.`food 🍖`
    }

    override var _setOwnedInternal: Z = 0.z
    override fun deepCopy() = Food().also { it._setOwnedInternal = owned }
    override fun toString() = "${this.emojiAndLabelSingular}(owned=$owned)"
}

class Land : Resource, HasLabels by Data, HasEmoji by Data {
    object Data : HasLabels, HasEmoji {
        override val labelSingular = "Land"
        override val labelPlural = labelSingular
        override val emoji = Emoji.`land 🌍`
    }

    override var _setOwnedInternal: Z = 0.z
    override fun deepCopy() = Land().also { it._setOwnedInternal = owned }
    override fun toString() = "${this.emojiAndLabelSingular}(owned=$owned)"
}

class Knowledge : Resource, HasLabels by Data, HasEmoji by Data {
    object Data : HasLabels, HasEmoji {
        override val labelSingular = "Knowledge"
        override val labelPlural = labelSingular
        override val emoji = Emoji.`knowledge 🧪`
    }

    override var _setOwnedInternal: Z = 0.z
    override fun deepCopy() = Knowledge().also { it._setOwnedInternal = owned }
    override fun toString() = "${this.emojiAndLabelSingular}(owned=$owned)"
}
