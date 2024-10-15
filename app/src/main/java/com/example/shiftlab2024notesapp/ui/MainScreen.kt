package com.example.shiftlab2024notesapp.ui

import NotesRoute
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shiftlab2024notesapp.navigation.NavControllerHolder
import com.example.shiftlab2024notesapp.notes.ui.NotesScreen
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

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = NotesRoute
        ) {
            composable<NotesRoute> {
                NotesScreen(
                    paddingValues = paddingValues
                )
            }
        }
    }
}
