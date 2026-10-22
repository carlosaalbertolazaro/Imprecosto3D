package com.upchiapas.imprecosto3d.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upchiapas.imprecosto3d.data.model.CalculationResult
import com.upchiapas.imprecosto3d.data.repository.ImpreCostoRepository
import com.upchiapas.imprecosto3d.presentation.state.ImpreCostoUiState
import com.upchiapas.imprecosto3d.presentation.state.ImpreCostoUserEvent
import com.upchiapas.imprecosto3d.presentation.state.QuoteItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ImpreCostoViewModel(
    private val repository: ImpreCostoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ImpreCostoUiState())
    val uiState: StateFlow<ImpreCostoUiState> = _uiState.asStateFlow()

    init {
        recalculateCosts()
        observeSavedQuotes()
    }


    private fun observeSavedQuotes() {
        viewModelScope.launch {
            repository.getSavedQuotes().collect { savedList ->
                _uiState.update { state ->
                    state.copy(
                        savedQuotes = savedList.map { result ->
                            QuoteItem(
                                id = result.id,
                                name = result.projectName,
                                price = result.finalPrice,
                                details = "${result.weightGrams.toInt()}g • ${result.printHours}h • ${result.materialUsed}"
                            )
                        }
                    )
                }
            }
        }
    }

    fun onEvent(event: ImpreCostoUserEvent) {
        when (event) {
            is ImpreCostoUserEvent.OnProjectNameChange -> {
                _uiState.update { it.copy(projectName = event.name) }
            }
            is ImpreCostoUserEvent.OnWeightChange -> {
                _uiState.update { it.copy(weightGramsText = event.weight) }
                recalculateCosts()
            }
            is ImpreCostoUserEvent.onHoursChange -> {
                _uiState.update { it.copy(printHoursText = event.hours) }
                recalculateCosts()
            }
            is ImpreCostoUserEvent.onMaterialChange -> {
                _uiState.update { it.copy(selectedMaterial = event.material) }
                recalculateCosts()
            }
            is ImpreCostoUserEvent.onMarginChange -> {
                _uiState.update { it.copy(profitMargin = event.margin) }
                recalculateCosts()
            }
            ImpreCostoUserEvent.OnSaveQuote -> saveCurrentQuote()
            is ImpreCostoUserEvent.OnDeleteQuote -> deleteQuote(event.id)
        }
    }

    private fun recalculateCosts() {
        val currentState = _uiState.value
        val grams = currentState.weightGramsText.toDoubleOrNull() ?: 0.0
        val hours = currentState.printHoursText.toDoubleOrNull() ?: 0.0

        val spoolPriceMxn = when (currentState.selectedMaterial) {
            "PETG" -> 450.0
            "ABS" -> 420.0
            "TPU" -> 550.0
            else -> 380.0
        }

        val filamentCostCalculated = grams * (spoolPriceMxn / 1000.0)
        val energyCostCalculated = (200.0 / 1000.0) * hours * 2.10
        val wearCostCalculated = hours * 2.50
        val totalDirect = filamentCostCalculated + energyCostCalculated + wearCostCalculated
        val finalCalculatedPrice = totalDirect * (1.0 + (currentState.profitMargin / 100.0))

        _uiState.update { state ->
            state.copy(
                filamentCost = filamentCostCalculated,
                energyCost = energyCostCalculated,
                wearCost = wearCostCalculated,
                totalDirectCost = totalDirect,
                finalPrice = finalCalculatedPrice
            )
        }
    }

    private fun saveCurrentQuote() {
        val currentState = _uiState.value
        val grams = currentState.weightGramsText.toDoubleOrNull() ?: 0.0
        val hours = currentState.printHoursText.toDoubleOrNull() ?: 0.0

        val nameToSave = if (currentState.projectName.isBlank()) {
            "Cotización #${System.currentTimeMillis().toString().takeLast(4)}"
        } else {
            currentState.projectName
        }

        val quoteToSave = CalculationResult(
            id = System.currentTimeMillis().toString(),
            projectName = nameToSave,
            weightGrams = grams,
            printHours = hours,
            materialUsed = currentState.selectedMaterial,
            filamentCost = currentState.filamentCost,
            energyCost = currentState.energyCost,
            wearCost = currentState.wearCost,
            totalDirectCost = currentState.totalDirectCost,
            finalPrice = currentState.finalPrice
        )

        viewModelScope.launch {
            repository.saveQuote(quoteToSave)
            _uiState.update { it.copy(projectName = "") }
        }
    }

    private fun deleteQuote(idToDelete: String) {
        viewModelScope.launch {
            repository.deleteQuote(idToDelete)
        }
    }
}