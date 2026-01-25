package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.logic.Asset
import com.github.seepick.derbauer2.game.logic.EmojiAndLabel
import com.github.seepick.derbauer2.game.logic.Units

interface Resource : Asset {
    val emojiAndUnitsFormatted: String get() = "${emojiWithSpaceSuffixOrEmpty}${owned}"
}

interface StorableResource : Resource

data class Citizen(override var owned: Units) : StorableResource {
    companion object {
        const val EMOJI = "🧑"
    }
    override val labelSingular = "Citizen"
    override val emoji = EMOJI
}

data class Gold(override var owned: Units) : Resource {
    companion object {
        const val EMOJI = "💰"
        const val LABEL = "Gold"
        const val EMOJI_N_LABEL = "$EMOJI $LABEL"
    }
    override val labelSingular = LABEL
    override val labelPlural = labelSingular
    override val emoji = EMOJI
}

data class Food(override var owned: Units) : StorableResource {
    companion object {
        const val EMOJI = "🍖"
    }
    override val labelSingular = "Food"
    override val labelPlural = labelSingular
    override val emoji = EMOJI
}

data class Land(override var owned: Units) : Resource {
    // TODO simplify even more
    object Text : EmojiAndLabel("🌍", "Land")
    override val labelSingular = Text.label
    override val labelPlural = labelSingular
    override val emoji = Text.emoji
}
