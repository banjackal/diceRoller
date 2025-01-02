package com.example.diceroller

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.diceroller.components.*

@Composable
fun DiceApp() {
    var showRollDialog by remember { mutableStateOf(false) }
    var showResultDialog by remember { mutableStateOf(false) }

    var numberOfDice by remember { mutableStateOf(1) }
    val diceSideOptions = listOf(2, 4, 6, 8, 10, 12, 20)
    var diceSides by remember { mutableStateOf(diceSideOptions[0]) }

    var results by remember { mutableStateOf(listOf<Int>()) }
    var favorites by remember { mutableStateOf(listOf<Pair<Int, Int>>()) }

    Scaffold(
        topBar = {
            AppBar(title = "Dice Roller")
        },
        bottomBar = {
            BottomBar(text = "This is a test android native app")
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            DiceGrid(
                diceSideOptions = diceSideOptions.takeLast(diceSideOptions.size - 1),
                onRoll = { sides ->
                    numberOfDice = 1
                    diceSides = sides
                    results = rollDice(numberOfDice, diceSides)
                    showResultDialog = true
                }
            )

            Button(
                onClick = { showRollDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Custom Roll")
            }

            if (favorites.isNotEmpty()) {
                Text("Favorite Rolls:", style = MaterialTheme.typography.headlineSmall)
                FavoritesList(
                    favorites = favorites,
                    onRollFavorite = { favorite ->
                        numberOfDice = favorite.first
                        diceSides = favorite.second
                        results = rollDice(numberOfDice, diceSides)
                        showResultDialog = true
                    },
                    onRemoveFavorite = { favorite ->
                        favorites = favorites - favorite
                    }
                )
            }

            if (showRollDialog) {
                CustomRollDialog(
                    diceSideOptions = diceSideOptions,
                    onRoll = { dice, sides ->
                        numberOfDice = dice
                        diceSides = sides
                        results = rollDice(numberOfDice, diceSides)
                        showRollDialog = false
                        showResultDialog = true
                    },
                    onClose = { showRollDialog = false }
                )
            }

            if (showResultDialog) {
                RollResultDialog(
                    numberOfDice = numberOfDice,
                    diceSides = diceSides,
                    results = results,
                    isFavorite = favorites.contains(Pair(numberOfDice, diceSides)),
                    onReroll = {
                        results = rollDice(numberOfDice, diceSides)
                    },
                    onAddFavorite = {
                        favorites = favorites + Pair(numberOfDice, diceSides)
                        showResultDialog = false
                    },
                    onClose = { showResultDialog = false }
                )
            }
        }
    }
}