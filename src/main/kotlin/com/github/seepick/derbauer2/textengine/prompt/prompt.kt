package com.github.seepick.derbauer2.textengine.prompt

import com.github.seepick.derbauer2.textengine.Renderer
import com.github.seepick.derbauer2.textengine.keyboard.KeyListener
import com.github.seepick.derbauer2.textengine.keyboard.KeyPressed
import com.github.seepick.derbauer2.textengine.keyboard.PrintChar
import com.github.seepick.derbauer2.textengine.textmap.Textmap
import com.github.seepick.derbauer2.textengine.textmap.TransformingTableCol
import io.github.oshai.kotlinlogging.KotlinLogging.logger

interface Prompt : KeyListener, Renderer {
    val inputIndicator: String
}

fun interface PromptProvider {
    fun buildPrompt(): Prompt
}

class EmptyPagePromptProvider(private val emptyMultiLineMessage: String) : Prompt {
    override val inputIndicator: String = KeyPressed.Command.Enter.label
    override fun render(textmap: Textmap) {
        textmap.multiLine(emptyMultiLineMessage)
    }

    override fun onKeyPressed(key: KeyPressed) = false
}

class SelectPrompt<LABEL : SelectOptionLabel, OPTIONS : Options<LABEL>>(
    val title: String, // TODO remove title; respect SRP
    val options: OPTIONS,
) : Prompt {
    private val log = logger {}

    init {
        @Suppress("MagicNumber") require(options.size in 1..9) {
            "Select prompt must have between 1 and 9 options, but has ${options.size}: $options"
        }
    }

    override val inputIndicator = "1-${options.size}"

    override fun onKeyPressed(key: KeyPressed) =
        if (key is KeyPressed.Symbol && key.char is PrintChar.Numeric && key.char.char in '1'..options.size.toString()
                .first()
        ) {
            val option = options.items[key.char.int - 1]
            log.debug { "Selected: $option" }
            option.onSelected()
            true
        } else {
            false
        }

    override fun render(textmap: Textmap) {
        textmap.line(title)
        textmap.emptyLine()
        when (options) {
            is Options.Singled<out SelectOptionLabel.Single> -> {
                options.items.mapIndexed { idx, opt ->
                    opt.label as SelectOptionLabel.Single
                    textmap.line("[${idx + 1}] ${opt.label.value}")
                }
            }

            is Options.Tabled -> {
                textmap.tableByTransform(
                    cols = buildList {
                        add(TransformingTableCol { rowIdx, _, opt ->
                            "[${rowIdx + 1}]"
                        })
                        addAll(
                            // TODO implicit contract that all columns are same length
                            (0..<(options.items.first().label.columns.size)).map {
                                TransformingTableCol { _, colIdx, opt ->
                                    opt.label.columns[colIdx - 1]
                                }
                            })
                    },
                    rowItems = options.items,
                )
            }
        }
    }

    companion object {
        operator fun <S : SelectOptionLabel.Single> invoke(title: String, items: List<SelectOption<S>>) =
            SelectPrompt(title, Options.Singled(items))
    }
}

sealed class Options<LABEL : SelectOptionLabel>(val items: List<SelectOption<out LABEL>>) {

    val size = items.size

    class Singled<SIMPLE : SelectOptionLabel.Single>(
        items: List<SelectOption<out SIMPLE>>,
    ) : Options<SIMPLE>(items)

    class Tabled(
        items: List<SelectOption<SelectOptionLabel.Table>>,
    ) : Options<SelectOptionLabel.Table>(items)

    companion object {
        operator fun <S : SelectOptionLabel.Single> invoke(items: List<SelectOption<S>>) = Singled(items)
    }
}

data class SelectOption<LABEL : SelectOptionLabel>(
    val label: LABEL,
    val onSelected: () -> Unit,
) {
    override fun toString() = "SelectOption(label=${label})"

    companion object {
        operator fun invoke(label: String, onSelected: () -> Unit): SelectOption<SelectOptionLabel.Single.Static> =
            SelectOption(SelectOptionLabel.Single.Static(label), onSelected)
    }
}

sealed interface SelectOptionLabel {
    sealed interface Single : SelectOptionLabel {
        val value
            get(): String = when (this) {
                is Static -> staticLabel
                is Dynamic -> provide()
            }

        data class Static(val staticLabel: String) : Single
        data class Dynamic(val provide: () -> String) : Single
    }

    class Table(val columns: List<String>) : SelectOptionLabel
}

typealias SingleSelectPrompt = SelectPrompt<SelectOptionLabel.Single, Options<SelectOptionLabel.Single>>
typealias StaticSelectPrompt = SelectPrompt<SelectOptionLabel.Single.Static, Options<SelectOptionLabel.Single.Static>>
typealias DynamicSelectPrompt = SelectPrompt<SelectOptionLabel.Single.Dynamic, Options<SelectOptionLabel.Single.Dynamic>>
