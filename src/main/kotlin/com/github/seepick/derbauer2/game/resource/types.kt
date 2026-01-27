package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.core.Asset
import com.github.seepick.derbauer2.game.core.EmojiAndLabel
import kotlin.reflect.KClass

interface Resource : Asset {
    val emojiAndUnitsFormatted: String get() = "${emojiWithSpaceSuffixOrEmpty}${owned}"
}

interface StorableResource : Resource

interface ResourceReference {
    val resourceClass: KClass<out Resource>
}

data class Citizen(override var _setOwnedOnlyByTx: Z) : StorableResource {
    object Text : EmojiAndLabel("🧑", "Citizen")
    override val labelSingular = Text.label
    override val labelPlural = labelSingular
    override val emoji = Text.emoji
}

data class Gold(override var _setOwnedOnlyByTx: Z) : Resource {
    object Text : EmojiAndLabel("💰", "Gold")
    override val labelSingular = Text.label
    override val labelPlural = labelSingular
    override val emoji = Text.emoji
}

data class Food(override var _setOwnedOnlyByTx: Z) : StorableResource {
    object Text : EmojiAndLabel("🍖", "Food")
    override val labelSingular = Text.label
    override val labelPlural = labelSingular
    override val emoji = Text.emoji
}

data class Land(override var _setOwnedOnlyByTx: Z) : Resource {
    object Text : EmojiAndLabel("🌍", "Land")
    override val labelSingular = Text.label
    override val labelPlural = labelSingular
    override val emoji = Text.emoji
}
