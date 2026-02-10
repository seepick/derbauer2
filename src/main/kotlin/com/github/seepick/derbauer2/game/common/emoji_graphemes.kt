@file:Suppress("MagicNumber")

package com.github.seepick.derbauer2.game.common

import com.github.seepick.derbauer2.game.core.AiGenerated

fun String.countGraphemes(): Int = extractGraphemes().size

@AiGenerated
@Suppress("MagicNumber", "VariableMinLength")
fun String.extractGraphemes(): List<String> {
    val result = mutableListOf<String>()
    var start = 0
    var index = 0
    while (index < length) {
        index = consumeBaseCodePoint(index)
        while (index < length) {
            val next = codePointAt(index)
            val newIndex = consumeExtraCodePoint(this, index, next) ?: break
            index = newIndex
        }
        result.add(substring(start, index))
        start = index
    }
    return result
}

private fun String.consumeBaseCodePoint(index: Int): Int =
    index + Character.charCount(codePointAt(index))

private fun consumeExtraCodePoint(text: String, index: Int, codePoint: Int): Int? = when {
    codePoint.isVariationSelector() -> index + Character.charCount(codePoint)
    codePoint.isCombiningMark() -> index + Character.charCount(codePoint)
    codePoint.isSkinToneModifier() -> index + Character.charCount(codePoint)
    codePoint.isZwj() -> consumeZwjSequence(text, index)
    else -> null
}

private fun Int.isVariationSelector(): Boolean = this in 0xFE00..0xFE0F || this in 0xE0100..0xE01EF
private fun Int.isCombiningMark(): Boolean = this in 0x0300..0x036F
private fun Int.isSkinToneModifier(): Boolean = this in 0x1F3FB..0x1F3FF
private fun Int.isZwj(): Boolean = this == 0x200D

@Suppress("ReturnCount")
private fun consumeZwjSequence(text: String, index: Int): Int {
    var idx = index + Character.charCount(0x200D)
    if (idx >= text.length) {
        return idx
    }
    val afterZwj = text.codePointAt(idx)
    idx += Character.charCount(afterZwj)
    if (idx >= text.length) {
        return idx
    }
    val maybeVs = text.codePointAt(idx)
    if (maybeVs.isVariationSelector()) {
        idx += Character.charCount(maybeVs)
    }
    return idx
}
