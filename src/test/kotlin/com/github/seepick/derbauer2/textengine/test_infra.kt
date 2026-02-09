package com.github.seepick.derbauer2.textengine

import com.github.seepick.derbauer2.textengine.keyboard.KeyPressed
import com.github.seepick.derbauer2.textengine.keyboard.PrintChar
import com.github.seepick.derbauer2.textengine.prompt.OptionLabel
import com.github.seepick.derbauer2.textengine.prompt.Prompt
import com.github.seepick.derbauer2.textengine.prompt.SelectOption
import com.github.seepick.derbauer2.textengine.prompt.SelectPrompt
import com.github.seepick.derbauer2.textengine.prompt.SingleSelectPrompt
import com.github.seepick.derbauer2.textengine.textmap.Textmap
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.kotest.assertions.withClue
import io.kotest.matchers.collections.shouldBeSingleton
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.string

private val log = logger {}

fun Arb.Companion.selectOption() = arbitrary {
    SelectOption(label = string(1..5).bind(), onSelected = {})
}

fun Renderer.renderTrimmedFullString(textmap: Textmap): String {
    render(textmap)
    val full = textmap.toFullString()
    return full.lines().joinToString("\n") { it.trimEnd() }
}

operator fun Textmap.Companion.invoke() = Textmap(1, 1)

// @formatter:off
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
// @formatter:on

fun Prompt.shouldHaveSelectOption(label: String) {
    this.shouldBeInstanceOf<SingleSelectPrompt>().options.items.map { it.label.value }
        .shouldContainSingleIgnoringCase(label)
}

fun List<String>.shouldContainSingleIgnoringCase(search: String): String =
    withClue({ "Expected to contain '$search' but was: ${joinToString { "'$it'" }}" }) {
        filter {
            it.contains(search, ignoreCase = true)
        }.shouldBeSingleton().first()
    }

fun Prompt.indexOfOption(searchLabel: String) =
    indexOfOptionOrNull(searchLabel) ?: error("No option found for label: '$searchLabel'")

fun Prompt.indexOfOptionOrNull(searchLabel: String): Int? {
    val select = this.shouldBeInstanceOf<SelectPrompt<*, *>>()
    val matching = select.options.items.mapIndexedNotNull { index, option ->
        if (option.label.evaluate().lowercase().contains(searchLabel.lowercase())) {
            log.debug { "Selected option for '$searchLabel' at index ${index}: $option" }
            index + 1 to option
        } else {
            null
        }
    }
    return if (matching.isEmpty()) {
        null
    } else {
        withClue({ "Expected single option for '$searchLabel' but was: $matching (all: ${select.options})" }) {
            matching.shouldBeSingleton().first().first
        }
    }
}

fun OptionLabel.evaluate(): String = when (this) {
    is OptionLabel.Single -> value
    is OptionLabel.Table -> columns.joinToString(" ")
}

