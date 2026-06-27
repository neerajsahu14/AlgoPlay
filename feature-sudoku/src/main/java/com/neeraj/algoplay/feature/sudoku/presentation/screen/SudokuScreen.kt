package com.neeraj.algoplay.feature.sudoku.presentation.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.windowsizeclass.WindowSizeClass
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
            .statusBarsPadding()
    ) {
        // Minimalist Header with Back Button and Difficulty Label
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { showExitDialog = true }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }

            Text(
                text = "Sudoku • $difficultyLabel",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        SudokuGameContent(
            windowSizeClass = windowSizeClass,
            uiState = uiState,
            onDifficultyChange = { viewModel.generateNewGame(it, force = true) },
            onNumberClick = { row, col, value -> viewModel.updateCell(row, col, value) },
            onGenerate = { viewModel.generateNewGame(force = true) },
            onCheck = viewModel::checkBoard,
            onSolve = viewModel::solvePuzzle,
            onClear = viewModel::clearCell,
            showDifficultySelector = false,
            modifier = Modifier.weight(1f)
        )
    }
}
