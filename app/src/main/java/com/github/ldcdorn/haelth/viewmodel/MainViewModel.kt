package com.github.ldcdorn.haelth.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val selectedTab: MutableState<String> = mutableStateOf("Exercise") // Standard-Tab
    val navigationEvent: MutableState<String?> = mutableStateOf(null)

    fun selectTab(tab: String) {
        selectedTab.value = tab // Auf 'value' der MutableState-Instanz zugreifen
    }

    fun navigateTo(destination: String) {
        navigationEvent.value = destination // Auf 'value' der MutableState-Instanz zugreifen
    }

    fun clearNavigationEvent() {
        navigationEvent.value = null // Auf 'value' der MutableState-Instanz zugreifen
    }
}