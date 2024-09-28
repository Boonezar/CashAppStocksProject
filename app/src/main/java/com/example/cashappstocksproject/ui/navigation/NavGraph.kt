package com.example.cashappstocksproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.cashappstocksproject.ui.navigation.Screens.*
import com.example.cashappstocksproject.ui.views.dashboard.DashboardDestination
import com.example.cashappstocksproject.ui.views.stocks.StocksDestination

@Composable
fun NavGraph(navController: NavHostController) {
    println("SAM: NavGraph")
    NavHost(
        navController = navController,
        startDestination = DASHBOARD.route
    ) {
        composable(route = DASHBOARD.route) {
            println("SAM: NavGraph Dashboard")
            DashboardDestination(navController)
        }
        composable(route = STOCKS.route) {
            println("SAM: NavGraph Stocks")
            StocksDestination(navController, it.arguments?.getString(STOCKS.param) ?: "")
        }
    }
}