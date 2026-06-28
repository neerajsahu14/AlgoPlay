package com.neeraj.algoplay.feature.sudoku.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameStatus(
    message: String?,
    modifier: Modifier = Modifier
) {
    if (message == null) return

    val colorScheme = MaterialTheme.colorScheme
    val isError = message.contains("conflict", ignoreCase = true) || message.contains("Failed", ignoreCase = true)

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isError) colorScheme.errorContainer else colorScheme.secondaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = statusPrefix(message),
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = if (isError) colorScheme.onErrorContainer else colorScheme.onSecondaryContainer
            )
        }
    }
}

private fun statusPrefix(message: String): String = when {
    message.contains("solved", ignoreCase = true) -> "✅ Puzzle Solved"
    message.contains("conflict", ignoreCase = true) -> "❌ Invalid Move"
    message.contains("valid", ignoreCase = true) -> "✅ Board Valid"
    else -> message
}
