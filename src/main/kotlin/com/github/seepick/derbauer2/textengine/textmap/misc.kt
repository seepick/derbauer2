package com.github.seepick.derbauer2.textengine.textmap

interface TextmapWriter {
    fun line(line: String): TextmapWriter
    fun emptyLine() = line("")
    fun multiLine(text: String): TextmapWriter = apply {
        text.split("\n").forEach(::line)
    }

    fun aligned(left: String, right: String): TextmapWriter
    fun fillVertical(minus: Int): TextmapWriter
    fun hr(): TextmapWriter
    fun toFullString(): String
    fun toGrid(): Array<Array<String>>

    fun table(cols: List<TableCol> = emptyList(), rows: List<List<String>>): TextmapWriter
    fun <T> tableByTransform(cols: List<TransformingTableCol<T>>, rowItems: List<T>): TextmapWriter
}

data class MatrixSize(val cols: Int, val rows: Int)

class InvalidTextmapException(message: String) : IllegalArgumentException(message)
