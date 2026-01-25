package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.game.logic.Food
import com.github.seepick.derbauer2.game.logic.Game
import com.github.seepick.derbauer2.game.logic.Gold
import com.github.seepick.derbauer2.game.logic.StorableResource
import com.github.seepick.derbauer2.game.logic.storageCapacityFor
import com.github.seepick.derbauer2.textengine.KeyPressed
import com.github.seepick.derbauer2.textengine.Textmap

class GameRenderer(
    private val game: Game,
) {
    fun render(
        textmap: Textmap,
        promptIndicator: String,
        metaOptions: List<MetaOption> = emptyList(),
        content: (Textmap) -> Unit
    ) {
        val resourcesInfo = game.user.resources.filter {
            it is Gold || it is Food
        }.joinToString(" | ") {
            it.emojiAndUnitsFormatted + if(it is StorableResource) {
                " / ${game.user.storageCapacityFor(it::class).formatted}"
            } else ""
        }
        textmap.printAligned(resourcesInfo, "Turn ${game.turn}")
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
