package com.example.rickymorty.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import com.example.rickymorty.data.PersonajeInfo

@Serializable
data object RouteListScreen : NavKey

@Serializable
data object RouteB : NavKey

@Serializable
data object RouteC : NavKey

@Serializable
data object ChListRoute : NavKey

@Serializable
data class ChDetailRoute(val personaje: PersonajeInfo) : NavKey