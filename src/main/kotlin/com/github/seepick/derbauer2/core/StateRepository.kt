package com.github.seepick.derbauer2.core

interface StateRepository {
    fun loadPlayingState(): Boolean
    fun savePlayingState(isPlaying: Boolean)
}
