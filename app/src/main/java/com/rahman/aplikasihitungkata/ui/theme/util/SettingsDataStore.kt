package com.rahman.aplikasihitungkata.ui.theme.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.rahman.aplikasihitungkata.ui.theme.model.AppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map



val THEME_KEY = stringPreferencesKey("app_theme")
val Context.dataStore : DataStore<Preferences> by preferencesDataStore(
    name = "settings_preference"
)

class SettingsDataStore(private val context: Context) {

    companion object {
        private val IS_LIST = booleanPreferencesKey("is_list")
    }

    val themeFlow: Flow<AppTheme> = context.dataStore.data.map { preferences ->
        val name = preferences[THEME_KEY] ?: AppTheme.SYSTEM.name
        AppTheme.valueOf(name)
    }

    suspend fun saveTheme(theme: AppTheme) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = theme.name
        }
    }

    val layoutFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_LIST] ?: true
    }


    suspend fun saveLayout(isList: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_LIST] = isList
        }
    }
}