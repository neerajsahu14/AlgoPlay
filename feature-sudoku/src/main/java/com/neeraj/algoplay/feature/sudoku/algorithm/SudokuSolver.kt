package com.neeraj.algoplay.feature.sudoku.algorithm

import com.neeraj.algoplay.core.model.SudokuBoard
import javax.inject.Inject

class SudokuSolver @Inject constructor() {

    fun solve(board: SudokuBoard): SudokuBoard {
        if (!solveRemaining(board, 0, 0)) {
            throw IllegalStateException("Sudoku board could not be solved")
        }
        return board
    }

    private fun solveRemaining(grid: SudokuBoard, row: Int, col: Int): Boolean {
        if (row == GRID_SIZE) {
            return true
        }

        if (col == GRID_SIZE) {
            return solveRemaining(grid, row + 1, 0)
        }

        if (grid[row][col] != 0) {
            return solveRemaining(grid, row, col + 1)
        }

        for (num in MIN_VALUE..MAX_VALUE) {
            if (isSafe(grid, row, col, num)) {
                grid[row][col] = num
                if (solveRemaining(grid, row, col + 1)) {
                    return true
                }
                grid[row][col] = 0
            }
        }

        return false
    }

    private fun isSafe(grid: SudokuBoard, row: Int, col: Int, num: Int): Boolean {
        return isUnusedInRow(grid, row, num) &&
            isUnusedInColumn(grid, col, num) &&
            isUnusedInBox(grid, row - row % BOX_SIZE, col - col % BOX_SIZE, num)
    }

    private fun isUnusedInRow(grid: SudokuBoard, row: Int, num: Int): Boolean {
        for (col in 0 until GRID_SIZE) {
            if (grid[row][col] == num) {
                return false
            }
        }
        return true
    }

    private fun isUnusedInColumn(grid: SudokuBoard, col: Int, num: Int): Boolean {
        for (row in 0 until GRID_SIZE) {
            if (grid[row][col] == num) {
                return false
            }
        }
        return true
    }

    private fun isUnusedInBox(grid: SudokuBoard, rowStart: Int, colStart: Int, num: Int): Boolean {
        for (row in 0 until BOX_SIZE) {
            for (col in 0 until BOX_SIZE) {
                if (grid[rowStart + row][colStart + col] == num) {
                    return false
                }
            }
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