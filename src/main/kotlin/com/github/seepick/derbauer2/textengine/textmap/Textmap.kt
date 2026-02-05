package com.github.seepick.derbauer2.textengine.textmap

import com.github.seepick.derbauer2.game.common.extractGraphemes

class Textmap(
    private val cols: Int,
    private val rows: Int,
) : TextmapWriter {

    private val cursor = Cursor()
    private val grid = Grid(cols = cols, rows = rows)

    constructor(matrixSize: MatrixSize) : this(cols = matrixSize.cols, rows = matrixSize.rows)

    override fun line(line: String) = apply {
        if (line.contains('\n')) {
            throw InvalidTextmapException(
                "Newline character not allowed in line(): '$line' => use multiLine() instead."
            )
        }
        val graphemes = line.extractGraphemes()
        graphemes.forEach { grapheme ->
            grid.set(cursor.x, cursor.y, grapheme)
            cursor.x++
        }
        cursor.nextLine()
    }

    override fun aligned(left: String, right: String) = apply {
        val leftCells = left.countCells()
        val rightCells = right.countCells()
        if (leftCells + rightCells > cols) {
            throw InvalidTextmapException("Cannot align left='$left' and right='$right' in width $cols")
        }
        val gap = " ".repeat(cols - leftCells - rightCells)
        line("$left$gap$right")
    }

    override fun hr() = apply {
        line(HR_SYMBOL.repeat(cols))
    }

    override fun toFullString() = grid.toFullString()

    override fun toGrid() = grid.toArrayArray()

    override fun fillVertical(minus: Int) = apply {
        repeat(rows - (cursor.y + minus)) { cursor.nextLine() }
    }

    fun reset() = apply {
        grid.reset()
        cursor.reset()
    }

    companion object {
        private const val HR_SYMBOL = "="
        private fun String.countCells(): Int = extractGraphemes().size
    }
}
