package com.github.seepick.derbauer2.textengine

interface TextmapWriter {
    fun line(line: String): TextmapWriter
    fun hr(): TextmapWriter
    fun aligned(left: String, right: String): TextmapWriter
    fun emptyLine(): TextmapWriter
    fun fillVertical(minus: Int): TextmapWriter
    fun multiLine(text: String): TextmapWriter
}

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

data class MatrixSize(val cols: Int, val rows: Int)

class InvalidTextmapException(message: String) : IllegalArgumentException(message)

class Textmap(
    private val cols: Int,
    private val rows: Int,
) : TextmapWriter {
    private val buffer = Array(rows) { Array(cols) { " " } }
    private val cursor = Cursor()

    constructor(matrixSize: MatrixSize) : this(cols = matrixSize.cols, rows = matrixSize.rows)

    override fun line(line: String) = apply {
        var i = 0
        while (i < line.length) {
            val codePoint = line.codePointAt(i)
            if (codePoint == '\n'.code) {
                throw InvalidTextmapException(
                    "Newline character not allowed in line(): '$line' => use multiLine() instead."
                )
            }
            val charCount = Character.charCount(codePoint)
            val cell = line.substring(i, i + charCount)
            set(cursor.x, cursor.y, cell)
            cursor.x++
            i += charCount
        }
        cursor.nextLine()
    }

    override fun aligned(left: String, right: String) = apply {
        val leftCells = countCells(left)
        val rightCells = countCells(right)
        if (leftCells + rightCells > cols) {
            throw InvalidTextmapException("Cannot align left='$left' and right='$right' in width $cols")
        }
        val gap = " ".repeat(cols - leftCells - rightCells)
        line("$left$gap$right")
    }

    override fun emptyLine() = line("")

    override fun hr() = apply {
        line(HR_SYMBOL.repeat(cols))
    }

    override fun fillVertical(minus: Int) = apply {
        repeat(rows - (cursor.y + minus)) { cursor.nextLine() }
    }

    override fun multiLine(text: String) = apply {
        text.split("\n").forEach(::line)
    }

    fun toFullString(): String =
        buffer.joinToString("\n") { it.joinToString("") }

    fun reset() = apply {
        for (x in buffer.indices) {
            for (y in buffer[x].indices) {
                buffer[x][y] = " "
            }
        }
        cursor.reset()
    }

    private fun set(x: Int, y: Int, cell: String) {
        if (x >= cols || y >= rows) {
            throw InvalidTextmapException("Textmap overflow at ($x,$y) for size ${cols}x$rows; cell=[$cell]")
        }
        buffer[y][x] = cell
    }

    fun getGrid(): Array<Array<String>> = buffer.map { it.copyOf() }.toTypedArray()

    private fun countCells(text: String): Int {
        var count = 0
        var i = 0
        while (i < text.length) {
            val codePoint = text.codePointAt(i)
            count++
            i += Character.charCount(codePoint)
        }
        return count
    }

    companion object {
        private const val HR_SYMBOL = "="
    }
}
