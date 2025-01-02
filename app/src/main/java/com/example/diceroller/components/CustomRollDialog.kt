package com.example.diceroller.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                // Number of Dice Spinner
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text("Quantity: ", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.align(Alignment.CenterVertically))
                    selectBox({numberOfDiceExpanded = !numberOfDiceExpanded}, numberOfDice)
                }
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

                // Dice Sides Spinner
                Row (horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text("Sides: ", style = MaterialTheme.typography.bodyMedium,  modifier = Modifier.align(Alignment.CenterVertically))
                    selectBox({diceSidesExpanded = !diceSidesExpanded}, diceSides)
                }
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

@Composable
private fun selectBox(onClick: () -> Unit, display: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                shape = MaterialTheme.shapes.small
            )
            .padding(16.dp)
    ) {
        Text(
            text = "$display",
            modifier = Modifier.align(Alignment.CenterStart),
            style = MaterialTheme.typography.bodyMedium
        )
        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = "Expand",
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}