package com.github.seepick.derbauer2.textengine.audio

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import java.util.prefs.Preferences

/**
 * Implementation of MusicStateRepository using Java Preferences API.
 */
class PreferencesMusicStateRepository : MusicStateRepository {
    private val log = logger {}
    private val prefs = Preferences.userNodeForPackage(PreferencesMusicStateRepository::class.java)

    override fun loadPlayingState(): Boolean {
        val state = prefs.getBoolean(KEY_IS_PLAYING, DEFAULT_IS_PLAYING)
        log.debug { "Loaded music playing state: $state" }
        return state
    }

    override fun savePlayingState(isPlaying: Boolean) {
        log.debug { "Saving music playing state: $isPlaying" }
        prefs.putBoolean(KEY_IS_PLAYING, isPlaying)
        prefs.flush()
    }

    companion object {
        private const val KEY_IS_PLAYING = "music.isPlaying"
        private const val DEFAULT_IS_PLAYING = true
    }
}
