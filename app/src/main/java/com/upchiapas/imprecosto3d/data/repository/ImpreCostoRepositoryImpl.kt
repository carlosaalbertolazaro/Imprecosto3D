package com.upchiapas.imprecosto3d.data.repository

import com.upchiapas.imprecosto3d.data.local.UserPreferencesDataStore
import com.upchiapas.imprecosto3d.data.model.CalculationResult
import com.upchiapas.imprecosto3d.data.model.Filament
import com.upchiapas.imprecosto3d.data.model.PrinterProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ImpreCostoRepositoryImpl(
    private val userPreferencesDataStore: UserPreferencesDataStore
) : ImpreCostoRepository {

    override fun getFilaments(): Flow<List<Filament>> {
        return MutableStateFlow(
            listOf(
                Filament("1", "PLA Estándar", "PLA", 1000.0, 380.0, 1.24),
                Filament("2", "PETG Resistente", "PETG", 1000.0, 450.0, 1.27),
                Filament("3", "ABS Alta Temp", "ABS", 1000.0, 420.0, 1.04),
                Filament("4", "TPU Flexible", "TPU", 1000.0, 550.0, 1.21)
            )
        ).asStateFlow()
    }

    override fun getPrinterProfile(): Flow<PrinterProfile> {
        return MutableStateFlow(
            PrinterProfile("1", "Impresora FDM Standard", 200, 2.50)
        ).asStateFlow()
    }

    override fun getSavedQuotes(): Flow<List<CalculationResult>> {
        return userPreferencesDataStore.savedQuotesFlow
    }

    override suspend fun saveQuote(quote: CalculationResult) {
        userPreferencesDataStore.saveQuote(quote)
    }

    override suspend fun deleteQuote(id: String) {
        userPreferencesDataStore.deleteQuote(id)
    }
}