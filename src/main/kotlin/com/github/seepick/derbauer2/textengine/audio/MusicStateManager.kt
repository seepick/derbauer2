package com.github.seepick.derbauer2.textengine.audio

import com.github.seepick.derbauer2.textengine.TextengineStateRepository

class MusicStateManager(
    private val repository: TextengineStateRepository,
) {
    fun shouldAutoPlay() =
        repository.isMusicPlaying()

    fun updatePlayingState(isPlaying: Boolean) {
        repository.setMusicPlaying(isPlaying)
    }
}
