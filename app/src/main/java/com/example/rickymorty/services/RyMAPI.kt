package com.example.rickymorty.services

import com.example.rickymorty.data.PersonajeInfo

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get

import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement


class RyMAPI {

    private val jsonConfig = Json {
        explicitNulls = false
        ignoreUnknownKeys = true
    }
    private val client = HttpClient(OkHttp){
        defaultRequest {
            url("https://rickandmortyapi.com/api/character/")
        }

        install(Logging){
            logger = Logger.SIMPLE
        }

        install(ContentNegotiation){
            json(jsonConfig)
        }
    }

    suspend fun getPersonajes(page: Int = 1): List<PersonajeInfo> {
        val response: JsonObject = client.get("?page=$page").body()
        val resultadoPersonajes = response["results"] ?: return emptyList()
        return jsonConfig.decodeFromJsonElement<List<PersonajeInfo>>(resultadoPersonajes)
    }
}