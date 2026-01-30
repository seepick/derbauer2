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
    private val buffer = Array(rows) { Array(cols) { ' ' } }
    private val cursor = Cursor()

    override fun line(line: String) = apply {
        for (c in line.toCharArray()) {
            if (c == '\n') throw InvalidTextmapException("Newline character not allowed in line(): '$line' => use multiLine() instead.")
            set(cursor.x, cursor.y, c)
            cursor.x++
        }
        cursor.nextLine()
    }

    override fun aligned(left: String, right: String) = apply {
        if (left.length + right.length > cols) {
            throw InvalidTextmapException("Cannot align left='$left' and right='$right' in width $cols")
        }
        val gap = " ".repeat(cols - left.length - right.length)
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
                buffer[x][y] = ' '
            }
        }
        cursor.reset()
    }

    private fun set(x: Int, y: Int, char: Char) {
        if (x >= cols || y >= rows) {
            throw InvalidTextmapException("Textmap overflow at ($x,$y) for size ${cols}x$rows; char=[$char]")
        }
        buffer[y][x] = char
    }

    companion object {
        private const val HR_SYMBOL = "="
    }
}
