package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.game.common.Emoji
import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.common.emoji
import com.github.seepick.derbauer2.game.core.HasLabel
import com.github.seepick.derbauer2.game.core.HasLabels

interface HasEmoji {
    val emoji: Emoji
}

val Any.emojiOrNull: Emoji? get() = (this as? HasEmoji)?.emoji
val Any.emojiSpaceOrEmpty: String get() = emojiOrNull?.let { "$it " } ?: ""

val <T> T.emojiAndLabel: String where T : HasLabel, T : HasEmoji
    get() = "$emojiSpaceOrEmpty$label"

val <T> T.emojiAndLabelSingular: String where T : HasLabels, T : HasEmoji
    get() = "$emojiSpaceOrEmpty$labelSingular"

val <T> T.emojiAndLabelPlural: String where T : HasLabels, T : HasEmoji
    get() = "$emojiSpaceOrEmpty$labelPlural"

fun <T> T.emojiAndLabelFor(unsignedAmount: Z) where T : HasLabels, T : HasEmoji =
    "$emojiSpaceOrEmpty${labelFor(unsignedAmount)}"

fun <T> T.emojiAndLabelFor(signedAmount: Zz) where T : HasLabels, T : HasEmoji =
    "$emojiSpaceOrEmpty${labelFor(signedAmount)}"

private val appEmoji = "🏰".emoji
@Suppress("ObjectPropertyName", "NonAsciiCharacters")
val Emoji.Companion.`derbauer2 🏰` get() = appEmoji
