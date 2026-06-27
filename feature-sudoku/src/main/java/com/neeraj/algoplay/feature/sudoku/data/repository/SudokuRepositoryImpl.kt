package com.neeraj.algoplay.feature.sudoku.data.repository

import com.neeraj.algoplay.feature.sudoku.algorithm.SudokuGenerator
import com.neeraj.algoplay.feature.sudoku.domain.model.Difficulty
import com.neeraj.algoplay.feature.sudoku.domain.model.SudokuPuzzle
import com.neeraj.algoplay.feature.sudoku.domain.repository.SudokuRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SudokuRepositoryImpl @Inject constructor(
    private val generator: SudokuGenerator
) : SudokuRepository {

    override suspend fun generate(difficulty: Difficulty): SudokuPuzzle {
        return withContext(Dispatchers.Default) {
            generator.generate(difficulty)
        }
    }
}
