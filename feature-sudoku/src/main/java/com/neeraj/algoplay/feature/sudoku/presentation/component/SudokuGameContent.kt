package com.neeraj.algoplay.feature.sudoku.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.neeraj.algoplay.feature.sudoku.domain.model.Difficulty
import com.neeraj.algoplay.feature.sudoku.presentation.state.SudokuUiState

@Composable
fun SudokuGameContent(
    windowSizeClass: WindowSizeClass,
    uiState: SudokuUiState,
    onDifficultyChange: (Difficulty) -> Unit,
    onNumberClick: (Int, Int, Int) -> Unit,
    onGenerate: () -> Unit,
    onCheck: () -> Unit,
    onSolve: () -> Unit,
    onClear: (Int, Int) -> Unit,
    showDifficultySelector: Boolean,
    modifier: Modifier = Modifier
) {
    var selectedRow by rememberSaveable { mutableStateOf(-1) }
    var selectedCol by rememberSaveable { mutableStateOf(-1) }

    val isExpanded = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else {
            if (isExpanded) {
                // Horizontal layout for tablets/expanded screens
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalArrangement = Arrangement.spacedBy(32.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.weight(1.2f),
                        contentAlignment = Alignment.Center
                    ) {
                        SudokuBoard(
                            board = uiState.puzzle,
                            givenCells = uiState.givenCells,
                            selectedRow = selectedRow,
                            selectedCol = selectedCol,
                            onCellClick = { row, col ->
                                selectedRow = row
                                selectedCol = col
                            }
                        )
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (showDifficultySelector) {
                            DifficultySelector(uiState.selectedDifficulty, onDifficultyChange)
                        }

                        ActionBar(
                            onGenerate = onGenerate,
                            onCheck = onCheck,
                            onSolve = onSolve
                        )
                        
                        NumberPad(
                            onNumberClick = { value ->
                                if (selectedRow != -1 && selectedCol != -1) {
                                    onNumberClick(selectedRow, selectedCol, value)
                                }
                            },
                            onBackspace = {
                                if (selectedRow != -1 && selectedCol != -1) {
                                    onClear(selectedRow, selectedCol)
                                }
                            }
                        )
                        GameStatus(uiState.statusMessage)
                    }
                }
            } else {
                // Vertical layout for phones
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    if (showDifficultySelector) {
                        DifficultySelector(uiState.selectedDifficulty, onDifficultyChange)
                    }
                    SudokuBoard(
                        board = uiState.puzzle,
                        givenCells = uiState.givenCells,
                        selectedRow = selectedRow,
                        selectedCol = selectedCol,
                        onCellClick = { row, col ->
                            selectedRow = row
                            selectedCol = col
                        }
                    )

                    ActionBar(
                        onGenerate = onGenerate,
                        onCheck = onCheck,
                        onSolve = onSolve
                    )

                    NumberPad(
                        onNumberClick = { value ->
                            if (selectedRow != -1 && selectedCol != -1) {
                                    onNumberClick(selectedRow, selectedCol, value)
                                }
                        },
                        onBackspace = {
                            if (selectedRow != -1 && selectedCol != -1) {
                                onClear(selectedRow, selectedCol)
                            }
                        }
                    )
                    GameStatus(uiState.statusMessage)
                }
            }
        }
    }
}
