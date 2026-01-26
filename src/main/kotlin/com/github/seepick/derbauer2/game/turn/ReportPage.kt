package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.HomePage
import com.github.seepick.derbauer2.game.feature.FeatureMultiView
import com.github.seepick.derbauer2.game.happening.HappeningMultiView
import com.github.seepick.derbauer2.game.view.ContinueButton
import com.github.seepick.derbauer2.game.view.GameRenderer
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.KeyPressed
import com.github.seepick.derbauer2.textengine.Page
import com.github.seepick.derbauer2.textengine.Textmap

class ReportPage(
    private val turner: Turner,
    private val gameRenderer: GameRenderer,
    private val current: CurrentPage,
    private val happeningMultiView: HappeningMultiView,
    private val featureMultiView: FeatureMultiView
) : Page {

    private val continueButton = ContinueButton {
        happeningMultiView.process(turner.reports.last().happenings) {
            featureMultiView.process(turner.reports.last().newFeatures) {
                current.pageClass = HomePage::class
            }
        }
    }

    override fun renderText(textmap: Textmap) {
        gameRenderer.render(textmap, promptIndicator = continueButton.label, listOf(continueButton)) {
            textmap.line("Turn Report")
            textmap.emptyLine()
            textmap.line("Resources produced this turn:")
            textmap.emptyLine()
            val report = turner.reports.last()
            report.resourceReportLines.forEach {
                textmap.line("${it.resource.emojiWithSpaceSuffixOrEmpty}${it.resource.labelPlural}: ${it.changeAmount.toPlusString()}")
            }
        }
    }

    override fun onKeyPressed(key: KeyPressed) = continueButton.onKeyPressed(key)
}