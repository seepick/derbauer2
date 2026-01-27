package com.github.seepick.derbauer2.game.core

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.common.zz

interface Entity {
    val emoji: String? get() = null
    val emojiWithSpaceSuffixOrEmpty: String get() = emoji?.let { "$it " } ?: ""
}

/** A physical object (house, resource, people); not an abstract concept (tech) */
interface Asset : Entity, Ownable, NounLabel

interface EntityEffect

interface Ownable {
    @Suppress("DEPRECATION")
    val owned: Z get() = _setOwnedOnlyByTx

    @Suppress("PropertyName")
    @Deprecated("just don't use it unless you are within transaction application code")
    var _setOwnedOnlyByTx: Z

    // funny things possible ;) operator fun unaryMinus(): Zz = -owned
}

abstract class EmojiAndLabel(
    val emoji: String,
    val label: String,
) {
    val emojiAndLabel = "$emoji $label" // TODO if used for +1, then stuck with singular, but require plural
}

interface NounLabel {
    val labelSingular: String
    val labelPlural: String get() = labelSingular + "s"
    fun labelFor(units: Z) = if (units == 1.z) labelSingular else labelPlural
    fun labelFor(units: Zz) = if (units == 1.zz) labelSingular else labelPlural
}

val <T> T.emojiAndLabel: String where T : NounLabel, T : Entity
    get() = "$emojiWithSpaceSuffixOrEmpty$labelSingular"
