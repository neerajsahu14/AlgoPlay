package com.neeraj.algoplay.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Algorithm(
    val title: String,
    val icon: String,
    val tags: String,
    val status: String,
    val isAvailable: Boolean = false,
    val route: String? = null,
)

private val algorithms = listOf(
    Algorithm(
        title = "Sudoku",
        icon = "🧩",
        tags = "Backtracking",
        status = "Completed",
        isAvailable = true,
        route = "sudoku_home"
    ),
    Algorithm(
        title = "N Queens",
        icon = "👑",
        tags = "Backtracking",
        status = "Coming Soon"
    ),
    Algorithm(
        title = "Maze",
        icon = "🌲",
        tags = "BFS • DFS • A*",
        status = "Coming Soon"
    ),
    Algorithm(
        title = "Word Search",
        icon = "🔤",
        tags = "Trie + DFS",
        status = "Coming Soon"
    ),
    Algorithm(
        title = "LRU Cache",
        icon = "💾",
        tags = "HashMap + DLL",
        status = "Coming Soon"
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onAlgorithmClick: (Algorithm) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "AlgoPlay",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                        Text(
                            text = "Interactive Algorithm Playground",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(algorithms) { algorithm ->
                AlgorithmItem(
                    algorithm = algorithm,
                ) { if (algorithm.isAvailable) onAlgorithmClick(algorithm) }
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    thickness = 0.5.dp,
                    color = MaterialTheme.colorScheme.outlineVariant
                )
            }
        }
    }
}

@Composable
fun AlgorithmItem(
    algorithm: Algorithm,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = algorithm.isAvailable, onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = algorithm.icon,
            fontSize = 32.sp,
            modifier = Modifier.padding(end = 16.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = algorithm.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = algorithm.tags,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = algorithm.status,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = if (algorithm.isAvailable) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
        )
    }
}
