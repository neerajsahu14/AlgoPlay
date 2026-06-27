package com.neeraj.algoplay.feature.sudoku.domain.usecase

import com.neeraj.algoplay.feature.sudoku.algorithm.SudokuValidator
import com.neeraj.algoplay.core.model.SudokuBoard
import javax.inject.Inject

class ValidateSudokuUseCase @Inject constructor(
    private val validator: SudokuValidator
) {

    operator fun invoke(board: SudokuBoard): Boolean {
        return validator.validate(board)
    }
}