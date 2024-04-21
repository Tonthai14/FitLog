package com.example.logger

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.logger.Routes.ADD_ENTRY_ROUTE
import com.example.logger.Routes.DAY_ROUTE
import com.example.logger.Routes.EDIT_ENTRY_ROUTE
import com.example.logger.Routes.ENTRY_DETAILS_ROUTE
import com.example.logger.Routes.HOME_ROUTE
import com.example.logger.addentry.AddEntryRoute
import com.example.logger.day.DayRoute
import com.example.logger.editentry.EditEntryRoute
import com.example.logger.entrydetails.EntryDetailsRoute
import com.example.logger.home.HomeRoute

object Routes {
    const val HOME_ROUTE = "home"
    const val DAY_ROUTE = "day/{date}"
    const val ADD_ENTRY_ROUTE = "add/{date}"
    const val ENTRY_DETAILS_ROUTE = "details/entry/{id}"
    const val EDIT_ENTRY_ROUTE = "edit/entry/{id}"
}

@Composable
fun ExerciseLogNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = HOME_ROUTE) {
        composable(HOME_ROUTE) {
            HomeRoute(
                onNavigateToDay = { date -> navController.navigate("day/$date") }
            )
        }
        composable(DAY_ROUTE) {
            val date = it.arguments?.getString("date")
            DayRoute(
                date = date,
                onNavigateToAddEntry = { inputDate -> navController.navigate("add/$inputDate") },
                onNavigateToEntry = { id -> navController.navigate("details/entry/$id") }
            )
        }
        composable(ADD_ENTRY_ROUTE) {
            val date = it.arguments?.getString("date")
            AddEntryRoute(
                date,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(ENTRY_DETAILS_ROUTE) {
            val entryId = it.arguments?.getString("id")!!.toLong()
            val entry = EntryDatabase.getInstance(null).getEntry(entryId)
            EntryDetailsRoute(
                entry = entry,
                onNavigateToEdit = { navController.navigate("edit/entry/$entryId") }
            )
        }
        composable(EDIT_ENTRY_ROUTE) {
            val entryId = it.arguments?.getString("id")!!.toLong()
            val entry = EntryDatabase.getInstance(null).getEntry(entryId)
            EditEntryRoute(
                entry = entry,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}