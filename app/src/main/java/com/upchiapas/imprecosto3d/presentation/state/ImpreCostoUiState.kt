package com.upchiapas.imprecosto3d.presentation.state

data class ImpreCostoUiState(
    val projectName: String = "",
    val weightGramsText: String = "50",
    val printHoursText: String = "4.5",
    val selectedMaterial: String = "PLA",
    val profitMargin: Float = 30f,
    val filamentCost: Double = 0.0,
    val energyCost: Double = 0.0,
    val wearCost: Double = 0.0,
    val totalDirectCost: Double = 0.0,
    val finalPrice: Double = 0.0,

    val savedQuotes: List<QuoteItem> = emptyList()
)
data class QuoteItem(
    val id: String,
    val name: String,
    val price: Double,
    val details: String
)