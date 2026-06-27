package com.neeraj.algoplay.feature.sudoku.domain.usecase

import com.neeraj.algoplay.feature.sudoku.algorithm.SudokuSolver
import com.neeraj.algoplay.core.model.SudokuBoard
import javax.inject.Inject

class SolveSudokuUseCase @Inject constructor(
    private val solver: SudokuSolver
) {

    operator fun invoke(board: SudokuBoard): SudokuBoard {
        return solver.solve(board)
    }
}