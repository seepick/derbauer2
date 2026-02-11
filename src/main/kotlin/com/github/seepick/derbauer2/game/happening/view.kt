package com.github.seepick.derbauer2.game.happening

import com.github.seepick.derbauer2.game.common.toFormatted
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.happinessImpact
import com.github.seepick.derbauer2.game.stat.Happiness
import com.github.seepick.derbauer2.game.stat.findStatOrNull
import com.github.seepick.derbauer2.game.view.ContinueButton
import com.github.seepick.derbauer2.game.view.MultiView
import com.github.seepick.derbauer2.game.view.NotificationPage
import com.github.seepick.derbauer2.textengine.CurrentPage
import io.github.oshai.kotlinlogging.KotlinLogging.logger

class HappeningPage(
    val multiView: HappeningMultiView,
) : NotificationPage(
    title = "Happening",
    emoji = { multiView.currentUnseen.nature.emoji },
    asciiArt = { multiView.currentUnseen.asciiArt },
    contentRenderer = { multiView.currentUnseen.render(it) },
    button = ContinueButton { multiView.continueNextOrFinish() })

class HappeningMultiView(user: User, currentPage: CurrentPage) : MultiView<Happening>(
    user = user,
    currentPage = currentPage,
    targetPageClass = HappeningPage::class,
) {
    private val log = logger {}

    override fun alsoExecute(page: Happening) {
        log.debug { "Adjusting happiness by happening-nature: ${page.nature.happinessImpact.toFormatted()}" }
        user.findStatOrNull(Happiness::class)?.changeBy(page.nature.happinessImpact)
    }
}
