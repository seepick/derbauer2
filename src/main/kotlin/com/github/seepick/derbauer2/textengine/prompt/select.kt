package com.github.seepick.derbauer2.textengine.prompt

import com.github.seepick.derbauer2.textengine.keyboard.KeyListener
import com.github.seepick.derbauer2.textengine.textmap.Textmap
import com.github.seepick.derbauer2.textengine.textmap.TransformingTableCol

class SelectPrompt<LABEL : OptionLabel, OPTIONS : Options<LABEL>>(
    val options: OPTIONS,
) : Prompt, KeyListener by options {

    init {
        @Suppress("MagicNumber")
        require(options.size in 1..9) {
            "Select prompt must have between 1 and 9 options, but has ${options.size}: $options"
        }
    }

    override val inputIndicator = "1-${options.size}"

    override fun render(textmap: Textmap) {
        when (options) {
            is OptionsSingle -> textmap.linesForSingle(options)
            is OptionsTabled -> textmap.linesForTabled(options)
        }
    }

    companion object {
        operator fun <S : SingleLabel> invoke(items: List<SelectOption<S>>) =
            SelectPrompt(Options.Singled(items))
    }
}

private fun Textmap.linesForSingle(options: OptionsSingle) {
    options.items.mapIndexed { idx, opt ->
        line("[${idx + 1}] ${opt.label.value}")
    }
}

private fun Textmap.linesForTabled(options: OptionsTabled) {
    customTable(
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
