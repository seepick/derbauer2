package com.github.seepick.derbauer2.game.happening

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.view.ContinueButton
import com.github.seepick.derbauer2.game.view.MultiView
import com.github.seepick.derbauer2.game.view.NotificationPage
import com.github.seepick.derbauer2.textengine.CurrentPage

class HappeningPage(
    private val multiView: HappeningMultiView,
) : NotificationPage(
    title = "Happening",
    emoji = "✨",
    asciiArt = { multiView.current().asciiArt },
    contentRenderer = { multiView.current().render(it) },
    button = ContinueButton { multiView.continueNextOrFinish() }
)

class HappeningMultiView(user: User, currentPage: CurrentPage) : MultiView<Happening>(
    user = user,
    currentPage = currentPage,
    targetPageClass = HappeningPage::class,
)
