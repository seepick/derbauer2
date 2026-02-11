package com.github.seepick.derbauer2.game.core

import com.github.seepick.derbauer2.textengine.TextengineStateRepository
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import java.util.prefs.Preferences
import kotlin.reflect.KClass

//interface GameStateRepository

class PreferencesStateRepository(
    prefStatePath: KClass<*>,
) : TextengineStateRepository {

    private val log = logger {}
    private val prefs = loadPreferences(prefStatePath)

    init {
        log.debug { "Initializing java prefs at: ${prefs.absolutePath()}" }
    }

    override fun isMusicPlaying() = prefs.getBoolean(KEY_IS_PLAYING, DEFAULT_MUSIC_PLAYING)
    override fun setMusicPlaying(musicPlaying: Boolean) {
        putBoolean(KEY_IS_PLAYING, musicPlaying)
    }

    override fun isSoundEnabled() = prefs.getBoolean(KEY_SOUND_ENABLED, DEFAULT_SOUND_ENABLED)
    override fun setSoundEnabled(soundEnabled: Boolean) {
        putBoolean(KEY_SOUND_ENABLED, soundEnabled)
    }

    override fun getWindowPosition(): WinPos? =
        if (prefs.keys().toSet().contains(KEY_WIN_POS)) {
            prefs.get(KEY_WIN_POS, "").split("/").let { it[0].toInt() to it[1].toInt() }
        } else {
            null
        }

    override fun setWindowPosition(position: WinPos) {
        log.debug { "Saving window position: $position" }
        prefs.put(KEY_WIN_POS, "${position.first}/${position.second}")
        prefs.flush()
    }

    private fun putBoolean(key: String, value: Boolean) {
        log.debug { "Set $key: $value" }
        prefs.putBoolean(key, value)
        prefs.flush()
    }

//    private fun getBooleanOrNull(key: String): Boolean? =
//        if (prefs.keys().toSet().contains(key)) {
//            prefs.getBoolean(key, IGNORE_DEFAULT_BOOLEAN)
//        } else {
//            null
//        }

    companion object {
        private const val KEY_WIN_POS = "window.position"
        private const val KEY_IS_PLAYING = "music.isPlaying"
        private const val KEY_SOUND_ENABLED = "sound.isEnabled"
        private const val DEFAULT_SOUND_ENABLED = true
        private const val DEFAULT_MUSIC_PLAYING = false

        fun loadPreferences(prefStatePath: KClass<*>): Preferences =
            Preferences.userRoot().node(
                prefStatePath.java.packageName.replace(".", "/") + "/" + prefStatePath.simpleName
            )
    }
}

typealias WinPos = Pair<Int, Int>
