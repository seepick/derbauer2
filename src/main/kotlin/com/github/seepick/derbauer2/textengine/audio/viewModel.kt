package com.github.seepick.derbauer2.textengine.audio

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.github.seepick.derbauer2.textengine.TextengineStateRepository
import io.github.oshai.kotlinlogging.KotlinLogging.logger

class AudioViewModel(
    private val soundService: SoundPlayService,
    stateRepo: TextengineStateRepository,
) : ViewModel() {

    private val log = logger {}

    var isSoundEnabled: Boolean by mutableStateOf(stateRepo.isSoundEnabled())
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
