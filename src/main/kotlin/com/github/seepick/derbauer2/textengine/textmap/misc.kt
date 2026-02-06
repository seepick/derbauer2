package com.github.seepick.derbauer2.textengine.textmap

interface TextmapWriter {
    fun line(line: String): TextmapWriter
    fun aligned(left: String, right: String): TextmapWriter
    fun fillVertical(minus: Int): TextmapWriter
    fun hr(): TextmapWriter
    fun simpleTable(cols: List<TableCol> = emptyList(), rows: List<List<String>>): TextmapWriter
    fun <T> customTable(cols: List<TransformingTableCol<T>>, rowItems: List<T>): TextmapWriter
    fun toGrid(): Array<Array<String>>
    fun toFullString(): String
}

fun TextmapWriter.emptyLine() = line("")

fun TextmapWriter.multiLine(text: String): TextmapWriter = apply {
    text.split("\n").forEach(::line)
}

data class MatrixSize(val cols: Int, val rows: Int)

class InvalidTextmapException(message: String) : IllegalArgumentException(message)
