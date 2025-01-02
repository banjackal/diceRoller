package com.example.diceroller.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FavoritesList(
    favorites: List<Pair<Int, Int>>,
    onRollFavorite: (Pair<Int, Int>) -> Unit,
    onRemoveFavorite: (Pair<Int, Int>) -> Unit
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(favorites) { favorite ->
            Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    modifier = Modifier.weight(1f).height(60.dp),
                    onClick = { onRollFavorite(favorite) }
                ) {
                    Text("Roll ${favorite.first}d${favorite.second}")
                }
                Button(
                    modifier = Modifier.weight(0.2f).height(60.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    onClick = { onRemoveFavorite(favorite) }
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Remove from favorites")
                }
            }
        }
    }
}