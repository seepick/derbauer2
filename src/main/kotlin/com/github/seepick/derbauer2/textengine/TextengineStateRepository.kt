package com.github.seepick.derbauer2.textengine

interface TextengineStateRepository {
    fun isMusicPlaying(): Boolean
    fun setMusicPlaying(musicPlaying: Boolean)
    fun isSoundEnabled(): Boolean
    fun setSoundEnabled(soundEnabled: Boolean)
    fun getWindowPosition(): Pair<Int, Int>?
    fun setWindowPosition(position: Pair<Int, Int>)
}
