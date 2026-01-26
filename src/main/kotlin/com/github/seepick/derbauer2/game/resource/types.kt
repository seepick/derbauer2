package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.logic.Asset
import com.github.seepick.derbauer2.game.logic.EmojiAndLabel
import com.github.seepick.derbauer2.game.logic.Zp
import kotlin.reflect.KClass

interface Resource : Asset {
    val emojiAndUnitsFormatted: String get() = "${emojiWithSpaceSuffixOrEmpty}${owned}"
}

interface StorableResource : Resource

interface ResourceReference {
    val resourceClass: KClass<out Resource>
}

data class Citizen(override var _setOwnedOnlyByTx: Zp) : StorableResource {
    companion object {
        const val EMOJI = "🧑"
    }
    override val labelSingular = "Citizen"
    override val emoji = EMOJI
}

data class Gold(override var _setOwnedOnlyByTx: Zp) : Resource {
    companion object {
        const val EMOJI = "💰"
        const val LABEL = "Gold"
        const val EMOJI_N_LABEL = "$EMOJI $LABEL"
    }
    override val labelSingular = LABEL
    override val labelPlural = labelSingular
    override val emoji = EMOJI
}

data class Food(override var _setOwnedOnlyByTx: Zp) : StorableResource {
    companion object {
        const val EMOJI = "🍖"
    }
    override val labelSingular = "Food"
    override val labelPlural = labelSingular
    override val emoji = EMOJI
}

data class Land(override var _setOwnedOnlyByTx: Zp) : Resource {
    // TODO simplify even more
    object Text : EmojiAndLabel("🌍", "Land")
    override val labelSingular = Text.label
    override val labelPlural = labelSingular
    override val emoji = Text.emoji
}
