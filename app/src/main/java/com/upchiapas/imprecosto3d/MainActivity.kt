package com.upchiapas.imprecosto3d

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.upchiapas.imprecosto3d.presentation.screens.CalculatorScreen
import com.upchiapas.imprecosto3d.presentation.theme.ImpreCosto3DTheme
import com.upchiapas.imprecosto3d.presentation.viewmodel.ImpreCostoViewModel
class MainActivity : ComponentActivity() {
    private val viewModel: ImpreCostoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImpreCosto3DTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                    CalculatorScreen(
                        uiState = uiState,
                        onEvent = viewModel::onEvent
                    )
                }
            }
        }
    }
}

