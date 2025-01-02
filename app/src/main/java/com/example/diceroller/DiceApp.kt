package com.example.diceroller

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.diceroller.components.*
import kotlinx.coroutines.launch

@Composable
fun DiceApp(context: Context) {
    val favoritesDataStore = remember { FavoritesDataStore(context) }
    var favorites by remember { mutableStateOf(emptyList<Pair<Int, Int>>()) }
    val coroutineScope = rememberCoroutineScope()

    // Observe favorites from DataStore
    LaunchedEffect(Unit) {
        favoritesDataStore.favoritesFlow.collect { savedFavorites ->
            favorites = savedFavorites
        }
    }

    diceApp(
        favorites = favorites,
        onAddFavorite = { favorite ->
            favorites = favorites + favorite
            // Save the updated favorites list to DataStore
            coroutineScope.launch {
                favoritesDataStore.saveFavorites(favorites)
            }
        },
        onRemoveFavorite = { favorite ->
            favorites = favorites - favorite
            // Save the updated favorites list to DataStore
            coroutineScope.launch {
                favoritesDataStore.saveFavorites(favorites)
            }
        }
    )
}

@Composable
fun diceApp(
    favorites: List<Pair<Int, Int>>,
    onAddFavorite: (Pair<Int, Int>) -> Unit,
    onRemoveFavorite: (Pair<Int, Int>) -> Unit
) {
    var showRollDialog by remember { mutableStateOf(false) }
    var showResultDialog by remember { mutableStateOf(false) }

    var numberOfDice by remember { mutableStateOf(1) }
    val diceSideOptions = listOf(2, 4, 6, 8, 10, 12, 20)
    var diceSides by remember { mutableStateOf(diceSideOptions[0]) }

    var results by remember { mutableStateOf(listOf<Int>()) }

    Scaffold(
        topBar = {
            AppBar(title = "Dice Roller")
        },
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
                modifier = Modifier.fillMaxWidth().height(60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
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
                    onRemoveFavorite = onRemoveFavorite
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
                    onAddFavorite = onAddFavorite,
                    onClose = { showResultDialog = false }
                )
            }
        }
    }
}