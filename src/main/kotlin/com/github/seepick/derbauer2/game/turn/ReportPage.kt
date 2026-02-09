package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.feature.FeatureMultiView
import com.github.seepick.derbauer2.game.happening.HappeningMultiView
import com.github.seepick.derbauer2.game.resource.findResource
import com.github.seepick.derbauer2.game.view.ContinueButton
import com.github.seepick.derbauer2.game.view.GameRenderer
import com.github.seepick.derbauer2.game.view.HomePage
import com.github.seepick.derbauer2.game.view.SimpleGamePage
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.textmap.TableAlign
import com.github.seepick.derbauer2.textengine.textmap.TransformingTableCol
import com.github.seepick.derbauer2.textengine.textmap.emptyLine

@Suppress("LongParameterList")
class ReportPage(
    gameRenderer: GameRenderer,
    private val user: User,
    private val current: CurrentPage,
    private val happeningMultiView: HappeningMultiView,
    private val featureMultiView: FeatureMultiView,
    private val reports: Reports,
) : SimpleGamePage(
    gameRenderer = gameRenderer,
    button = ContinueButton {
        happeningMultiView.process(reports.last().happenings) {
            featureMultiView.process(reports.last().featurePages) {
                current.pageClass = HomePage::class
            }
        }
    },
    contentRenderer = { textmap ->
        textmap.line("---------------------------")
        textmap.line("🗞️  T U R N   R E P O R T 🗞")
        textmap.line("---------------------------")
        textmap.emptyLine()
        textmap.customTable(
            cols = listOf(
                TransformingTableCol(align = TableAlign.Left) { _, _, change ->
                    val res = user.findResource(change.resourceClass)
                    "${res.emojiSpaceOrEmpty}${res.labelPlural}"
                },
                TransformingTableCol(align = TableAlign.Right) { _, _, change ->
                    (user.findResource(change.resourceClass).owned.zz - change.change).toString()
                },
                TransformingTableCol(align = TableAlign.Right) { _, _, change ->
                    change.change.toSymboledString() + " ="
                },
                TransformingTableCol(align = TableAlign.Right) { _, _, change ->
                    user.findResource(change.resourceClass).owned.toString()
                },
            ),
            rowItems = reports.last().resourceChanges.changes,
        )
    },
)
