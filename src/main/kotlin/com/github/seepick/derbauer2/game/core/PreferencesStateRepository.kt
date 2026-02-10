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

    override fun getMusicPlaying(): Boolean? =
        if (prefs.keys().toSet().contains(KEY_IS_PLAYING)) {
            prefs.getBoolean(KEY_IS_PLAYING, IGNORE_DEFAULT_BOOLEAN)
        } else {
            null
        }

    override fun setMusicPlaying(isPlaying: Boolean) {
        log.debug { "Saving music playing state: $isPlaying" }
        prefs.putBoolean(KEY_IS_PLAYING, isPlaying)
        prefs.flush()
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

    companion object {
        private const val KEY_WIN_POS = "window.position"
        private const val KEY_IS_PLAYING = "music.isPlaying"
        private const val IGNORE_DEFAULT_BOOLEAN = false

        fun loadPreferences(prefStatePath: KClass<*>): Preferences =
            Preferences.userRoot().node(
                prefStatePath.java.packageName.replace(".", "/") + "/" + prefStatePath.simpleName
            )
    }
}

typealias WinPos = Pair<Int, Int>
