package com.github.seepick.derbauer2.textengine.audio

import io.github.oshai.kotlinlogging.KotlinLogging.logger

/**
 * Manages the music player state and coordinates persistence.
 */
class MusicStateManager(
    private val repository: MusicStateRepository,
) {
    private val log = logger {}

    /**
     * Get the initial auto-play state from persistent storage.
     * @return true if music should auto-play, false otherwise.
     */
    fun shouldAutoPlay(): Boolean {
        return repository.loadPlayingState()
    }

    /**
     * Update the playing state and persist it.
     * @param isPlaying true if music is currently playing, false otherwise.
     */
    fun updatePlayingState(isPlaying: Boolean) {
        log.debug { "Updating music playing state to: $isPlaying" }
        repository.savePlayingState(isPlaying)
    }
}
