package com.upchiapas.imprecosto3d.data.repository

import com.upchiapas.imprecosto3d.data.model.CalculationResult
import com.upchiapas.imprecosto3d.data.model.Filament
import com.upchiapas.imprecosto3d.data.model.PrinterProfile
import kotlinx.coroutines.flow.Flow

interface ImpreCostoRepository {
    fun getFilaments(): Flow<List<Filament>>
    fun getPrinterProfile(): Flow<PrinterProfile>
    fun getSavedQuotes(): Flow<List<CalculationResult>>
    suspend fun saveQuote(quote: CalculationResult)
    suspend fun deleteQuote(id: String)
}