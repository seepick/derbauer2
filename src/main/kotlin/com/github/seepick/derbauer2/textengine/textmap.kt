package com.github.seepick.derbauer2.textengine

import com.github.seepick.derbauer2.game.common.extractGraphemes

interface TextmapWriter {
    fun line(line: String): TextmapWriter
    fun emptyLine(): TextmapWriter
    fun multiLine(text: String): TextmapWriter
    fun aligned(left: String, right: String): TextmapWriter
    fun fillVertical(minus: Int): TextmapWriter
    fun hr(): TextmapWriter
}

data class MatrixSize(val cols: Int, val rows: Int)

class Textmap(
    private val cols: Int,
    private val rows: Int,
) : TextmapWriter {
    private val buffer = Array(rows) { Array(cols) { " " } }
    private val cursor = Cursor()

    constructor(matrixSize: MatrixSize) : this(cols = matrixSize.cols, rows = matrixSize.rows)

    override fun line(line: String) = apply {
        if (line.contains('\n')) {
            throw InvalidTextmapException(
                "Newline character not allowed in line(): '$line' => use multiLine() instead."
            )
        }
        val graphemes = line.extractGraphemes()
        graphemes.forEach { grapheme ->
            set(cursor.x, cursor.y, grapheme)
            cursor.x++
        }
        cursor.nextLine()
    }

    override fun multiLine(text: String) = apply {
        text.split("\n").forEach(::line)
    }

    override fun emptyLine() = line("")

    override fun aligned(left: String, right: String) = apply {
        val leftCells = countCells(left)
        val rightCells = countCells(right)
        if (leftCells + rightCells > cols) {
            throw InvalidTextmapException("Cannot align left='$left' and right='$right' in width $cols")
        }
        val gap = " ".repeat(cols - leftCells - rightCells)
        line("$left$gap$right")
    }

    override fun hr() = apply {
        line(HR_SYMBOL.repeat(cols))
    }

    override fun fillVertical(minus: Int) = apply {
        repeat(rows - (cursor.y + minus)) { cursor.nextLine() }
    }

    fun reset() = apply {
        for (x in buffer.indices) {
            for (y in buffer[x].indices) {
                buffer[x][y] = " "
            }
        }
        cursor.reset()
    }

    fun toFullString(): String =
        buffer.joinToString("\n") { it.joinToString("") }

    fun toGrid(): Array<Array<String>> =
        buffer.map { it.copyOf() }.toTypedArray()

    private fun set(x: Int, y: Int, cell: String) {
        if (x >= cols || y >= rows) {
            throw InvalidTextmapException("Textmap overflow at ($x,$y) for size ${cols}x$rows; cell=[$cell]")
        }
        buffer[y][x] = cell
    }

    private fun countCells(text: String): Int = text.extractGraphemes().size

    companion object {
        private const val HR_SYMBOL = "="
    }
}

class InvalidTextmapException(message: String) : IllegalArgumentException(message)

@Suppress("VariableMinLength")
private class Cursor {
    var x = 0
    var y = 0
    fun nextLine() {
        x = 0; y++
    }

    fun reset() {
        x = 0; y = 0
    }
}
