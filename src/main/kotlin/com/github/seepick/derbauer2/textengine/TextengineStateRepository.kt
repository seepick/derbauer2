package com.github.seepick.derbauer2.textengine

interface TextengineStateRepository {
    fun getMusicPlaying(): Boolean?
    fun setMusicPlaying(isPlaying: Boolean)
    fun getWindowPosition(): Pair<Int, Int>?
    fun setWindowPosition(position: Pair<Int, Int>)
}
