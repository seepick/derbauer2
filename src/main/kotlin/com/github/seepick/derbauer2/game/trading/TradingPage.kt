package com.github.seepick.derbauer2.game.trading

import com.github.seepick.derbauer2.game.logic.Game
import com.github.seepick.derbauer2.game.view.Back
import com.github.seepick.derbauer2.game.view.GameRenderer
import com.github.seepick.derbauer2.game.view.HomePage
import com.github.seepick.derbauer2.viewer.CurrentPage
import com.github.seepick.derbauer2.viewer.KeyPressed
import com.github.seepick.derbauer2.viewer.Page
import com.github.seepick.derbauer2.viewer.Textmap

class TradingPage(
    private val game: Game,
    private val currentPage: CurrentPage,
    private val gameRenderer: GameRenderer,
) : Page {

    private val back = Back {
        currentPage.page = HomePage::class
    }

    override fun renderText(textmap: Textmap) {
        gameRenderer.render(textmap, "x", listOf(back.option)) {
            textmap.printLine("trading ...")
        }
    }

    override fun onKeyPressed(key: KeyPressed): Boolean {
        return back.onKeyPressed(key)
    }
}
