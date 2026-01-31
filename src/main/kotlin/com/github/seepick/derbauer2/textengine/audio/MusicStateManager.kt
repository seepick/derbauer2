package com.github.seepick.derbauer2.textengine.audio

import io.github.oshai.kotlinlogging.KotlinLogging.logger

class MusicStateManager(
    private val repository: StateRepository,
) {
    private val log = logger {}

    fun shouldAutoPlay(): Boolean {
        return repository.loadPlayingState()
    }

    fun updatePlayingState(isPlaying: Boolean) {
        log.debug { "Updating music playing state to: $isPlaying" }
        repository.savePlayingState(isPlaying)
    }
}
