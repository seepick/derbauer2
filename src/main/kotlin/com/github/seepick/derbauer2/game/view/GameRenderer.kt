package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.resources
import com.github.seepick.derbauer2.game.resource.totalLandUse
import com.github.seepick.derbauer2.game.resource.totalStorageFor
import com.github.seepick.derbauer2.textengine.Textmap
import com.github.seepick.derbauer2.textengine.keyboard.KeyPressed

class GameRenderer(
    private val user: User,
) {
    private val MetaOption.formatted get() = "${key.label}: $label"

    private fun renderInfoBar(): String = buildList {
        user.resources.alsoIfExists(Land::class) {
            add(it.emojiAndOwned) // TODO Resource.renderInfoBarItem()
        }
        user.resources.alsoIfExists(Gold::class) {
            add(it.emojiAndOwned)
        }

        user.resources.alsoIfExists(Food::class) {
            add("${it.emojiAndOwned} / ${user.totalStorageFor(Food::class)}")
        }
        user.resources.alsoIfExists(Land::class) {
            val totalLandUse = user.totalLandUse
            add("${it.emojiSpaceOrEmpty}${totalLandUse} / ${it.owned}")
        }
        user.resources.alsoIfExists(Citizen::class) {
            add("${it.emojiSpaceOrEmpty}${it.owned} / ${user.totalStorageFor(Citizen::class)}")
        }
    }.joinToString(" | ")

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
}

interface MetaOption {
    val key: KeyPressed.Command // ENTER
    val label: String // Buy Building
}

data class MetaOptionImpl(
    override val key: KeyPressed.Command,
    override val label: String,
) : MetaOption
