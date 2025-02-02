package com.listener.onlyoffice.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject

private val Context.userDataStore by preferencesDataStore(name = "user_data")

class DataStoreManager @Inject constructor(context: Context) {

    private val userDS = context.userDataStore

    suspend fun get(key: String): String? {
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = userDS.data.first()
        return preferences[dataStoreKey]
    }

    suspend fun save(key: String, data: String) {
        val dataStoreKeyIsFirstStart = stringPreferencesKey(key)

        userDS.edit { preference ->
            preference[dataStoreKeyIsFirstStart] = data
        }
    }
}