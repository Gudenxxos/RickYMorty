package com.example.rickymorty.data

import kotlinx.serialization.Serializable

@Serializable
data class PersonajeInfo(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val origin: NameContainer,
    val location: NameContainer,
    val image: String,
    val type: String?
)