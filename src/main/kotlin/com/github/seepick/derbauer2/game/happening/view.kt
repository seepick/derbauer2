package com.github.seepick.derbauer2.game.happening

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.view.ContinueButton
import com.github.seepick.derbauer2.game.view.MultiView
import com.github.seepick.derbauer2.game.view.NotificationPage
import com.github.seepick.derbauer2.textengine.CurrentPage

class HappeningMultiView(user: User, currentPage: CurrentPage) : MultiView<Happening>(
    user = user,
    currentPage = currentPage,
    targetPageClass = HappeningPage::class,
)

class HappeningPage(
    private val controller: HappeningMultiView,
) : NotificationPage(
    title = "Happening",
    emoji = "✨",
    asciiArt = { controller.current().asciiArt },
    contentRenderer = { controller.current().render(it) },
    button = ContinueButton { controller.continueNextOrFinish() }
)
