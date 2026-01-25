package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.HomePage
import com.github.seepick.derbauer2.game.feature.FeatureMultiView
import com.github.seepick.derbauer2.game.happening.HappeningMultiView
import com.github.seepick.derbauer2.game.view.GameRenderer
import com.github.seepick.derbauer2.game.view.MetaOption
import com.github.seepick.derbauer2.ifDo
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

    private val continueKey = KeyPressed.Command.Space
    override fun renderText(textmap: Textmap) {
        gameRenderer.render(textmap, promptIndicator = continueKey.label, listOf(MetaOption(continueKey, "Continue"))) {
            textmap.printLine("Turn Report")
            textmap.printEmptyLine()
            textmap.printLine("Resources produced this turn:")
            textmap.printEmptyLine()
            val report = turner.reports.last()
            report.resourceChanges.forEach {
                textmap.printLine("${it.resource.emojiWithSpaceSuffixOrEmpty}${it.resource.labelPlural}: ${it.change.toPlusString()}")
            }
        }
    }

    override fun onKeyPressed(key: KeyPressed) =
        ifDo(key == continueKey) {
            happeningMultiView.process(turner.reports.last().happenings) {
                featureMultiView.process(turner.reports.last().newFeatures) {
                    current.page = HomePage::class
                }
            }
        }
}