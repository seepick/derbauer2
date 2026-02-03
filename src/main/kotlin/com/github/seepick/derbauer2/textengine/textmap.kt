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

    private fun countCells(text: String): Int = text.extractGraphemes().size

    private fun String.extractGraphemes(): List<String> {
        val result = mutableListOf<String>()
        var start = 0
        var i = 0

        while (i < this.length) {
            // Consume base codepoint
            val codePoint = this.codePointAt(i)
            i += Character.charCount(codePoint)

            // Keep consuming modifiers, combiners, and ZWJ sequences
            while (i < this.length) {
                val nextCodePoint = this.codePointAt(i)

                when {
                    // Variation selectors
                    nextCodePoint in 0xFE00..0xFE0F || nextCodePoint in 0xE0100..0xE01EF -> {
                        i += Character.charCount(nextCodePoint)
                    }
                    // Combining diacritical marks
                    nextCodePoint in 0x0300..0x036F -> {
                        i += Character.charCount(nextCodePoint)
                    }
                    // Emoji modifiers (skin tones)
                    nextCodePoint in 0x1F3FB..0x1F3FF -> {
                        i += Character.charCount(nextCodePoint)
                    }
                    // Zero-width joiner
                    nextCodePoint == 0x200D -> {
                        i += Character.charCount(nextCodePoint)
                        // Consume the character after ZWJ
                        if (i < this.length) {
                            val afterZWJ = this.codePointAt(i)
                            i += Character.charCount(afterZWJ)
                            // Check for variation selector after the emoji following ZWJ
                            if (i < this.length) {
                                val maybeVS = this.codePointAt(i)
                                if (maybeVS in 0xFE00..0xFE0F || maybeVS in 0xE0100..0xE01EF) {
                                    i += Character.charCount(maybeVS)
                                }
                            }
                        }
                    }
                    else -> break // Not a combining/modifier character
                }
            }

            result.add(this.substring(start, i))
            start = i
        }

        return result
    }

    companion object {
        private const val HR_SYMBOL = "="
    }
}
