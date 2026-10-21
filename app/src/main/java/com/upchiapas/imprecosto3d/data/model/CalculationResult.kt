package com.upchiapas.imprecosto3d.data.model

data class CalculationResult(
    val id: String,
    val projectName: String,
    val weightGrams: Double,
    val printHours: Double,
    val materialUsed: String,
    val filamentCost: Double,
    val energyCost: Double,
    val wearCost: Double,
    val totalDirectCost: Double,
    val finalPrice: Double,
    val timestamp: Long = System.currentTimeMillis()
    )
