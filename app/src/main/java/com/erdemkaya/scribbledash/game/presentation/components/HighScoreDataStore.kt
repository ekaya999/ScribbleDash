package com.erdemkaya.scribbledash.game.presentation.components

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("high_score")

class HighScoreDataStore(private val context: Context) {

    companion object {
        private val SPEED_DRAW_AVG = intPreferencesKey("speed_draw_avg")
        private val SPEED_DRAW_COUNT = intPreferencesKey("speed_draw_count")
        private val ENDLESS_DRAW_AVG = intPreferencesKey("endless_draw_avg")
        private val ENDLESS_DRAW_COUNT = intPreferencesKey("endless_draw_count")
    }

    val speedDrawAvg: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[SPEED_DRAW_AVG] ?: 0
    }

    val speedDrawCount: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[SPEED_DRAW_COUNT] ?: 0
    }

    val endlessDrawAvg: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[ENDLESS_DRAW_AVG] ?: 0
    }

    val endlessDrawCount: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[ENDLESS_DRAW_COUNT] ?: 0
    }

    //für Testzwecke
    suspend fun clearDataStore() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    suspend fun saveSpeedDrawAvgHighScore(score: Int) {
        context.dataStore.edit { preferences ->
            preferences[SPEED_DRAW_AVG] = score
        }
    }

    suspend fun saveSpeedDrawCountHighScore(score: Int) {
        context.dataStore.edit { preferences ->
            preferences[SPEED_DRAW_COUNT] = score
        }
    }

    suspend fun saveEndlessDrawAvgHighScore(score: Int) {
        context.dataStore.edit { preferences ->
            preferences[ENDLESS_DRAW_AVG] = score
        }
    }

    suspend fun saveEndlessDrawCountHighScore(score: Int) {
        context.dataStore.edit { preferences ->
            preferences[ENDLESS_DRAW_COUNT] = score
        }
    }
}