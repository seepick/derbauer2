package com.github.seepick.derbauer2.game.testInfra.pageParser

import com.github.seepick.derbauer2.game.core.Emoji

data class InfoLine(
    val cappedResources: List<CappedResourceInfo>,
    val singleResources: List<SingleResourceInfo>,
    val turn: Int,
)

data class SingleResourceInfo(
    val emoji: Emoji,
    val owned: Int,
)

data class CappedResourceInfo(
    val emoji: Emoji,
    val owned: Int,
    val capacity: Int,
)

fun GamePageParser.parseInfoLine(): InfoLine {
    val (singles, capped) = parseResourceInfos()
    return InfoLine(
        cappedResources = capped,
        singleResources = singles,
        turn = parseTurn(),
    )
}

fun GamePageParser.readSingleResource(emoji: Emoji): SingleResourceInfo =
    parseResourceInfos().first.single { it.emoji == emoji }

fun GamePageParser.parseTurn(): Int =
    infoRight.let {
        val turnPart = it.substringAfter("Turn").trim()
        return turnPart.toIntOrNull() ?: error("Cannot parse turn from infoRight: $infoRight")
    }

fun GamePageParser.parseResourceInfos(): Pair<List<SingleResourceInfo>, List<CappedResourceInfo>> {
    val singleResources = mutableListOf<SingleResourceInfo>()
    val cappedResources = mutableListOf<CappedResourceInfo>()
    // "🌍 10 | 💰 500 | 🍖 50 / 100 | 🌍 3 / 10 | 🙎🏻‍♂️ 4 / 5"
    infoLeft.split("|").map { it.trim() }.forEach { entry ->
        // entry an be "💰 460" or "🍖 50 / 100"
        val emoji = Emoji(entry.takeWhile { !it.isWhitespace() })
        val withoutEmoji = entry.substringAfter(" ").trim()
        if (withoutEmoji.contains("/")) {
            val (ownedStr, capacityStr) = withoutEmoji.split("/").map { it.trim() }
            cappedResources.add(
                CappedResourceInfo(
                    emoji = emoji,
                    owned = ownedStr.toInt(),
                    capacity = capacityStr.toInt(),
                )
            )
        } else {
            singleResources.add(
                SingleResourceInfo(
                    emoji = emoji,
                    owned = withoutEmoji.toInt(),
                )
            )
        }
    }
    return singleResources to cappedResources
}
