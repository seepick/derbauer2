package com.github.seepick.derbauer2.game.logic

interface Entity {
    val emoji: String? get() = null
    val emojiWithSpaceSuffixOrEmpty: String get() = emoji?.let { "$it " } ?: ""
}

/** A physical object (house, resource, people); not an abstract concept (tech) */
interface Asset : Entity, Ownable, NounLabel

interface EntityEffect

interface Ownable {
    var owned: Units // FIXME make val
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
    fun labelFor(units: Units) = if (units == 1.units) labelSingular else labelPlural
}

val <T> T.emojiAndLabel: String where T : NounLabel, T : Entity
    get() = "$emojiWithSpaceSuffixOrEmpty$labelSingular"
