package com.github.seepick.derbauer2.textengine

interface Output {
    fun printLine(line: String): Output
    fun printHr(): Output
    fun printAligned(left: String, /*center: String?,*/ right: String)
    fun printEmptyLine(): Output = printLine("")
    fun fillVertical(minus: Int)
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

    override fun printLine(line: String) = apply {
        for (c in line.toCharArray()) {
            set(cursor.x, cursor.y, c)
            cursor.x++
        }
        cursor.nextLine()
    }

    override fun printAligned(left: String, right: String) {
        val gap = " ".repeat(cols - left.length - right.length)
        printLine("$left$gap$right")
    }

    override fun printHr() = apply {
        printLine(HR_SYMBOL.repeat(cols))
    }

    override fun fillVertical(minus: Int) {
        repeat(rows - (cursor.y + minus)) { cursor.nextLine() }
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

    private fun get(x: Int, y: Int): Char = buffer[y][x]
    private fun set(x: Int, y: Int, char: Char) {
        buffer[y][x] = char
    }

}
