package com.github.seepick.derbauer2.textengine

interface TextengineStateRepository {
    fun getMusicPlaying(): Boolean?
    fun setMusicPlaying(isPlaying: Boolean)
}
