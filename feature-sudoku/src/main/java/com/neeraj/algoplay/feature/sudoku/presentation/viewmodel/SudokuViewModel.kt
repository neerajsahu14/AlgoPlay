package com.neeraj.algoplay.feature.sudoku.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neeraj.algoplay.core.extension.deepCopy
import com.neeraj.algoplay.core.model.SudokuBoard
import com.neeraj.algoplay.feature.sudoku.domain.model.Difficulty
import com.neeraj.algoplay.feature.sudoku.domain.usecase.GenerateSudokuUseCase
import com.neeraj.algoplay.feature.sudoku.domain.usecase.SolveSudokuUseCase
import com.neeraj.algoplay.feature.sudoku.domain.usecase.ValidateSudokuUseCase
import com.neeraj.algoplay.feature.sudoku.presentation.state.SudokuUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SudokuViewModel @Inject constructor(
    private val generateSudokuUseCase: GenerateSudokuUseCase,
    private val solveSudokuUseCase: SolveSudokuUseCase,
    private val validateSudokuUseCase: ValidateSudokuUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SudokuUiState())
    val uiState = _uiState.asStateFlow()

    fun generateNewGame(
        difficulty: Difficulty = _uiState.value.selectedDifficulty,
        force: Boolean = false
    ) {
        // Don't regenerate if a game is already active unless forced
        if (!force && _uiState.value.isGameActive) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, selectedDifficulty = difficulty) }
            
            var isValid = false
            var attempts = 0
            val maxAttempts = 5
            
            while (!isValid && attempts < maxAttempts) {
                val sudokuPuzzle = generateSudokuUseCase(difficulty)
                if (validateSudokuUseCase(sudokuPuzzle.puzzle)) {
                    isValid = true
                    _uiState.update {
                        it.copy(
                            puzzle = sudokuPuzzle.puzzle,
                            solution = sudokuPuzzle.solution,
                            givenCells = buildGivenCells(sudokuPuzzle.puzzle),
                            isGameActive = true,
                            statusMessage = null,
                            isLoading = false
                        )
                    }
                }
                attempts++
            }
            
            if (!isValid) {
                _uiState.update { 
                    it.copy(
                        isLoading = false, 
                        isGameActive = false,
                        statusMessage = "Failed to generate a valid puzzle. Please try again."
                    )
                }
            }
        }
    }

    fun updateCell(row: Int, col: Int, value: Int) {
        val currentState = _uiState.value
        if (row !in 0..8 || col !in 0..8) return
        if (row to col in currentState.givenCells) return
        if (value !in 0..9) return

        _uiState.update {
            it.copy(
                puzzle = it.puzzle.deepCopy().apply { this[row][col] = value },
                statusMessage = null
            )
        }
    }

    fun checkBoard() {
        val isValid = validateSudokuUseCase(_uiState.value.puzzle)
        _uiState.update {
            it.copy(
                statusMessage = if (isValid) "Board is valid." else "Board has conflicts."
            )
        }
    }

    fun solvePuzzle() {
        val currentBoard = _uiState.value.puzzle.deepCopy()
        if (!validateSudokuUseCase(currentBoard)) {
            _uiState.update {
                it.copy(statusMessage = "Board has conflicts.")
            }
            return
        }

        val solvedBoard = solveSudokuUseCase(currentBoard)
        _uiState.update {
            it.copy(
                puzzle = solvedBoard,
                statusMessage = "Puzzle solved."
            )
        }
    }

    fun clearCell(row: Int, col: Int) {
        updateCell(row, col, 0)
    }

    private fun buildGivenCells(board: SudokuBoard): Set<Pair<Int, Int>> {
        return buildSet {
            board.forEachIndexed { rowIndex, row ->
                row.forEachIndexed { colIndex, value ->
                    if (value != 0) add(rowIndex to colIndex)
                }
            }
        }
    }
}
