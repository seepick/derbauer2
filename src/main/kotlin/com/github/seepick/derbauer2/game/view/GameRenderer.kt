package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.findResourceOrNull
import com.github.seepick.derbauer2.game.resource.toInfoBarString
import com.github.seepick.derbauer2.textengine.keyboard.KeyPressed
import com.github.seepick.derbauer2.textengine.textmap.Textmap

class GameRenderer(
    private val user: User,
) {
    private val MetaOption.formatted get() = "${key.label}: $label"
    // IDEA in the future configurable via UI
    private val infoResources = listOf(Land::class, Gold::class, Food::class, Land::class, Citizen::class)

    private fun renderInfoBar(): String =
        infoResources
            .mapNotNull { user.findResourceOrNull(it) }
            .joinToString(" $RESOURCE_INFO_SEPARATOR ") { it.toInfoBarString(user) }

    fun render(
        textmap: Textmap,
        promptIndicator: String,
        metaOptions: List<MetaOption> = emptyList(),
        content: (Textmap) -> Unit
    ) {
        textmap.aligned(renderInfoBar(), user.turn.prettyString)
        textmap.hr()
        content(textmap)
        textmap.fillVertical(minus = 2)
        textmap.hr()
        textmap.aligned(
            left = "[$promptIndicator]> ▉",
            right = metaOptions.joinToString("   ") { it.formatted },
        )
    }

    companion object {
        const val RESOURCE_INFO_SEPARATOR = "|"
        const val RESOURCE_INFO_OWNED_SEPARATOR = "/"
    }
}

interface MetaOption {
    val key: KeyPressed.Command // ENTER
    val label: String // Buy Building
}

data class MetaOptionImpl(
    override val key: KeyPressed.Command,
    override val label: String,
) : MetaOption
