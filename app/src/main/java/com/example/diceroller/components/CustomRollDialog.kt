package com.example.diceroller.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun CustomRollDialog(
    diceSideOptions: List<Int>,
    onRoll: (Int, Int) -> Unit,
    onClose: () -> Unit
) {
    var numberOfDice by remember { mutableStateOf(1) }
    var diceSides by remember { mutableStateOf(diceSideOptions[0]) }

    AlertDialog(
        onDismissRequest = onClose,
        title = {
            Text("Custom Roll")
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                // Number of Dice Spinner
                Text(text = "Quantity", style = MaterialTheme.typography.bodyMedium)
                AndroidView(
                    factory = { context ->
                        Spinner(context).apply {
                            adapter = ArrayAdapter(
                                context,
                                android.R.layout.simple_spinner_dropdown_item,
                                (1..99).toList()
                            )
                            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: android.view.View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    numberOfDice = (1..99).toList()[position]
                                }

                                override fun onNothingSelected(parent: AdapterView<*>?) {}
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                // Dice Sides Spinner
                Text(text = "Sides", style = MaterialTheme.typography.bodyMedium)
                AndroidView(
                    factory = { context ->
                        Spinner(context).apply {
                            adapter = ArrayAdapter(
                                context,
                                android.R.layout.simple_spinner_dropdown_item,
                                diceSideOptions
                            )
                            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: android.view.View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    diceSides = diceSideOptions[position]
                                }

                                override fun onNothingSelected(parent: AdapterView<*>?) {}
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = { onRoll(numberOfDice, diceSides) }, modifier = Modifier.height(60.dp)) {
                Text("Roll")
            }
        },
        dismissButton = {
            Button(onClick = onClose, modifier = Modifier.height(60.dp)) {
                Text("Close")
            }
        }
    )
}