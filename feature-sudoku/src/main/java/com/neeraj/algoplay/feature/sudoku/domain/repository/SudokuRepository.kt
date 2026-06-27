package com.neeraj.algoplay.feature.sudoku.domain.repository

import com.neeraj.algoplay.feature.sudoku.domain.model.Difficulty
import com.neeraj.algoplay.feature.sudoku.domain.model.SudokuPuzzle

interface SudokuRepository {
    suspend fun generate(difficulty: Difficulty): SudokuPuzzle
}
