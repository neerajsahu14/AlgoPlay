package com.neeraj.algoplay.feature.sudoku.domain.usecase

import com.neeraj.algoplay.feature.sudoku.domain.model.Difficulty
import com.neeraj.algoplay.feature.sudoku.domain.model.SudokuPuzzle
import com.neeraj.algoplay.feature.sudoku.domain.repository.SudokuRepository
import javax.inject.Inject

class GenerateSudokuUseCase @Inject constructor(
    private val repository: SudokuRepository
) {

    suspend operator fun invoke(
        difficulty: Difficulty
    ): SudokuPuzzle {
        return repository.generate(difficulty)
    }
}
