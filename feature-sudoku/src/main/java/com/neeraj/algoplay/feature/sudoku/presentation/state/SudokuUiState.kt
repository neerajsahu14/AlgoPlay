package com.neeraj.algoplay.feature.sudoku.presentation.state

import com.neeraj.algoplay.core.model.SudokuBoard
import com.neeraj.algoplay.feature.sudoku.domain.model.Difficulty

data class SudokuUiState(
    val puzzle: SudokuBoard = Array(9) { IntArray(9) },
    val solution: SudokuBoard = Array(9) { IntArray(9) },
    val givenCells: Set<Pair<Int, Int>> = emptySet(),
    val isLoading: Boolean = false,
    val isGameActive: Boolean = false,
    val selectedDifficulty: Difficulty = Difficulty.MEDIUM,
    val statusMessage: String? = null,
    val canUndo: Boolean = false,
    val canRedo: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SudokuUiState

        if (isLoading != other.isLoading) return false
        if (!puzzle.contentDeepEquals(other.puzzle)) return false
        if (!solution.contentDeepEquals(other.solution)) return false
        if (givenCells != other.givenCells) return false
        if (isGameActive != other.isGameActive) return false
        if (selectedDifficulty != other.selectedDifficulty) return false
        if (statusMessage != other.statusMessage) return false
        if (canUndo != other.canUndo) return false
        if (canRedo != other.canRedo) return false

        return true
    }

    override fun hashCode(): Int {
        var result = isLoading.hashCode()
        result = 31 * result + puzzle.contentDeepHashCode()
        result = 31 * result + solution.contentDeepHashCode()
        result = 31 * result + givenCells.hashCode()
        result = 31 * result + isGameActive.hashCode()
        result = 31 * result + selectedDifficulty.hashCode()
        result = 31 * result + (statusMessage?.hashCode() ?: 0)
        result = 31 * result + canUndo.hashCode()
        result = 31 * result + canRedo.hashCode()
        return result
    }
}
