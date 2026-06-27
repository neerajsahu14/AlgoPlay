package com.neeraj.algoplay.feature.sudoku.algorithm

import com.neeraj.algoplay.core.extension.deepCopy
import com.neeraj.algoplay.core.model.SudokuBoard
import com.neeraj.algoplay.feature.sudoku.domain.model.Difficulty
import com.neeraj.algoplay.feature.sudoku.domain.model.SudokuPuzzle
import javax.inject.Inject
import kotlin.random.Random

class SudokuGenerator @Inject constructor(
    private val solver: SudokuSolver,
    private val random: Random = Random.Default
) {

    fun generate(difficulty: Difficulty): SudokuPuzzle {
        val solution = emptyBoard()

        fillDiagonal(solution)

        solver.solve(solution)

        val puzzle = solution.deepCopy()
        removeCells(puzzle, difficulty.cellsToRemove)

        return SudokuPuzzle(
            puzzle = puzzle,
            solution = solution
        )
    }

    private fun fillDiagonal(grid: SudokuBoard) {
        for (i in 0 until GRID_SIZE step BOX_SIZE) {
            fillBox(grid, i, i)
        }
    }

    private fun fillBox(grid: SudokuBoard, row: Int, col: Int) {
        var num: Int
        for (i in 0 until BOX_SIZE) {
            for (j in 0 until BOX_SIZE) {
                do {
                    num = random.nextInt(MIN_VALUE, MAX_VALUE_EXCLUSIVE)
                } while (!isUnusedInBox(grid, row, col, num))
                grid[row + i][col + j] = num
            }
        }
    }

    private fun removeCells(grid: SudokuBoard, cellsToRemove: Int) {
        var remaining = cellsToRemove
        while (remaining > 0) {
            val cellId = random.nextInt(0, GRID_SIZE * GRID_SIZE)
            val row = cellId / GRID_SIZE
            val col = cellId % GRID_SIZE

            if (grid[row][col] != 0) {
                grid[row][col] = 0
                remaining--
            }
        }
    }

    private fun isUnusedInBox(grid: SudokuBoard, rowStart: Int, colStart: Int, num: Int): Boolean {
        for (i in 0 until BOX_SIZE) {
            for (j in 0 until BOX_SIZE) {
                if (grid[rowStart + i][colStart + j] == num) {
                    return false
                }
            }
        }
        return true
    }

    private fun emptyBoard(): SudokuBoard = SudokuBoard(GRID_SIZE) { IntArray(GRID_SIZE) }

    private companion object {
        const val GRID_SIZE = 9
        const val BOX_SIZE = 3
        const val MIN_VALUE = 1
        const val MAX_VALUE_EXCLUSIVE = 10
    }
}
