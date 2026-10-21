package com.upchiapas.imprecosto3d.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.upchiapas.imprecosto3d.presentation.state.ImpreCostoUiState
import com.upchiapas.imprecosto3d.presentation.state.ImpreCostoUserEvent
import com.upchiapas.imprecosto3d.presentation.ui.components.CostBreakdownCard
import com.upchiapas.imprecosto3d.presentation.ui.components.MaterialFilterChips

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(
    uiState: ImpreCostoUiState,
    onEvent: (ImpreCostoUserEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ImpreCosto 3D", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(4.dp)) }

            // 1. Nombre del proyecto
            item {
                OutlinedTextField(
                    value = uiState.projectName,
                    onValueChange = { onEvent(ImpreCostoUserEvent.OnProjectNameChange(it)) },
                    label = { Text("Nombre del proyecto") },
                    placeholder = { Text("Ej. Casco Ironman") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }

            // 2. Peso y Tiempo
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = uiState.weightGramsText,
                        onValueChange = { onEvent(ImpreCostoUserEvent.OnWeightChange(it)) },
                        label = { Text("Peso (g)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = uiState.printHoursText,
                        onValueChange = { onEvent(ImpreCostoUserEvent.onHoursChange(it)) },
                        label = { Text("Tiempo (h)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }
            }

            // 3. Selección de Material
            item {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Material de Filamento",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    MaterialFilterChips(
                        selectedMaterial = uiState.selectedMaterial,
                        onMaterialSelected = { onEvent(ImpreCostoUserEvent.onMaterialChange(it)) }
                    )
                }
            }

            // 4. Margen de Ganancia
            item {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Margen de Ganancia",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "${uiState.profitMargin.toInt()}%",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Slider(
                        value = uiState.profitMargin,
                        onValueChange = { onEvent(ImpreCostoUserEvent.onMarginChange(it)) },
                        valueRange = 0f..100f
                    )
                }
            }

            // 5. Tarjeta de Desglose
            item {
                CostBreakdownCard(
                    filamentCost = uiState.filamentCost,
                    energyCost = uiState.energyCost,
                    wearCost = uiState.wearCost,
                    totalDirectCost = uiState.totalDirectCost,
                    finalPrice = uiState.finalPrice
                )
            }

            // 6. Botón Guardar
            item {
                Button(
                    onClick = { onEvent(ImpreCostoUserEvent.OnSaveQuote) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(imageVector = Icons.Default.Save, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("GUARDAR COTIZACIÓN EN MEMORIA")
                }
            }

            // 7. Encabezado del Historial
            item {
                Text(
                    text = "Historial Reciente",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            // 8. Lista de cotizaciones
            items(uiState.savedQuotes, key = { it.id }) { quote ->
                OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = quote.name,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = quote.details,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "$${String.format("%.2f", quote.price)} MXN",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            IconButton(onClick = { onEvent(ImpreCostoUserEvent.OnDeleteQuote(quote.id)) }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Eliminar",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}