package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.feature.FeatureInfo
import com.github.seepick.derbauer2.game.happening.Happening
import com.github.seepick.derbauer2.game.resource.ResourceChanges

data class TurnReport(
    val turn: Int,
    val resourceChanges: ResourceChanges,
    val happenings: List<Happening>,
    val newFeatures: List<FeatureInfo>,
    val isGameOver: Boolean,
)
