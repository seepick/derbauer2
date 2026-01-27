package com.github.seepick.derbauer2.textengine

interface Output {
    fun line(line: String): Output
    fun hr(): Output
    fun aligned(left: String, right: String)
    fun emptyLine(): Output = line("")
    fun fillVertical(minus: Int)
    fun multiLine(text: String)
}

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

class Textmap(
    private val cols: Int,
    private val rows: Int,
) : Output {
    companion object {
        private const val HR_SYMBOL = "="
    }

    private val buffer = Array(rows) { Array(cols) { ' ' } }
    private var cursor = Cursor()

    override fun line(line: String) = apply {
        for (c in line.toCharArray()) {
            if(c == '\n') error("Newline character not allowed in line(): '$line' => use multiLine() instead.")
            set(cursor.x, cursor.y, c)
            cursor.x++
        }
        cursor.nextLine()
    }

    override fun aligned(left: String, right: String) {
        val gap = " ".repeat(cols - left.length - right.length)
        line("$left$gap$right")
    }

    override fun hr() = apply {
        line(HR_SYMBOL.repeat(cols))
    }

    override fun fillVertical(minus: Int) {
        repeat(rows - (cursor.y + minus)) { cursor.nextLine() }
    }

    override fun multiLine(text: String) {
        text.split("\n").forEach(::line)
    }

    fun toFullString(): String =
        buffer.joinToString("\n") { it.joinToString("") }

    fun reset() {
        for (x in buffer.indices) {
            for (y in buffer[x].indices) {
                buffer[x][y] = ' '
            }
        }
        cursor.reset()
    }

    private fun set(x: Int, y: Int, char: Char) {
        buffer[y][x] = char
    }
}
