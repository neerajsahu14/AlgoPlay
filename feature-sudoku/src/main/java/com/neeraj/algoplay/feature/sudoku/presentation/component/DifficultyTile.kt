package com.neeraj.algoplay.feature.sudoku.presentation.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neeraj.algoplay.feature.sudoku.presentation.screen.DifficultyMeta

@Composable
fun DifficultyTile(
    meta: DifficultyMeta,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val bgColor by animateColorAsState(
        targetValue = if (isSelected) meta.accentLight else MaterialTheme.colorScheme.surface,
        animationSpec = tween(200),
        label = "tile_bg"
    )
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) meta.accentDark else MaterialTheme.colorScheme.outline.copy(alpha = 0.25f),
        animationSpec = tween(200),
        label = "tile_border"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(bgColor)
            .border(
                width = if (isSelected) 1.5.dp else 0.5.dp,
                color = borderColor,
                shape = RoundedCornerShape(14.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Colored dot indicator
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(RoundedCornerShape(50))
                    .background(if (isSelected) meta.dotColor else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
            )

            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text       = meta.label,
                    fontSize   = 15.sp,
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                    color      = if (isSelected) meta.accentDark
                    else MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text     = meta.subtitle,
                    fontSize = 12.sp,
                    color    = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.45f)
                )
            }
        }

        // Clues badge
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(
                    if (isSelected) meta.accentDark.copy(alpha = 0.1f)
                    else MaterialTheme.colorScheme.surfaceVariant
                )
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Text(
                text      = meta.cluesRange,
                fontSize  = 11.sp,
                fontWeight = FontWeight.Medium,
                color     = if (isSelected) meta.accentDark
                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                textAlign = TextAlign.Center
            )
        }
    }
}
