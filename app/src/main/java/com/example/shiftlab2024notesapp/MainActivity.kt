package com.example.shiftlab2024notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.resources.ui.ShiftLab2024NotesAppTheme
import com.example.shiftlab2024notesapp.ui.MainScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ShiftLab2024NotesAppTheme {
                MainScreen()
            }
        }

    }
}
