package com.erdemkaya.scribbledash.game.presentation.components.ui

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStoreCanvas by preferencesDataStore("purchased_canvas")

class PurchasedCanvasDataStore(private val context: Context) {

    companion object {
        private val PURCHASED_CANVAS = stringSetPreferencesKey("purchased_canvas")
    }

    val purchasedCanvas: Flow<Set<String>> = context.dataStoreCanvas.data.map { preferences ->
        preferences[PURCHASED_CANVAS] ?: emptySet()
    }

    suspend fun savePurchasedCanvas(name: String) {
        context.dataStoreCanvas.edit { preferences ->
            val current = preferences[PURCHASED_CANVAS] ?: emptySet()
            preferences[PURCHASED_CANVAS] = current + name
        }
    }
}