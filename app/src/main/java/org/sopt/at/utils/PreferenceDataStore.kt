package org.sopt.at.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

object PreferenceDataStore {
    private val EMAIL_KEY = stringPreferencesKey("email")
    private val PASSWORD_KEY = stringPreferencesKey("password")

    fun getEmail(context: Context): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[EMAIL_KEY]
        }
    }

    suspend fun setEmail(context: Context, email: String) {
        context.dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = email
        }
    }

    fun getPassword(context: Context): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[PASSWORD_KEY]
        }
    }

    suspend fun setPassword(context: Context, pw: String) {
        context.dataStore.edit { preferences ->
            preferences[PASSWORD_KEY] = pw
        }
    }
}