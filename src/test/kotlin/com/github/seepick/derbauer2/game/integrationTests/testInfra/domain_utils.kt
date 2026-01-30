package com.github.seepick.derbauer2.game.integrationTests.testInfra

import com.github.seepick.derbauer2.game.view.PromptGamePage
import com.github.seepick.derbauer2.textengine.keyboard.KeyPressed
import com.github.seepick.derbauer2.textengine.keyboard.PrintChar
import com.github.seepick.derbauer2.textengine.prompt.Prompt
import io.github.oshai.kotlinlogging.KotlinLogging.logger

private val log = logger {}

enum class KeyInput(val asKeyPressed: KeyPressed) {
    Enter(KeyPressed.Command.Enter),
    Escape(KeyPressed.Command.Escape),
    Space(KeyPressed.Command.Space),
    Nr1(KeyPressed.Symbol(PrintChar.Numeric.One)),
    Nr2(KeyPressed.Symbol(PrintChar.Numeric.Two)),
    Nr3(KeyPressed.Symbol(PrintChar.Numeric.Three)),
    Nr4(KeyPressed.Symbol(PrintChar.Numeric.Four)),
    Nr5(KeyPressed.Symbol(PrintChar.Numeric.Five)),
    Nr6(KeyPressed.Symbol(PrintChar.Numeric.Six)),
    Nr7(KeyPressed.Symbol(PrintChar.Numeric.Seven)),
    Nr8(KeyPressed.Symbol(PrintChar.Numeric.Eight)),
    Nr9(KeyPressed.Symbol(PrintChar.Numeric.Nine));
    ;

    companion object {
        fun byNr(nr: Int): KeyInput = when (nr) {
            1 -> Nr1
            2 -> Nr2
            3 -> Nr3
            4 -> Nr4
            5 -> Nr5
            6 -> Nr6
            7 -> Nr7
            8 -> Nr8
            9 -> Nr9
            else -> error("Only 1-9 supported, but was: $nr")
        }
    }
}

fun PromptGamePage.indexOfOption(searchLabel: String): Int {
    val select = prompt as Prompt.Select
    val matching = select.options.mapIndexedNotNull { index, option ->
        if (option.label().lowercase().contains(searchLabel.lowercase())) {
            log.debug { "Selected option for '$searchLabel' at index ${index}: $option" }
            index + 1 to option
        } else {
            null
        }
    }
    require(matching.size == 1) {
        "Expected exactly one matching option for '$searchLabel', but was: $matching (all: ${select.options})"
    }
    return matching.first().first
}
