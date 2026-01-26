package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.game.logic.Entity
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.logic.storageFor
import com.github.seepick.derbauer2.game.logic.totalLandUse
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.turn.Turner
import com.github.seepick.derbauer2.textengine.KeyPressed
import com.github.seepick.derbauer2.textengine.Textmap

inline fun <reified E : Entity> User.find(code: (E) -> Unit) {
    all.filterIsInstance<E>().firstOrNull()?.also(code)
}

class GameRenderer(
    private val user: User,
    private val turner: Turner,
) {
    private fun renderInfoBar(): String =
        buildList {
            user.find<Gold> {
                add(it.emojiAndUnitsFormatted)
            }
            user.find<Food> {
                add("${it.emojiAndUnitsFormatted} / ${user.storageFor(Food::class)}")
            }
            user.find<Land> {
                val totalLandUse = user.totalLandUse
                add("${it.emojiWithSpaceSuffixOrEmpty}${totalLandUse} / ${it.owned}")
            }
            user.find<Citizen> {
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
            right = metaOptions.joinToString("   ") { "${it.command.label}: ${it.description}" },
        )
    }
}

data class MetaOption(
    val command: KeyPressed.Command, // ENTER
    val description: String, // Buy Building
)
