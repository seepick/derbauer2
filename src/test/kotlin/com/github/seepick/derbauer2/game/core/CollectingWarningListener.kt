package com.github.seepick.derbauer2.game.core

class CollectingWarningListener : WarningListener {

    private val _warnings = mutableListOf<Warning>()
    val warnings: List<Warning> = _warnings

    override fun onWarning(warning: Warning) {
        _warnings += warning
    }
}
