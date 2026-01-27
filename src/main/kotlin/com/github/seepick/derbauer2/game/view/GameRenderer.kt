package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.logic.alsoIfExists
import com.github.seepick.derbauer2.game.logic.storageFor
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.resources
import com.github.seepick.derbauer2.game.resource.totalLandUse
import com.github.seepick.derbauer2.game.turn.Turner
import com.github.seepick.derbauer2.textengine.KeyPressed
import com.github.seepick.derbauer2.textengine.Textmap

class GameRenderer(
    private val user: User,
    private val turner: Turner,
) {
    private fun renderInfoBar(): String = buildList<String> {
            user.resources.alsoIfExists(Land::class) {
                add(it.emojiAndUnitsFormatted)
            }
            user.resources.alsoIfExists(Gold::class) {
                add(it.emojiAndUnitsFormatted)
            }

            user.resources.alsoIfExists(Food::class) {
                add("${it.emojiAndUnitsFormatted} / ${user.storageFor(Food::class)}")
            }
            user.resources.alsoIfExists(Land::class) {
                val totalLandUse = user.totalLandUse
                add("${it.emojiWithSpaceSuffixOrEmpty}${totalLandUse} / ${it.owned}")
            }
            user.resources.alsoIfExists(Citizen::class) {
                add("${it.emojiWithSpaceSuffixOrEmpty}${it.owned} / ${user.storageFor(Citizen::class)}")
            }
        }.joinToString(" | ")

    fun render(
        textmap: Textmap,
        promptIndicator: String,
        metaOptions: List<MetaOption> = emptyList(),
        content: (Textmap) -> Unit
    ) {
        textmap.aligned(renderInfoBar(), "Turn ${turner.turn}")
        textmap.hr()
        content(textmap)
        textmap.fillVertical(minus = 2)
        textmap.hr()
        textmap.aligned(
            left = "[$promptIndicator]> ▉",
            right = metaOptions.joinToString("   ") { it.formatted },
        )
    }

    private val MetaOption.formatted get() = "${key.label}: $label"
}

interface MetaOption {
    val key: KeyPressed.Command // ENTER
    val label: String // Buy Building
}

data class MetaOptionImpl(
    override val key: KeyPressed.Command,
    override val label: String,
) : MetaOption
