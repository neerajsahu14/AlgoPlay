package com.neeraj.algoplay.feature.sudoku.domain.model

import com.neeraj.algoplay.core.model.SudokuBoard

data class SudokuPuzzle(
    val puzzle: SudokuBoard,
    val solution: SudokuBoard
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SudokuPuzzle

        if (!puzzle.contentDeepEquals(other.puzzle)) return false
        if (!solution.contentDeepEquals(other.solution)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = puzzle.contentDeepHashCode()
        result = 31 * result + solution.contentDeepHashCode()
        return result
    }
}
