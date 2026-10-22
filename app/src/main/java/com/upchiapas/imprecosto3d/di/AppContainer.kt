package com.upchiapas.imprecosto3d.di

import android.content.Context
import com.upchiapas.imprecosto3d.data.local.UserPreferencesDataStore
import com.upchiapas.imprecosto3d.data.repository.ImpreCostoRepository
import com.upchiapas.imprecosto3d.data.repository.ImpreCostoRepositoryImpl

class AppContainer(private val context: Context) {

    private val userPreferencesDataStore: UserPreferencesDataStore by lazy {
        UserPreferencesDataStore(context)
    }

    val impreCostoRepository: ImpreCostoRepository by lazy {
        ImpreCostoRepositoryImpl(userPreferencesDataStore)
    }
}