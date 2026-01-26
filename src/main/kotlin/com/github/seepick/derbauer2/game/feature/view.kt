package com.github.seepick.derbauer2.game.feature

import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.view.MultiView
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.KeyPressed
import com.github.seepick.derbauer2.textengine.Page
import com.github.seepick.derbauer2.textengine.Textmap

class FeatureMultiView(user: User, currentPage: CurrentPage) : MultiView<FeatureInfo>(
    user = user,
    currentPage = currentPage,
    targetPage = FeatureViewPage::class,
)

class FeatureViewPage(
    private val multiView: FeatureMultiView,
) : Page {
    private val continueKey = KeyPressed.Command.Space

    override fun renderText(textmap: Textmap) {
        multiView.current().render(textmap)
        textmap.fillVertical(1)
        textmap.line("${continueKey.label} to continue")
    }

    override fun onKeyPressed(key: KeyPressed): Boolean {
        if (key == continueKey) {
            multiView.continueNextOrFinish()
            return true
        }
        return false
    }

}