package com.example.shiftlab2024notesapp.ui

import NotesRoute
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shiftlab2024notesapp.navigation.NavControllerHolder
import com.example.shiftlab2024notesapp.notes.ui.NotesScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun MainScreen() {

    val navController = rememberNavController()
    val navControllerHolder = koinInject<NavControllerHolder>()

    DisposableEffect(key1 = Unit) {
        navControllerHolder.setNavController(navController)
        onDispose {
            navControllerHolder.clearNavController()
        }
    }

    NavHost(
        navController = navController,
        startDestination = NotesRoute
    ) {
        composable<NotesRoute> {
            NotesScreen(
                viewModel = koinViewModel()
            )
        }
    }
}
