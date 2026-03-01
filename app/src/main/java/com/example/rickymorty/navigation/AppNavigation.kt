package com.example.rickymorty.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.rickymorty.ui.screens.PersonajesListScreen
import com.example.rickymorty.viewmodels.PersonajeViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator




@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(AppRoutes.PersonajesList); //AGREGAAAAAAAR
    val personajeViewModel: PersonajeViewModel = viewModel()




    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        transitionSpec = {
            slideInHorizontally(initialOffsetX = { it}) togetherWith slideOutHorizontally (
                targetOffsetX = {- it})
        },
        predictivePopTransitionSpec = {
            slideInHorizontally(initialOffsetX = { -it}) togetherWith slideOutHorizontally (
                targetOffsetX = { it})
        },
        popTransitionSpec = {
            slideInHorizontally(initialOffsetX = { -it}) togetherWith slideOutHorizontally (
                targetOffsetX = { it})
        },

        entryProvider = { key ->
            when (key) {
                is AppRoutes.PersonajesList -> NavEntry(key){
                    PersonajesListScreen(
                        viewModel = personajeViewModel,
                        navToPersonajeDetail = { personaje ->
                            backStack.add(AppRoutes.PersonajeDetail(personaje))
                        }
                        )
                }

                is AppRoutes.PersonajeDetail -> NavEntry(key){
                    //personajeScreen(personaje = key.personaje)
                }
                else -> {
                    error("Unknown route: $key")
                }
            }


        }

    )
}