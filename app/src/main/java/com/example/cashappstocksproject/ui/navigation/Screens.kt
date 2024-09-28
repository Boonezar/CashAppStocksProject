package com.example.cashappstocksproject.ui.navigation

enum class Screens(val route: String, val param: String?) {
    DASHBOARD(route = "dashboard", param = null),
    STOCKS(route ="stocks/{apiOption}", param = "apiOption")
}