package com.github.seepick.derbauer2.game.logic

interface Entity {
    val emoji: String? get() = null
    val emojiWithSpaceSuffixOrEmpty: String get() = emoji?.let { "$it " } ?: ""
}

/** A physical object (house, resource, people); not an abstract concept (tech) */
interface Asset : Entity, Ownable, NounLabel

interface EntityEffect

interface Ownable {
    @Suppress("DEPRECATION")
    val owned: Zp get() = _setOwnedOnlyByTx

    @Suppress("PropertyName")
    @Deprecated("just don't use it unless you are within transaction application code")
    var _setOwnedOnlyByTx: Zp
}

abstract class EmojiAndLabel(
    val emoji: String,
    val label: String,
) {
    val emojiAndLabel = "$emoji $label"
}

interface NounLabel {
    val labelSingular: String
    val labelPlural: String get() = labelSingular + "s"
    fun labelFor(units: Zp) = if (units == 1.zp) labelSingular else labelPlural
    fun labelFor(units: Z) = if (units == 1.z) labelSingular else labelPlural
}

val <T> T.emojiAndLabel: String where T : NounLabel, T : Entity
    get() = "$emojiWithSpaceSuffixOrEmpty$labelSingular"
