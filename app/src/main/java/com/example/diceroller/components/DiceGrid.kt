package com.example.diceroller.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DiceGrid(diceSideOptions: List<Int>, onRoll: (Int) -> Unit) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(diceSideOptions.chunked(2)) { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowItems.forEach { sides ->
                    Button(
                        onClick = { onRoll(sides) },
                        modifier = Modifier.weight(1f).height(60.dp)
                    ) {
                        Text("Roll 1d$sides")
                    }
                }
                if (rowItems.size < 2) {
                    Spacer(modifier = Modifier.weight(1f).height(60.dp))
                }
            }
        }
    }
}