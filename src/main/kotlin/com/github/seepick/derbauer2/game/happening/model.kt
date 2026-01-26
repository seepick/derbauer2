package com.github.seepick.derbauer2.game.happening

import com.github.seepick.derbauer2.game.view.MultiViewItem

enum class HappeningNature {
    Positive,
    Negative,
    Mixed,
    Neutral,
}

interface HappeningData {
    val nature: HappeningNature
}

interface Happening : MultiViewItem, HappeningData
