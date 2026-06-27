package com.neeraj.algoplay.feature.sudoku.presentation.screen

import androidx.compose.ui.graphics.Color
import com.neeraj.algoplay.feature.sudoku.domain.model.Difficulty

data class DifficultyMeta(
    val difficulty: Difficulty,
    val label: String,
    val subtitle: String,
    val cluesRange: String,
    val accentLight: Color,
    val accentDark: Color,
    val dotColor: Color
)

internal val difficultyOptions = listOf(
    DifficultyMeta(
        difficulty  = Difficulty.EASY,
        label       = "Easy",
        subtitle    = "Great for beginners",
        cluesRange  = "50 clues",
        accentLight = Color(0xFFE8F5E9),
        accentDark  = Color(0xFF2E7D32),
        dotColor    = Color(0xFF43A047)
    ),
    DifficultyMeta(
        difficulty  = Difficulty.MEDIUM,
        label       = "Medium",
        subtitle    = "A balanced challenge",
        cluesRange  = "40 clues",
        accentLight = Color(0xFFFFF8E1),
        accentDark  = Color(0xFFF57F17),
        dotColor    = Color(0xFFFFB300)
    ),
    DifficultyMeta(
        difficulty  = Difficulty.HARD,
        label       = "Hard",
        subtitle    = "For seasoned solvers",
        cluesRange  = "30 clues",
        accentLight = Color(0xFFFCE4EC),
        accentDark  = Color(0xFFC62828),
        dotColor    = Color(0xFFE53935)
    ),
    DifficultyMeta(
        difficulty  = Difficulty.EXPERT,
        label       = "Expert",
        subtitle    = "No guessing allowed",
        cluesRange  = "20 clues",
        accentLight = Color(0xFFEDE7F6),
        accentDark  = Color(0xFF4527A0),
        dotColor    = Color(0xFF7E57C2)
    )
)
