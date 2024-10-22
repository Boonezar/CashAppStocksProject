package com.example.cashappstocksproject.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.cashappstocksproject.ui.navigation.Screens.*
import com.example.cashappstocksproject.ui.views.dashboard.DashboardDestination
import com.example.cashappstocksproject.ui.views.stocks.StocksDestination

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = DASHBOARD.route
    ) {
        composable(route = DASHBOARD.route) {
            DashboardDestination(navController)
        }
        composable(route = STOCKS.route) {
            StocksDestination(navController, it.arguments?.getString(STOCKS.param) ?: "")
        }
    }
}