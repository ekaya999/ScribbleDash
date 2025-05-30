package com.erdemkaya.scribbledash.game.presentation.components.ui

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStoreCoins by preferencesDataStore("coins")

class CoinsDataStore(private val context: Context) {

    companion object {
        private val COINS = intPreferencesKey("coins")
    }

    val coins: Flow<Int> = context.dataStoreCoins.data.map { preferences ->
        preferences[COINS] ?: 0
    }

    suspend fun updateCoins(newCoins: Int) {
        context.dataStoreCoins.edit { preferences ->
            preferences[COINS] = newCoins
        }
    }
}