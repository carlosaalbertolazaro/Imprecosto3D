package com.upchiapas.imprecosto3d.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MaterialFilterChips(
    selectedMaterial: String,
    onMaterialSelected: (String) -> Unit,
    modifier: Modifier = Modifier
){
    val materials = listOf("PLA", "PETG", "ABS", "TPU")

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        materials.forEach { material ->
            FilterChip(
                selected = (material == selectedMaterial),
                onClick = {onMaterialSelected(material)},
                label = {Text(material)}
            )


        }
    }
}