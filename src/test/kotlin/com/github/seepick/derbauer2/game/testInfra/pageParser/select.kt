package com.github.seepick.derbauer2.game.testInfra.pageParser

import com.github.seepick.derbauer2.textengine.keyboard.PrintChar

fun GamePageParser.keyForSelectOption(labelContains: String): PrintChar.Numeric =
    contentLines.mapNotNull {
        if (it.contains(labelContains, ignoreCase = true)) {
            val match = Regex("""\[(\d)]""").find(it) ?: error("Unparsable option: $it")
            val keyChar = match.groupValues[1].first()
            PrintChar.Numeric.fromInt(keyChar.digitToInt())
        } else {
            null
        }
    }.single()
