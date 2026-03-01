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

    private val JsonConfig = Json {
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
            json(JsonConfig)
        }
    }

    suspend fun getPersonajes(): List<PersonajeInfo> {
        val response: JsonObject = client.get("?page=21").body()
        val resultsElement = response["results"] ?: return emptyList()
        return JsonConfig.decodeFromJsonElement<List<PersonajeInfo>>(resultsElement)
    }

}