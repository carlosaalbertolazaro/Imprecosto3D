package com.upchiapas.imprecosto3d.presentation.state

sealed interface ImpreCostoUserEvent {
    data class OnProjectNameChange(val name: String) : ImpreCostoUserEvent
    data class OnWeightChange(val weight: String) : ImpreCostoUserEvent
    data class onHoursChange(val hours: String) : ImpreCostoUserEvent
    data class onMaterialChange(val material: String) : ImpreCostoUserEvent
    data class onMarginChange(val margin: Float) : ImpreCostoUserEvent
    data class OnDeleteQuote(val id: String) : ImpreCostoUserEvent
    data object OnSaveQuote : ImpreCostoUserEvent
}