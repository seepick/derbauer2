package com.github.seepick.derbauer2.textengine.audio

import com.github.seepick.derbauer2.core.StateRepository

class MusicStateManager(
    private val repository: StateRepository,
) {
    fun shouldAutoPlay() =
        repository.loadPlayingState()

    fun updatePlayingState(isPlaying: Boolean) {
        repository.savePlayingState(isPlaying)
    }
}
