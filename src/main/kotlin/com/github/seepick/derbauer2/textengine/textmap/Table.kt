package com.github.seepick.derbauer2.textengine.textmap

import com.github.seepick.derbauer2.game.common.countGraphemes

interface TableGenerator {
    fun <T> transformingTable(
        cols: List<TransformingTableCol<T>>,
        rowItems: List<T>,
    ): List<String>

    fun table(
        cols: List<TableCol>,
        rows: List<List<String>>,
    ): List<String>
}

class LineWritingTableGenerator(private val lineWriter: (String) -> Unit) : TableGenerator {

    override fun <T> transformingTable(
        cols: List<TransformingTableCol<T>>,
        rowItems: List<T>,
    ): List<String> =
        table(
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

    private fun calculateColumnWidths(rows: List<List<String>>): IntArray {
        val numCols = rows.maxOf { it.size }
        return IntArray(numCols) { colIndex ->
            rows.maxOf { row -> row.getOrNull(colIndex)?.countGraphemes() ?: 0 }
        }
    }

    private fun renderTableRow(
        row: List<String>,
        cols: List<TableCol>,
        colWidths: IntArray
    ): List<String> = colWidths.indices.map { colIndex ->
        val cell = row.getOrNull(colIndex) ?: ""
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
