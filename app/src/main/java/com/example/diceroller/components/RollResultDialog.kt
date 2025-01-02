package com.example.diceroller.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun RollResultDialog(
    numberOfDice: Int,
    diceSides: Int,
    results: List<Int>,
    isFavorite: Boolean,
    onReroll: () -> Unit,
    onAddFavorite: () -> Unit,
    onClose: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onClose,
        title = {
            Text("Roll Result")
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                // Display roll results
                Text(
                    text = "The dice roll for ${numberOfDice}d${diceSides} is: ${results.joinToString(", ")}",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Total: ${results.sum()}",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
            }
        },
        confirmButton = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                // Reroll button
                Button(
                    onClick = onReroll,
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                ) {
                    Text("Reroll")
                }

                // Add to Favorites button (conditionally rendered)
                if ((numberOfDice > 1 || numberOfDice == 1 && diceSides == 2) && !isFavorite) {
                    Button(
                        onClick = onAddFavorite,
                        modifier = Modifier.fillMaxWidth().height(60.dp),
                    ) {
                        Text("Add Roll to Favorites")
                    }
                }

                // Close button
                Button(
                    onClick = onClose,
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                ) {
                    Text("Close")
                }
            }
        },
        dismissButton = {
            // No separate dismiss button; included in confirmButton Column
        }
    )
}