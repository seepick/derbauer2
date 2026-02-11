package com.github.seepick.derbauer2.textengine.audio

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import io.github.oshai.kotlinlogging.KotlinLogging.logger

class AudioViewModel(
    private val soundService: SoundPlayService,
) : ViewModel() {

    private val log = logger {}

    // TODO load initial value from prefs
    var isSoundEnabled: Boolean by mutableStateOf(true)
        private set

    val soundButtonTooltip: String by derivedStateOf {
        if (isSoundEnabled) "Disable Sound" else "Enable Sound"
    }

    fun onToggleSoundEnabled() {
        log.debug { "onToggleSoundEnabled" }
        isSoundEnabled = !isSoundEnabled
        soundService.enable(isSoundEnabled)
    }
}
