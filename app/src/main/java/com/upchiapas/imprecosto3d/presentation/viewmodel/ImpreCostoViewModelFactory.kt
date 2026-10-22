package com.upchiapas.imprecosto3d.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.upchiapas.imprecosto3d.data.repository.ImpreCostoRepository

class ImpreCostoViewModelFactory(
    private val repository: ImpreCostoRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ImpreCostoViewModel::class.java)) {
            return ImpreCostoViewModel(repository) as T
        }
        throw IllegalArgumentException("Clase ViewModel desconocida: ${modelClass.name}")
    }
}