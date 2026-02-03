package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.feature.FeatureMultiView
import com.github.seepick.derbauer2.game.happening.HappeningMultiView
import com.github.seepick.derbauer2.game.resource.ResourceChange
import com.github.seepick.derbauer2.game.resource.findResource
import com.github.seepick.derbauer2.game.view.ContinueButton
import com.github.seepick.derbauer2.game.view.GameRenderer
import com.github.seepick.derbauer2.game.view.HomePage
import com.github.seepick.derbauer2.game.view.SimpleGamePage
import com.github.seepick.derbauer2.textengine.CurrentPage

@Suppress("LongParameterList")
class ReportPage(
    gameRenderer: GameRenderer,
    private val user: User,
    private val current: CurrentPage,
    private val happeningMultiView: HappeningMultiView,
    private val featureMultiView: FeatureMultiView
) : SimpleGamePage(
    gameRenderer = gameRenderer,
    button = ContinueButton {
        happeningMultiView.process(user.reports.last().happenings) {
            featureMultiView.process(user.reports.last().newFeatures) {
                current.pageClass = HomePage::class
            }
        }
    },
    contentRenderer = { textmap ->
        textmap.line("Turn Report")
        textmap.emptyLine()
        textmap.line("Resource production:")
        val report = user.reports.last()
        report.resourceChanges.changes.forEach { change ->
            with(user) {
                textmap.line(change.toLine())
            }
        }
    },
)

context(user: User)
fun ResourceChange.toLine(): String {
    val res = user.findResource(this.resourceClass)
    return "${res.emojiSpaceOrEmpty}${res.labelPlural} ${this.changeAmount.toSymboledString()}"
}
