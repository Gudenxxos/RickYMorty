package com.example.rickymorty.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rickymorty.data.PersonajeInfo
import com.example.rickymorty.services.RyMAPI
import com.example.rickymorty.ui.components.ListItem
import com.example.rickymorty.ui.theme.RickYMortyTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PersonajesListScreen(onPersonajeClick: (PersonajeInfo) -> Unit = {}) {

    val rickMortyApi = RyMAPI()
    var personajesList by remember { mutableStateOf(listOf<PersonajeInfo>()) }

    LaunchedEffect(key1 = null) {
        personajesList = rickMortyApi.getPersonajes()
    }

    AnimatedVisibility(
        personajesList.isEmpty(),
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        stickyHeader {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.inversePrimary)
                    .padding(16.dp)
            ) {
                Text("Pokemon: ${personajesList.size}", style = MaterialTheme.typography.titleLarge)
            }
        }
        items(items = personajesList, key = { it.id }) { pers ->
            ListItem(personaje = pers, onClick = { onPersonajeClick(pers) })
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PersonajeListScreenPreview() {
    RickYMortyTheme(dynamicColor = false) {
        PersonajesListScreen(
            onPersonajeClick = {}
        )
    }
}