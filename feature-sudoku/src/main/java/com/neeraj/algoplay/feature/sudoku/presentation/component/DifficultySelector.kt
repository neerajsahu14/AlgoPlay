package com.neeraj.algoplay.feature.sudoku.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.neeraj.algoplay.feature.sudoku.domain.model.Difficulty

@Composable
fun DifficultySelector(
    selectedDifficulty: Difficulty,
    onDifficultyChange: (Difficulty) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Difficulty.entries.forEach { difficulty ->
            FilterChip(
                selected = selectedDifficulty == difficulty,
                onClick = { onDifficultyChange(difficulty) },
                label = { Text(difficulty.label) }
            )
        }
    }
}

private val Difficulty.label: String
    get() = when (this) {
        Difficulty.EASY -> "🟢 Easy"
        Difficulty.MEDIUM -> "🟡 Medium"
        Difficulty.HARD -> "🔴 Hard"
        Difficulty.EXPERT -> "⚫ Expert"
    }
