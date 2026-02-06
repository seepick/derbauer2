package com.github.seepick.derbauer2.textengine.textmap

import com.github.seepick.derbauer2.game.common.countGraphemes

interface TableGenerator {
    fun <T> tableByTransform(
        cols: List<TransformingTableCol<T>>,
        rowItems: List<T>,
    ): List<String>

    fun table(
        cols: List<TableCol>,
        rows: List<List<String>>,
    ): List<String>
}

class LineWritingTableGenerator(private val lineWriter: (String) -> Unit) : TableGenerator {

    override fun <T> tableByTransform(
        cols: List<TransformingTableCol<T>>,
        rowItems: List<T>,
    ): List<String> = table(
        cols = cols.map { TableCol(align = it.align) },
        rows = rowItems.mapIndexed { rowIdx, row ->
            cols.mapIndexed { colIdx, col ->
                col.transformRow(rowIdx, colIdx, row)
            }
        }
    )

    override fun table(
        cols: List<TableCol>,
        rows: List<List<String>>,
    ): List<String> {
        if (rows.isEmpty()) {
            return emptyList()
        }
        validateTableRows(rows) // TODO no! support dynamic cols; fill up missing rows with spaces
        val colWidths = calculateColumnWidths(rows)
        return rows.map { row ->
            val cells = renderTableRow(row, cols, colWidths)
            cells.joinToString(CELL_SEPARATOR)
        }.apply {
            forEach {
                lineWriter(it)
            }
        }
    }

    private fun validateTableRows(rows: List<List<String>>) {
        val numCols = rows.first().size
        rows.forEach { row ->
            require(row.size == numCols) {
                "All rows must have the same number of columns (expected=$numCols, actual=${row.size})"
            }
        }
    }

    private fun calculateColumnWidths(rows: List<List<String>>): IntArray {
        val numCols = rows.first().size
        return IntArray(numCols) { colIndex ->
            rows.maxOf { row -> row[colIndex].countGraphemes() }
        }
    }

    private fun renderTableRow(
        row: List<String>,
        cols: List<TableCol>,
        colWidths: IntArray
    ): List<String> = row.mapIndexed { colIndex, cell ->
        val cellWidth = cell.countGraphemes()
        val colWidth = colWidths[colIndex]
        val padding = colWidth - cellWidth
        val colOrNull = cols.getOrNull(colIndex)
        val align = colOrNull?.align ?: TableAlign.DEFAULT
        when (align) {
            TableAlign.Left -> {
                cell + CELL_SEPARATOR.repeat(padding)
            }

            TableAlign.Right -> {
                CELL_SEPARATOR.repeat(padding) + cell
            }

            TableAlign.Center -> {
                val leftPad = padding / 2
                val rightPad = padding - leftPad
                CELL_SEPARATOR.repeat(leftPad) + cell + CELL_SEPARATOR.repeat(rightPad)
            }
        }
    }

    companion object {
        private const val CELL_SEPARATOR = " "
    }
}

data class TableCol(
    // TODO support header titles
    val align: TableAlign = TableAlign.DEFAULT,
)

typealias RowIndex = Int
typealias ColumnIndex = Int

data class TransformingTableCol<T>(
    val align: TableAlign = TableAlign.DEFAULT,
    val transformRow: (RowIndex, ColumnIndex, T) -> String,
)

enum class TableAlign {
    Left, Right, Center;

    companion object {
        val DEFAULT = Left
    }
}