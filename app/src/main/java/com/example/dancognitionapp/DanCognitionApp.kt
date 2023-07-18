package com.example.dancognitionapp

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.dancognitionapp.bart.BartScreen
import com.example.dancognitionapp.ui.LandingPageScreen


enum class DanCognitionScreen(@StringRes val title: Int) {
    LANDING(title = R.string.app_name),
    BART(title = R.string.bart_screen_title)
}

@Composable
fun DanCognitionApp() {
    // Create NavController
    val navController = rememberNavController()
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen (if not in backstack use landing)
    val currentScreen = DanCognitionScreen.valueOf(
        backStackEntry?.destination?.route ?: DanCognitionScreen.LANDING.name
    )

    NavHost(
        navController = navController,
        startDestination = DanCognitionScreen.LANDING.name,
    ) {
        composable(route = DanCognitionScreen.LANDING.name) {
           LandingPageScreen(
               onClick = { navController.navigate(DanCognitionScreen.BART.name) }
           )
        }
        composable(route = DanCognitionScreen.BART.name) {
            BartScreen()
        }
    }

}