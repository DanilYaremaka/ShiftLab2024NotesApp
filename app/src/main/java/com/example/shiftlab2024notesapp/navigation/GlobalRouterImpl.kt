package com.example.shiftlab2024notesapp.navigation

import androidx.navigation.NavController

class GlobalRouterImpl: GlobalRouter, NavControllerHolder {

    private var navController: NavController? = null

    override fun openWithSavingState(route: Any) {
        //val navController = navController?: return
        navController?.navigate(route)
    }

    override fun openWithRestoreState(route: Any) {
        val navController = navController?: return
        navController.navigate(route) {
            popUpTo(navController.graph.startDestinationId) {
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    override fun openWithSaveAndRestoreState(route: Any) {
        val navController = navController?: return
        navController.navigate(route) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    override fun pop() {
        navController?.popBackStack()
    }

    override fun setNavController(navController: NavController) {
        this.navController = navController
    }

    override fun clearNavController() {
        navController = null
    }
}