package com.github.seepick.derbauer2.game.feature

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.view.ContinueButton
import com.github.seepick.derbauer2.game.view.MultiView
import com.github.seepick.derbauer2.game.view.MultiViewSubPage
import com.github.seepick.derbauer2.game.view.NotificationPage
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.textmap.Textmap

class FeatureMultiView(user: User, currentPage: CurrentPage) : MultiView<FeatureInfo>(
    user = user,
    currentPage = currentPage,
    targetPageClass = FeatureViewPage::class,
)

class FeatureViewPage(
    private val multiView: FeatureMultiView,
) : NotificationPage(
    title = "Feature Unlocked",
    emoji = "🔓",
    contentRenderer = { multiView.current().render(it) },
    asciiArt = { multiView.current().asciiArt },
    button = ContinueButton { multiView.continueNextOrFinish() }
)

class FeatureInfo(private val feature: Feature) : MultiViewSubPage {
    override val asciiArt = feature.asciiArt

    override fun render(textmap: Textmap) {
        textmap.line(">> ${feature.label} <<")
        textmap.emptyLine()
        textmap.multiLine(feature.multilineDescription)
    }

    override fun execute(user: User) {
        user.add(feature)
    }
}
