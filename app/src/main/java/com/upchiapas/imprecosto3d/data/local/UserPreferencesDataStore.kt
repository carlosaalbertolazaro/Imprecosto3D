package com.upchiapas.imprecosto3d.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.upchiapas.imprecosto3d.data.model.CalculationResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_preferences")

class UserPreferencesDataStore(private val context: Context) {

    private val gson = Gson()

    companion object {
        private val SAVED_QUOTES_KEY = stringPreferencesKey("saved_quotes_json")
    }

    // Flujo para leer las cotizaciones guardadas desde el json
    val savedQuotesFlow: Flow<List<CalculationResult>> = context.dataStore.data.map { preferences ->
        val jsonString = preferences[SAVED_QUOTES_KEY] ?: "[]"
        val type = object : TypeToken<List<CalculationResult>>() {}.type
        gson.fromJson(jsonString, type) ?: emptyList()
    }

    // Función para guardar una nueva cotización en disco
    suspend fun saveQuote(quote: CalculationResult) {
        context.dataStore.edit { preferences ->
            val currentJson = preferences[SAVED_QUOTES_KEY] ?: "[]"
            val type = object : TypeToken<List<CalculationResult>>() {}.type
            val currentList: MutableList<CalculationResult> = gson.fromJson(currentJson, type) ?: mutableListOf()

            currentList.add(quote)

            preferences[SAVED_QUOTES_KEY] = gson.toJson(currentList)
        }
    }

    // Función para eliminar una cotización del disco
    suspend fun deleteQuote(id: String) {
        context.dataStore.edit { preferences ->
            val currentJson = preferences[SAVED_QUOTES_KEY] ?: "[]"
            val type = object : TypeToken<List<CalculationResult>>() {}.type
            val currentList: MutableList<CalculationResult> = gson.fromJson(currentJson, type) ?: mutableListOf()

            currentList.removeAll { it.id == id }

            preferences[SAVED_QUOTES_KEY] = gson.toJson(currentList)
        }
    }
}