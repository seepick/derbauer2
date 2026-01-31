package com.github.seepick.derbauer2.textengine.audio

interface StateRepository {
    fun loadPlayingState(): Boolean
    fun savePlayingState(isPlaying: Boolean)
}
