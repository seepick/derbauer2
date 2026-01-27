package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.feature.FeatureMultiView
import com.github.seepick.derbauer2.game.happening.HappeningMultiView
import com.github.seepick.derbauer2.game.view.ContinueButton
import com.github.seepick.derbauer2.game.view.GameRenderer
import com.github.seepick.derbauer2.game.view.HomePage
import com.github.seepick.derbauer2.game.view.SimpleGamePage
import com.github.seepick.derbauer2.textengine.CurrentPage

@Suppress("LongParameterList")
class ReportPage(
    gameRenderer: GameRenderer,
    private val reports: ReportIntelligence,
    private val current: CurrentPage,
    private val happeningMultiView: HappeningMultiView,
    private val featureMultiView: FeatureMultiView
) : SimpleGamePage(
    gameRenderer = gameRenderer,
    button = ContinueButton {
        happeningMultiView.process(reports.last().happenings) {
            featureMultiView.process(reports.last().newFeatures) {
                current.pageClass = HomePage::class
            }
        }
    },
    contentRenderer = { textmap ->
        textmap.line("Turn Report")
        textmap.emptyLine()
        textmap.line("Resource production:")
        val report = reports.last()
        report.resourceReportLines.forEach {
            textmap.line("${it.resource.emojiSpaceOrEmpty}${it.resource.labelPlural} ${it.changeAmount.toPlusString()}")
        }
    },
)
