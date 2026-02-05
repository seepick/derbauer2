package com.github.seepick.derbauer2.textengine.textmap

class Grid(val cols: Int, val rows: Int) {

    private val buffer = Array(rows) { Array(cols) { " " } }

    fun set(x: Int, y: Int, cell: String) {
        if (x >= cols || y >= rows) {
            throw InvalidTextmapException("Textmap overflow at ($x,$y) for size ${cols}x$rows; cell=[$cell]")
        }
        buffer[y][x] = cell
    }

    fun reset() {
        for (x in buffer.indices) {
            for (y in buffer[x].indices) {
                buffer[x][y] = " "
            }
        }
    }

    fun toFullString(): String =
        buffer.joinToString("\n") { it.joinToString("") }

    fun toArrayArray(): Array<Array<String>> =
        buffer.map { it.copyOf() }.toTypedArray()

}
