package com.github.seepick.derbauer2.game.logic

import com.github.seepick.derbauer2.game.feature.FeatureInfo
import com.github.seepick.derbauer2.game.happening.Happening
import com.github.seepick.derbauer2.game.resource.ResourceChange

data class TurnReport(
    val turn: Int,
    val resourceChanges: List<ResourceChange>,
    val happenings: List<Happening>,
    val newFeatures: List<FeatureInfo>,
)
