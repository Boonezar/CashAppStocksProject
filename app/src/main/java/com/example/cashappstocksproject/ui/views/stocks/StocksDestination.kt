package com.example.cashappstocksproject.ui.views.stocks

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cashappstocksproject.ui.views.VIEW_EFFECTS_KEY
import com.example.cashappstocksproject.ui.views.stocks.StocksContract.Effect.ToPreviousScreen
import com.example.cashappstocksproject.ui.views.stocks.StocksContract.Event.CallApiByOption
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun StocksDestination(navController: NavController, option: String) {
    val viewModel = hiltViewModel<StocksViewModel>()
    StocksScreen(
        viewState = viewModel.viewState,
        onEvent = { viewModel.setEvent(it) }
    )

    LaunchedEffect(VIEW_EFFECTS_KEY) {
        viewModel.setEvent(CallApiByOption(option))
        viewModel.effect.onEach { effect ->
            when (effect) {
                ToPreviousScreen -> navController.popBackStack()
            }
        }.collect()
    }
}