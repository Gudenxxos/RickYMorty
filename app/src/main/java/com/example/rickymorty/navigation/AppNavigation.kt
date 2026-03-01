package com.example.rickymorty.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.scene.rememberSceneState
import androidx.navigation3.ui.NavDisplay
import com.example.rickymorty.navigation.ChListRoute
import com.example.rickymorty.ui.screens.PersonajesListScreen


@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(RouteListScreen); //AGREGAAAAAAAR

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
                is RouteListScreen -> NavEntry(key){
                    PersonajesListScreen(onPersonajeClick = { personaje ->
                        /*backStack.add(
                                ChListRoute(
                                    character = personaje
                                )
                            )*/
                        })
                }


                else -> {
                    error("Unknown route: $key")
                }
            }


        }

    )
}