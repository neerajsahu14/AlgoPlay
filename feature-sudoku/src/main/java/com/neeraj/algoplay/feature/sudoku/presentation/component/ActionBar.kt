package com.neeraj.algoplay.feature.sudoku.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ActionBar(
    onGenerate: () -> Unit,
    onCheck: () -> Unit,
    onSolve: () -> Unit,
    modifier: Modifier = Modifier
) {
    GameCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(onClick = onGenerate, modifier = Modifier.weight(1f)) {
                    Text("Generate")
                }
                FilledTonalButton(onClick = onCheck, modifier = Modifier.weight(1f)) {
                    Text("Check")
                }
            }
            Button(onClick = onSolve, modifier = Modifier.fillMaxWidth()) {
                Text("Solve")
            }
        }
    }
}
