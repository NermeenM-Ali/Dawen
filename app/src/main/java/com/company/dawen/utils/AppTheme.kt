package com.company.dawen.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val themeDataStoreName = "Theme_DS"

private val Context.dataStore by preferencesDataStore(themeDataStoreName)

class AppTheme(val context: Context) {
    private val dataStore = context.dataStore

    companion object {
        private val IS_DARK_MODE_ENABLED_KEY = booleanPreferencesKey("is_dark_mode_enabled")
    }

    val isDarkModeEnabled: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_DARK_MODE_ENABLED_KEY] ?: false //light
    }

    suspend fun setDarkModeEnabled(isDarkModeEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_DARK_MODE_ENABLED_KEY] = isDarkModeEnabled
        }
    }
}