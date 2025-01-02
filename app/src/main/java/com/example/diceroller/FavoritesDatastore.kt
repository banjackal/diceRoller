package com.example.diceroller

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


// Extension property to create a DataStore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class FavoritesDataStore(private val context: Context) {
    private val FAVORITES_KEY = stringSetPreferencesKey("favorites_key")

    // Read favorites from DataStore as a Flow
    val favoritesFlow: Flow<List<Pair<Int, Int>>> = context.dataStore.data.map { preferences ->
        // Convert stored string set to List<Pair<Int, Int>>
        preferences[FAVORITES_KEY]?.map { favoriteString ->
            val parts = favoriteString.split("x")
            Pair(parts[0].toInt(), parts[1].toInt())
        } ?: emptyList()
    }

    // Save favorites to DataStore
    suspend fun saveFavorites(favorites: List<Pair<Int, Int>>) {
        context.dataStore.edit { preferences ->
            // Convert List<Pair<Int, Int>> to a Set of strings for storage
            preferences[FAVORITES_KEY] = favorites.map { "${it.first}x${it.second}" }.toSet()
        }
    }
}