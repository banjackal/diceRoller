package com.example.diceroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.diceroller.ui.theme.DiceRollerTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DiceRollerTheme {
                diceApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun diceApp() {
    var numberOfDice by remember { mutableStateOf(1) } // Default to 1 dice
    var numberOfDiceExpanded by remember { mutableStateOf(false) }

    val diceSideOptions = listOf(2, 4, 6, 8, 10, 12, 20)
    var diceSides by remember { mutableStateOf(diceSideOptions[0]) }
    var diceSidesExpanded by remember { mutableStateOf(false) }

    var results by remember { mutableStateOf(listOf<Int>()) }
    var favorites by remember { mutableStateOf(listOf<Pair<Int, Int>>()) }

    fun rollDice() {
        results = List(numberOfDice) { Random.nextInt(1, diceSides + 1) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Dice Roller")
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "This is a test android native app",
                )
            }
        },

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { numberOfDiceExpanded = true
                           results = listOf()},
            text = "Number of Dice:\n$numberOfDice",
            style = MaterialTheme.typography.bodyMedium
            )
            DropdownMenu(
                expanded = numberOfDiceExpanded,
                onDismissRequest = { numberOfDiceExpanded = false }
            ) {
                (1..99).forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            numberOfDice = option // Update selected option
                            numberOfDiceExpanded = false // Close the dropdown
                        },
                        text = { Text(option.toString()) }
                    )
                }
            }

            // Dropdown for dice sides
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { diceSidesExpanded = true
                               results = listOf()},
                text = "Dice Sides:\n$diceSides",
                style = MaterialTheme.typography.bodyMedium
            )
            DropdownMenu(
                expanded = diceSidesExpanded,
                onDismissRequest = { diceSidesExpanded = false }
            ) {
                diceSideOptions.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            diceSides = option // Update selected option
                            diceSidesExpanded = false // Close the dropdown
                        },
                        text = { Text(option.toString()) }
                    )
                }
            }
                Button(onClick = {
                    rollDice()
                }) {
                    Text("Roll")
                }

            // Display the results
            if (results.isNotEmpty()) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = """
                        The dice roll for ${numberOfDice}d${diceSides} is ${results.joinToString { it.toString() }}
                        Total is ${results.sum()}
                    """.trimIndent(),
                )
                if (!favorites.contains(Pair(numberOfDice, diceSides))){
                    Button(
                        onClick = {
                            favorites = favorites + Pair(numberOfDice, diceSides)
                        }) {
                        Text("Add roll to favorites")
                    }
                }

            }

            if (favorites.isNotEmpty()){
                Text("Favorite Rolls:", style = MaterialTheme.typography.headlineSmall)
            }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(favorites) { favorite ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Button(
                            modifier = Modifier.weight(1f),
                                onClick =  {
                                    numberOfDice = favorite.first
                                    diceSides = favorite.second
                                    rollDice()
                                })
                        {Text("Roll ${favorite.first}d${favorite.second}")}

                        Button(
                            modifier = Modifier.weight(0.2f),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                            onClick = {
                            favorites = favorites - favorite
                        },
                            )
                        {
                            Icon(Icons.Default.Delete, contentDescription = "Remove from favorites")
                        }
                    }
                }
            }
        }
    }
}