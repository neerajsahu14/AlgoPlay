package com.neeraj.algoplay.feature.sudoku.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GameStatus(
    message: String?,
    modifier: Modifier = Modifier
) {
    if (message == null) return

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = statusPrefix(message))
        }
    }
}

private fun statusPrefix(message: String): String = when {
    message.contains("solved", ignoreCase = true) -> "✅ Puzzle Solved"
    message.contains("conflict", ignoreCase = true) -> "❌ Invalid Move"
    message.contains("valid", ignoreCase = true) -> "✅ Board Valid"
    else -> message
}
