package com.github.seepick.derbauer2.textengine.prompt

import com.github.seepick.derbauer2.textengine.keyboard.KeyPressed
import com.github.seepick.derbauer2.textengine.keyboard.PrintChar
import com.github.seepick.derbauer2.textengine.textmap.Textmap
import com.github.seepick.derbauer2.textengine.textmap.TransformingTableCol
import io.github.oshai.kotlinlogging.KotlinLogging.logger

class SelectPrompt<LABEL : OptionLabel, OPTIONS : Options<LABEL>>(
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
            log.debug { "🔢 Selected: $option" }
            option.onSelected()
            true
        } else {
            false
        }

    override fun render(textmap: Textmap) {
        when (options) {
            is Options.Singled<out OptionLabel.Single> -> {
                options.items.mapIndexed { idx, opt ->
                    opt.label as OptionLabel.Single
                    textmap.line("[${idx + 1}] ${opt.label.value}")
                }
            }

            is Options.Tabled -> {
                textmap.customTable(
                    cols = buildList {
                        val rowsBefore = 1
                        add(TransformingTableCol { rowIdx, _, _ ->
                            "[${rowIdx + 1}]"
                        })
                        addAll((0..<(options.items.maxOf { it.label.columns.size })).map {
                            TransformingTableCol { _, colIdx, opt ->
                                val colIdxAdjusted = colIdx - rowsBefore
                                if (colIdxAdjusted > (opt.label.columns.size - 1)) {
                                    ""
                                } else {
                                    opt.label.columns[colIdxAdjusted]
                                }
                            }
                        })
                    },
                    rowItems = options.items,
                )
            }
        }
    }

    companion object {
        operator fun <S : OptionLabel.Single> invoke(items: List<SelectOption<S>>) =
            SelectPrompt(Options.Singled(items))
    }
}

sealed class Options<LABEL : OptionLabel>(val items: List<SelectOption<out LABEL>>) {

    val size = items.size

    class Singled<SIMPLE : OptionLabel.Single>(
        items: List<SelectOption<out SIMPLE>>,
    ) : Options<SIMPLE>(items)

    class Tabled(
        items: List<SelectOption<OptionLabel.Table>>,
    ) : Options<OptionLabel.Table>(items)

    companion object {
        operator fun <S : OptionLabel.Single> invoke(items: List<SelectOption<S>>) = Singled(items)
    }
}

data class SelectOption<LABEL : OptionLabel>(
    val label: LABEL,
    val onSelected: () -> Unit,
) {
    override fun toString() = "SelectOption(label=${label})"

    companion object {
        operator fun invoke(label: String, onSelected: () -> Unit): SelectOption<OptionLabel.Single.Static> =
            SelectOption(OptionLabel.Single.Static(label), onSelected)
    }
}

sealed interface OptionLabel {
    sealed interface Single : OptionLabel {
        val value
            get(): String = when (this) {
                is Static -> staticLabel
                is Dynamic -> provide()
            }

        data class Static(val staticLabel: String) : Single
        class Dynamic(val provide: () -> String) : Single {
            override fun toString() = "${this::class.simpleName}(label=${provide()})"
        }
    }

    data class Table(val columns: List<String>) : OptionLabel
}

typealias StaticLabel = OptionLabel.Single.Dynamic
typealias DynamicLabel = OptionLabel.Single.Dynamic
typealias TableLabel = OptionLabel.Table

typealias SingleSelectPrompt = SelectPrompt<OptionLabel.Single, Options<OptionLabel.Single>>
typealias StaticSelectPrompt = SelectPrompt<OptionLabel.Single.Static, Options<OptionLabel.Single.Static>>
typealias DynamicSelectPrompt = SelectPrompt<OptionLabel.Single.Dynamic, Options<OptionLabel.Single.Dynamic>>
