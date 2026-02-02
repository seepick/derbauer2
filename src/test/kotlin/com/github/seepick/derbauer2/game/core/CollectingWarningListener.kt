package com.github.seepick.derbauer2.game.core

class CollectingWarningListener : WarningListener {
    val warnings = mutableListOf<Warning>()
    override fun onWarning(warning: Warning) {
        warnings += warning
    }
}
