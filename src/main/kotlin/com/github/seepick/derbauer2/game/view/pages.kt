package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.textengine.Page
import com.github.seepick.derbauer2.textengine.Textmap
import com.github.seepick.derbauer2.textengine.keyboard.KeyListener
import com.github.seepick.derbauer2.textengine.keyboard.KeyPressed
import com.github.seepick.derbauer2.textengine.prompt.PromptProvider

abstract class SimpleGamePage(
    private val gameRenderer: GameRenderer,
    private val button: Button,
    private val contentRenderer: (Textmap) -> Unit,
) : Page, KeyListener by button {

    override fun render(textmap: Textmap) {
        gameRenderer.render(textmap, promptIndicator = button.key.label, emptyList()) {
            contentRenderer(textmap)
        }
    }
}

abstract class PromptGamePage(
    private val gameRenderer: GameRenderer,
    private val promptBuilder: PromptProvider,
    private val buttons: List<Button>,
    private val contentRenderer: (Textmap) -> Unit,
) : Page {

    var prompt = promptBuilder.buildPrompt()

    override fun invalidate() {
        prompt = promptBuilder.buildPrompt()
    }

    override fun render(textmap: Textmap) {
        gameRenderer.render(textmap, prompt.inputIndicator, buttons) {
            contentRenderer(textmap)
            textmap.emptyLine()
            prompt.render(textmap)
        }
    }

    override fun onKeyPressed(key: KeyPressed): Boolean =
        buttons.plus(prompt).any { it.onKeyPressed(key) }
}

@Suppress("LongParameterList")
abstract class NotificationPage(
    private val title: String,
    private val emoji: String,
    private val asciiArt: () -> AsciiArt,
    private val contentRenderer: (Textmap) -> Unit,
    private val button: Button,
) : Page, KeyListener by button {

    override fun render(textmap: Textmap) {
        textmap.line("$emoji $title $emoji")
        textmap.emptyLine()
        textmap.multiLine(asciiArt().value)
        textmap.emptyLine()
        contentRenderer(textmap)
        textmap.fillVertical(1)
        textmap.line("[${button.key.label}] ${button.label}")
    }
}
