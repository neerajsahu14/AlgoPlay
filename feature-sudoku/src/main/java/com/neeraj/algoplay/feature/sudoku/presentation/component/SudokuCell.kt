package com.neeraj.algoplay.feature.sudoku.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SudokuCell(
    value: Int,
    isGiven: Boolean,
    isConflict: Boolean,
    bgColor: Color,
    row: Int,
    col: Int,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val thickWidth = 3.dp
    val thinWidth = 0.5.dp
    val colorScheme = MaterialTheme.colorScheme

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .background(bgColor)
            .drawBehind {
                val thickPx = thickWidth.toPx()
                val thinPx = thinWidth.toPx()
                val w = size.width
                val h = size.height
                val borderColor = colorScheme.outline.copy(alpha = 0.5f)
                val thickBorderColor = colorScheme.outline

                val topW = if (row % 3 == 0) thickPx else thinPx
                val topC = if (row % 3 == 0) thickBorderColor else borderColor
                drawLine(topC, Offset(0f, topW / 2f), Offset(w, topW / 2f), topW)

                if (row == 8) {
                    drawLine(thickBorderColor, Offset(0f, h - thickPx / 2f), Offset(w, h - thickPx / 2f), thickPx)
                }

                val leftW = if (col % 3 == 0) thickPx else thinPx
                val leftC = if (col % 3 == 0) thickBorderColor else borderColor
                drawLine(leftC, Offset(leftW / 2f, 0f), Offset(leftW / 2f, h), leftW)

                if (col == 8) {
                    drawLine(thickBorderColor, Offset(w - thickPx / 2f, 0f), Offset(w - thickPx / 2f, h), thickPx)
                }

                if (isSelected) {
                    drawRect(
                        color = colorScheme.primary,
                        size = size,
                        style = androidx.compose.ui.graphics.drawscope.Stroke(width = thickPx)
                    )
                }
            },
        contentAlignment = Alignment.Center
    ) {
        if (value != 0) {
            Text(
                text = value.toString(),
                fontSize = 20.sp,
                fontWeight = if (isGiven) FontWeight.Bold else FontWeight.Medium,
                color = when {
                    isConflict -> colorScheme.error
                    isGiven -> colorScheme.onSurface
                    else -> colorScheme.primary
                }
            )
        }
    }
}
