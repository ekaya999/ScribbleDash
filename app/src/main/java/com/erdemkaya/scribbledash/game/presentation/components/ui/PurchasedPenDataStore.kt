package com.erdemkaya.scribbledash.game.presentation.components.ui

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStorePen by preferencesDataStore("purchased_pen")

class PurchasedPenDataStore(private val context: Context) {

    companion object {
        private val PURCHASED_PENS = stringSetPreferencesKey("purchased_pens")
    }

    val purchasedPens: Flow<Set<String>> = context.dataStorePen.data.map { preferences ->
        preferences[PURCHASED_PENS] ?: emptySet()
    }

    suspend fun savePurchasedPen(name: String) {
        context.dataStorePen.edit { preferences ->
            val current = preferences[PURCHASED_PENS] ?: emptySet()
            preferences[PURCHASED_PENS] = current + name
        }
    }
}