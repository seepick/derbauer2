package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.feature.FeatureInfo
import com.github.seepick.derbauer2.game.happening.Happening
import com.github.seepick.derbauer2.game.resource.ResourceReportLine

data class TurnReport(
    val turn: Int,
    val resourceReportLines: List<ResourceReportLine>,
    val happenings: List<Happening>,
    val newFeatures: List<FeatureInfo>,
)