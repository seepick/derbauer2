package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.game.logic.User

class WindowedRenderer(
    private val user: User,
) {
    fun render(
        footer: String,
        footerOptions: List<FooterOption> = emptyList(),
        content: () -> String
    ): String {
        return "💰 ${user.money.formatted}\n" +
                "===================================\n" +
                content() + "\n" +
                "===================================\n" +
                // calc h-space
                "[$footer]> ▉       ${footerOptions.joinToString("   ") { "${it.key}: ${it.description}" }}"
    }

    fun render(
        footerOptions: List<FooterOption>,
        content: () -> String
    ) = render(footerOptions.first().key, footerOptions, content)
}

data class FooterOption(
    val key: String, // ENTER
    val description: String, // Buy Building
)
