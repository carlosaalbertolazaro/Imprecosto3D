package com.upchiapas.imprecosto3d.data.model

data class Filament(
    val id: String,
    val name: String,
    val material: String,
    val spoolWeightGrams: Double,
    val spoolPriceMxn: Double,
    val densityGramsPerCubicMeter: Double
){
    val pricePerGram: Double
        get() = if (spoolWeightGrams > 0) spoolPriceMxn / spoolWeightGrams else 0.0
}
