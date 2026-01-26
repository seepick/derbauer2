package com.github.seepick.derbauer2.game.happening

import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.view.MultiView
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.KeyPressed
import com.github.seepick.derbauer2.textengine.Page
import com.github.seepick.derbauer2.textengine.Textmap

class HappeningMultiView(user: User, currentPage: CurrentPage) : MultiView<Happening>(
    user = user,
    currentPage = currentPage,
    targetPageClass = HappeningPage::class,
)

class HappeningPage(
    private val controller: HappeningMultiView,
) : Page {
    private val continueKey = KeyPressed.Command.Space

    override fun renderText(textmap: Textmap) {
        textmap.line("⭐️ Happening ⭐️")
        textmap.emptyLine()
        controller.current().render(textmap) // TODO vertical center alignment
        textmap.fillVertical(1)
        textmap.line("${continueKey.label} to continue")
    }

    override fun onKeyPressed(key: KeyPressed): Boolean {
        if (key == continueKey) {
            controller.continueNextOrFinish()
            return true
        }
        return false
    }

}