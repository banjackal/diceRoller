package com.example.diceroller.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomRollDialog(
    diceSideOptions: List<Int>,
    onRoll: (Int, Int) -> Unit,
    onClose: () -> Unit
) {
    var numberOfDice by remember { mutableStateOf(1) }
    var diceSides by remember { mutableStateOf(diceSideOptions[0]) }

    var numberOfDiceExpanded by remember { mutableStateOf(false) }
    var diceSidesExpanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onClose,
        title = {
            Text("Custom Roll")
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                // Number of Dice Dropdown
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { numberOfDiceExpanded = true },
                    text = "Number of Dice: $numberOfDice",
                    style = MaterialTheme.typography.bodyMedium
                )
                DropdownMenu(
                    expanded = numberOfDiceExpanded,
                    onDismissRequest = { numberOfDiceExpanded = false }
                ) {
                    (1..99).forEach { option ->
                        DropdownMenuItem(
                            onClick = {
                                numberOfDice = option
                                numberOfDiceExpanded = false
                            },
                            text = { Text(option.toString()) }
                        )
                    }
                }

                // Dice Sides Dropdown
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { diceSidesExpanded = true },
                    text = "Dice Sides: $diceSides",
                    style = MaterialTheme.typography.bodyMedium
                )
                DropdownMenu(
                    expanded = diceSidesExpanded,
                    onDismissRequest = { diceSidesExpanded = false }
                ) {
                    diceSideOptions.forEach { option ->
                        DropdownMenuItem(
                            onClick = {
                                diceSides = option
                                diceSidesExpanded = false
                            },
                            text = { Text(option.toString()) }
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = { onRoll(numberOfDice, diceSides) }) {
                Text("Roll")
            }
        },
        dismissButton = {
            Button(onClick = onClose) {
                Text("Close")
            }
        }
    )
}