package com.neeraj.algoplay.core.extension

import com.neeraj.algoplay.core.model.SudokuBoard

fun SudokuBoard.deepCopy(): SudokuBoard = Array(size) { this[it].copyOf() }
