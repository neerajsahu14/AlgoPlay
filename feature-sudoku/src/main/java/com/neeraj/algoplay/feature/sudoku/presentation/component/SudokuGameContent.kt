package com.neeraj.algoplay.feature.sudoku.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
    onUndo: () -> Unit,
    onRedo: () -> Unit,
    canUndo: Boolean,
    canRedo: Boolean,
    showDifficultySelector: Boolean,
    modifier: Modifier = Modifier
) {
    var selectedRow by rememberSaveable { mutableIntStateOf(-1) }
    var selectedCol by rememberSaveable { mutableIntStateOf(-1) }

    val isExpanded = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded
    val colorScheme = MaterialTheme.colorScheme

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator(color = colorScheme.primary)
        } else {
            if (isExpanded) {
                // Horizontal layout for tablets/expanded screens
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 75.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(70.dp),
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
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        UndoRedoControls(
                            canUndo = canUndo,
                            canRedo = canRedo,
                            onUndo = onUndo,
                            onRedo = onRedo
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

                    UndoRedoControls(
                        canUndo = canUndo,
                        canRedo = canRedo,
                        onUndo = onUndo,
                        onRedo = onRedo
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

@Composable
private fun UndoRedoControls(
    canUndo: Boolean,
    canRedo: Boolean,
    onUndo: () -> Unit,
    onRedo: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedButton(
            onClick = onUndo,
            enabled = canUndo,
            modifier = Modifier.weight(1f)
        ) {
            Text(text = "Undo")
        }
        OutlinedButton(
            onClick = onRedo,
            enabled = canRedo,
            modifier = Modifier.weight(1f)
        ) {
            Text(text = "Redo")
        }
    }
}
