@file:AiGenerated
@file:Suppress("MagicNumber")

package com.github.seepick.derbauer2.game.common

import java.text.BreakIterator

fun String.requireIsSingleEmoji() {
    require(isNotEmpty()) { "Empty string is not a valid emoji." }
    val graphemeCount = graphemeClusterCount()
    val codePoints = codePoints().toArray()
    val emojiBaseCount = codePoints.count { it.isEmojiBase() }
    require(isValidSingleEmojiSequence(graphemeCount, codePoints, emojiBaseCount)) {
        "Not a single emoji sequence (graphemes: $graphemeCount, emojiBases: $emojiBaseCount) for [$this]"
    }
    require(emojiBaseCount >= 1) { "Not a recognized emoji sequence: [$this]" }
}

@Suppress("AlsoCouldBeApply")
private fun String.graphemeClusterCount(): Int {
    val charIterator = BreakIterator.getCharacterInstance().also { it.setText(this) }
    return generateSequence { charIterator.next() }
        .takeWhile { it != BreakIterator.DONE }
        .count()
}

private fun Int.isEmojiBase(): Boolean = when (this) {
    in 0x1F600..0x1F64F,
    in 0x1F300..0x1F5FF,
    in 0x1F680..0x1F6FF,
    in 0x1F1E6..0x1F1FF,
    in 0x2600..0x26FF,
    in 0x2700..0x27BF,
    in 0x1F900..0x1F9FF,
    in 0x1FA70..0x1FAFF,
        -> true

    else -> false
}

private fun isValidSingleEmojiSequence(
    graphemeCount: Int,
    cps: IntArray,
    emojiBaseCount: Int,
): Boolean = when {
    graphemeCount == 1 -> true
    cps.hasZwj() -> true
    cps.regionalIndicatorCount() == 2 -> true
    emojiBaseCount == 1 -> true
    else -> false
}

private fun IntArray.hasZwj(): Boolean = any { it == 0x200D }

private fun IntArray.regionalIndicatorCount(): Int = count { it in 0x1F1E6..0x1F1FF }
