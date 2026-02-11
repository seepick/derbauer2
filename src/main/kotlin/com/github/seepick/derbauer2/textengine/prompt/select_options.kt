package com.github.seepick.derbauer2.textengine.prompt

import com.github.seepick.derbauer2.textengine.keyboard.KeyListener
import com.github.seepick.derbauer2.textengine.keyboard.KeyPressed
import com.github.seepick.derbauer2.textengine.keyboard.getIntOrNull
import io.github.oshai.kotlinlogging.KotlinLogging.logger

sealed class Options<LABEL : OptionLabel>(val items: List<SelectOption<out LABEL>>) : KeyListener {

    private val log = logger {}
    val size = items.size

    override fun onKeyPressed(key: KeyPressed): Boolean {
        val pressedInt = key.getIntOrNull() ?: return false
        if (pressedInt !in 1..size) return false
        val option = items[pressedInt - 1]
        log.debug { "🔢 Selected: $option" }
        option.onSelected()
        return true
    }

    class Singled<SIMPLE : SingleLabel>(
        items: List<SelectOption<out SIMPLE>>,
    ) : Options<SIMPLE>(items)

    class Tabled(
        items: List<SelectOption<TableLabel>>,
    ) : Options<TableLabel>(items)

    companion object {
        operator fun <S : SingleLabel> invoke(items: List<SelectOption<S>>) = Singled(items)
    }
}

data class SelectOption<LABEL : OptionLabel>(
    val label: LABEL,
    val onSelected: () -> Unit,
) {
    override fun toString() = "SelectOption(label=${label})"

    companion object {
        operator fun invoke(label: String, onSelected: () -> Unit): SelectOption<StaticLabel> =
            SelectOption(StaticLabel(label), onSelected)
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

typealias SingleLabel = OptionLabel.Single
typealias StaticLabel = OptionLabel.Single.Static
typealias DynamicLabel = OptionLabel.Single.Dynamic
typealias TableLabel = OptionLabel.Table

typealias OptionsSingle = Options.Singled<out OptionLabel.Single>
typealias OptionsTabled = Options.Tabled

typealias SingleSelectPrompt = SelectPrompt<OptionLabel.Single, Options<OptionLabel.Single>>
typealias StaticSelectPrompt = SelectPrompt<OptionLabel.Single.Static, Options<OptionLabel.Single.Static>>
typealias DynamicSelectPrompt = SelectPrompt<OptionLabel.Single.Dynamic, Options<OptionLabel.Single.Dynamic>>

