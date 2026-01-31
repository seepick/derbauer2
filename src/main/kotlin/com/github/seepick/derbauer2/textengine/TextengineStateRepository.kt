package com.github.seepick.derbauer2.textengine

interface TextengineStateRepository {
    fun isMusicPlaying(): Boolean
    fun setMusicPlaying(isPlaying: Boolean)
}
