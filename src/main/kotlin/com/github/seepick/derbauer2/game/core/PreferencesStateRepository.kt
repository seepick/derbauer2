package com.github.seepick.derbauer2.game.core

import com.github.seepick.derbauer2.textengine.TextengineStateRepository
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import java.util.prefs.Preferences
import kotlin.reflect.KClass

class PreferencesStateRepository(
    storagePackage: KClass<*>,
) : TextengineStateRepository {

    private val log = logger {}
    private val prefs = Preferences.userNodeForPackage(storagePackage.java)

    override fun getMusicPlaying(): Boolean {
        val state = prefs.getBoolean(KEY_IS_PLAYING, DEFAULT_IS_PLAYING)
        log.debug { "Loaded music playing state: $state" }
        return state
    }

    override fun setMusicPlaying(isPlaying: Boolean) {
        log.debug { "Saving music playing state: $isPlaying" }
        prefs.putBoolean(KEY_IS_PLAYING, isPlaying)
        prefs.flush()
    }

    companion object {
        private const val KEY_IS_PLAYING = "music.isPlaying"
        private const val DEFAULT_IS_PLAYING = true
    }
}
