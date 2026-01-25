package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.game.logic.Entity
import com.github.seepick.derbauer2.game.logic.Game
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.logic.storageFor
import com.github.seepick.derbauer2.game.logic.totalLandUse
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.textengine.KeyPressed
import com.github.seepick.derbauer2.textengine.Textmap

inline fun <reified E : Entity> User.find(code: (E) -> Unit) {
    all.filterIsInstance<E>().firstOrNull()?.also(code)
}

class GameRenderer(
    private val game: Game,
) {
    private fun renderInfoBar(): String =
        buildList {
            game.user.find<Gold> {
                add(it.emojiAndUnitsFormatted)
            }
            game.user.find<Food> {
                add("${it.emojiAndUnitsFormatted} / ${game.user.storageFor(Food::class)}")
            }
            game.user.find<Land> {
                val totalLandUse = game.user.totalLandUse
                add("${it.emojiWithSpaceSuffixOrEmpty}${totalLandUse} / ${it.owned}")
            }
            game.user.find<Citizen> {
                add("${it.emojiWithSpaceSuffixOrEmpty}${it.owned} / ${game.user.storageFor(Citizen::class)}")
            }
        }.joinToString(" | ")

    fun render(
        textmap: Textmap,
        promptIndicator: String,
        metaOptions: List<MetaOption> = emptyList(),
        content: (Textmap) -> Unit
    ) {

        textmap.printAligned(renderInfoBar(), "Turn ${game.turn}")
        textmap.printHr()
        content(textmap)
        textmap.fillVertical(minus = 2)
        textmap.printHr()
        textmap.printAligned(
            left = "[$promptIndicator]> ▉",
            right = metaOptions.joinToString("   ") { "${it.command.key}: ${it.description}" },
        )
    }
}

data class MetaOption(
    val command: KeyPressed.Command, // ENTER
    val description: String, // Buy Building
)
