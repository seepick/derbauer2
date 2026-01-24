package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.engine.Textmap
import com.github.seepick.derbauer2.game.logic.User

class GameRenderer(
    private val user: User,
) {
    fun render(
        textmap: Textmap,
        footer: String,
        footerOptions: List<FooterOption> = emptyList(),
        content: (Textmap) -> Unit
    ) {
        textmap.printAligned("💰 ${user.money.formatted}", "Turn ?")
        textmap.printHr()
        content(textmap)
        textmap.fillVertical(minus = 2)
        textmap.printHr()
        textmap.printAligned(
            left = "[$footer]> ▉",
            right = footerOptions.joinToString("   ") { "${it.key}: ${it.description}" },
        )
    }

    fun render(
        textmap: Textmap,
        footerOptions: List<FooterOption>,
        content: (Textmap) -> Unit
    ) {
        render(textmap, footerOptions.first().key, footerOptions, content)
    }
}

data class FooterOption(
    val key: String, // ENTER
    val description: String, // Buy Building
)
