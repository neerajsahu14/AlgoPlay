package com.neeraj.algoplay.feature.sudoku.domain.model

enum class Difficulty(val cellsToRemove: Int) {
    EASY(30),
    MEDIUM(40),
    HARD(50),
    EXPERT(60)
}
