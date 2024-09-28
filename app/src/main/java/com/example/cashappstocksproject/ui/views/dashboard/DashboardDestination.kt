package com.example.cashappstocksproject.ui.views.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cashappstocksproject.ui.navigation.Screens
import com.example.cashappstocksproject.ui.views.VIEW_EFFECTS_KEY
import com.example.cashappstocksproject.ui.views.dashboard.DashboardContract.Effect.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun DashboardDestination(
    navController: NavController
) {
    val viewModel = hiltViewModel<DashboardViewModel>()
    DashboardScreen(onEvent = { viewModel.setEvent(it) })

    LaunchedEffect(VIEW_EFFECTS_KEY) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                is ToStocksScreen -> navController.navigate(Screens.STOCKS.route.replace("{apiOption}", effect.stockOption.value))
            }
        }.collect()
    }
}