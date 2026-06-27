package com.neeraj.algoplay.feature.sudoku.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.neeraj.algoplay.core.model.SudokuBoard as SudokuBoardModel

private const val GRID_SIZE = 9

@Composable
fun SudokuBoard(
    board: SudokuBoardModel,
    givenCells: Set<Pair<Int, Int>>,
    selectedRow: Int,
    selectedCol: Int,
    onCellClick: (row: Int, col: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    GameCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            repeat(GRID_SIZE) { row ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    repeat(GRID_SIZE) { col ->
                        val isSelected = row == selectedRow && col == selectedCol
                        val cellValue = board[row][col]
                        val isGiven = row to col in givenCells
                        val isConflict = hasConflict(board, row, col)
                        val selectedValue = if (selectedRow in 0 until GRID_SIZE && selectedCol in 0 until GRID_SIZE) {
                            board[selectedRow][selectedCol]
                        } else {
                            0
                        }
                        val isSameNum = !isSelected && selectedValue != 0 && cellValue == selectedValue
                        val isSameRowCol = !isSelected && (row == selectedRow || col == selectedCol)
                        val isSameBox = !isSelected && isSameBox(row, col, selectedRow, selectedCol)

                        val bgColor = when {
                            isSelected -> Color(0xFFD7EBFF)
                            isSameNum -> Color(0xFFE3F2FD)
                            isSameRowCol || isSameBox -> Color(0xFFF3F6F9)
                            else -> Color(0xFFFAFAFA)
                        }

                        SudokuCell(
                            value = cellValue,
                            isGiven = isGiven,
                            isConflict = isConflict,
                            bgColor = bgColor,
                            row = row,
                            col = col,
                            isSelected = isSelected,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onCellClick(row, col) }
                        )
                    }
                }
            }
        }
    }
}

private fun isSameBox(r1: Int, c1: Int, r2: Int, c2: Int): Boolean =
    r2 in 0 until GRID_SIZE && c2 in 0 until GRID_SIZE && (r1 / 3 == r2 / 3) && (c1 / 3 == c2 / 3)

private fun hasConflict(board: SudokuBoardModel, row: Int, col: Int): Boolean {
    val v = board[row][col]
    if (v == 0) return false
    for (c in 0 until GRID_SIZE) if (c != col && board[row][c] == v) return true
    for (r in 0 until GRID_SIZE) if (r != row && board[r][col] == v) return true
    val br = (row / 3) * 3
    val bc = (col / 3) * 3
    for (r in br until br + 3) {
        for (c in bc until bc + 3) {
            if ((r != row || c != col) && board[r][c] == v) return true
        }
    }
    return false
}
