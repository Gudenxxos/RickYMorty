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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rickymorty.data.PersonajeInfo
import com.example.rickymorty.ui.components.ListItem
import com.example.rickymorty.ui.theme.RickYMortyTheme
import com.example.rickymorty.viewmodels.PersonajeViewModel
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PersonajesListScreen(
    navToPersonajeDetail: (PersonajeInfo) -> Unit = {},
    viewModel: PersonajeViewModel
) {
    val personajeUiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        if (personajeUiState.personajesList.isEmpty()) {
            viewModel.getPersonajeList()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = personajeUiState.personajesList.isEmpty() && personajeUiState.isLoading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        PullToRefreshBox(
            isRefreshing = personajeUiState.isRefreshing,
            onRefresh = { viewModel.refresh() },
            modifier = Modifier.fillMaxSize()
        ) {
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
                        Text(
                            "Personajes: ${personajeUiState.personajesList.size}",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
                items(items = personajeUiState.personajesList, key = { it.id }) { pers ->
                    ListItem(personaje = pers, onClick = { navToPersonajeDetail(pers) })
                    HorizontalDivider()
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PersonajeListScreenPreview() {
    RickYMortyTheme(dynamicColor = false) {
        PersonajesListScreen(
            viewModel = viewModel()
        )
    }
}