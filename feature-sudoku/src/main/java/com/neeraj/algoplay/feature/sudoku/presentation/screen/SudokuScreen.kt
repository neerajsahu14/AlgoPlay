package com.neeraj.algoplay.feature.sudoku.presentation.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.neeraj.algoplay.feature.sudoku.domain.model.Difficulty
import com.neeraj.algoplay.feature.sudoku.presentation.component.SudokuGameContent
import com.neeraj.algoplay.feature.sudoku.presentation.viewmodel.SudokuViewModel

@Composable
fun SudokuScreen(
    windowSizeClass: WindowSizeClass,
    initialDifficulty: Difficulty,
    onBack: () -> Unit,
    viewModel: SudokuViewModel = hiltViewModel(),
    @Suppress("unused") gameSeed: Long
) {
    val uiState by viewModel.uiState.collectAsState()
    var showExitDialog by remember { mutableStateOf(false) }
    val colorScheme = MaterialTheme.colorScheme

    // Intercept device back button
    BackHandler {
        showExitDialog = true
    }

    LaunchedEffect(initialDifficulty) {
        viewModel.generateNewGame(initialDifficulty)
    }

    val difficultyLabel = difficultyOptions.find { it.difficulty == initialDifficulty }?.label ?: initialDifficulty.name

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text("Quit Game") },
            text = { Text("Are you sure you want to quit the current game? Your progress will be lost.") },
            confirmButton = {
                TextButton(onClick = {
                    showExitDialog = false
                    onBack()
                }) {
                    Text("Quit")
                }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .statusBarsPadding()
    ) {

        SudokuGameContent(
            windowSizeClass = windowSizeClass,
            uiState = uiState,
            onDifficultyChange = { viewModel.generateNewGame(it, force = true) },
            onNumberClick = { row, col, value -> viewModel.updateCell(row, col, value) },
            onGenerate = { viewModel.generateNewGame(force = true) },
            onCheck = viewModel::checkBoard,
            onSolve = viewModel::solvePuzzle,
            onClear = viewModel::clearCell,
            onUndo = viewModel::undo,
            onRedo = viewModel::redo,
            canUndo = uiState.canUndo,
            canRedo = uiState.canRedo,
            showDifficultySelector = false,
            modifier = Modifier.weight(1f)
        )
    }
}
