package com.github.seepick.derbauer2.textengine.audio

/**
 * Repository for persisting the music player state.
 */
interface MusicStateRepository {
    /**
     * Load the last known playing state.
     * @return true if music was playing when the app was last closed, false otherwise.
     */
    fun loadPlayingState(): Boolean

    /**
     * Save the current playing state.
     * @param isPlaying true if music is currently playing, false otherwise.
     */
    fun savePlayingState(isPlaying: Boolean)
}
