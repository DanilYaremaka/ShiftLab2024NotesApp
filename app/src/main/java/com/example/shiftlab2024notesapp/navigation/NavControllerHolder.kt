package com.example.shiftlab2024notesapp.navigation

import androidx.navigation.NavController

interface NavControllerHolder {

    fun setNavController(navController: NavController)

    fun clearNavController()
}