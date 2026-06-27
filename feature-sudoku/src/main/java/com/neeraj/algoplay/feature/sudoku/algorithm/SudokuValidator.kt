package com.neeraj.algoplay.feature.sudoku.algorithm

import com.neeraj.algoplay.core.model.SudokuBoard
import javax.inject.Inject

class SudokuValidator @Inject constructor() {

    fun validate(board: SudokuBoard): Boolean {
        return rowsAreValid(board) &&
            columnsAreValid(board) &&
            boxesAreValid(board)
    }

    private fun rowsAreValid(board: SudokuBoard): Boolean {
        for (row in 0 until GRID_SIZE) {
            if (!unitIsValid(board[row])) {
                return false
            }
        }
        return true
    }

    private fun columnsAreValid(board: SudokuBoard): Boolean {
        for (col in 0 until GRID_SIZE) {
            val values = IntArray(GRID_SIZE) { row -> board[row][col] }
            if (!unitIsValid(values)) {
                return false
            }
        }
        return true
    }

    private fun boxesAreValid(board: SudokuBoard): Boolean {
        for (rowStart in 0 until GRID_SIZE step BOX_SIZE) {
            for (colStart in 0 until GRID_SIZE step BOX_SIZE) {
                val values = IntArray(GRID_SIZE)
                var index = 0
                for (row in 0 until BOX_SIZE) {
                    for (col in 0 until BOX_SIZE) {
                        values[index++] = board[rowStart + row][colStart + col]
                    }
                }
                if (!unitIsValid(values)) {
                    return false
                }
            }
        }
        return true
    }

    private fun unitIsValid(values: IntArray): Boolean {
        val seen = BooleanArray(GRID_SIZE + 1)
        for (value in values) {
            if (value == 0) {
                continue
            }
            if (value !in MIN_VALUE..MAX_VALUE || seen[value]) {
                return false
            }
            seen[value] = true
        }
        return true
    }

    private companion object {
        const val GRID_SIZE = 9
        const val BOX_SIZE = 3
        const val MIN_VALUE = 1
        const val MAX_VALUE = 9
    }
}