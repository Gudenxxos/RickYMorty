package com.example.rickymorty.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickymorty.data.PersonajeInfo
import com.example.rickymorty.services.RyMAPI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

data class PersonajeUiState(
    val personajesList: List<PersonajeInfo> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false
)

class PersonajeViewModel(private val ryMAPI: RyMAPI = RyMAPI()) : ViewModel() {
    // Personaje UI state
    private val _uiState = MutableStateFlow(PersonajeUiState())
    val uiState: StateFlow<PersonajeUiState> = _uiState.asStateFlow()

    fun getPersonajeList() {
        if (_uiState.value.isLoading) return
        
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val randomPage = Random.nextInt(1, 43)
                val newPersonajes = ryMAPI.getPersonajes(randomPage)
                _uiState.update { it.copy(
                    personajesList = newPersonajes,
                    isLoading = false
                ) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            try {
                // Generamos una página aleatoria (la API de Rick and Morty tiene 42 páginas de personajes)
                val randomPage = Random.nextInt(1, 43)
                val newPersonajes = ryMAPI.getPersonajes(randomPage)
                
                _uiState.update { it.copy(
                    personajesList = newPersonajes,
                    isRefreshing = false
                ) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isRefreshing = false) }
            }
        }
    }
}