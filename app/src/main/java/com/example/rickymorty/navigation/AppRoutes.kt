package com.example.rickymorty.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import com.example.rickymorty.data.PersonajeInfo

@Serializable
sealed class AppRoutes() : NavKey {
    @Serializable data object Home : NavKey
    @Serializable data object PersonajesList : NavKey
    @Serializable data class PersonajeDetail(val personaje: PersonajeInfo) : NavKey
}
