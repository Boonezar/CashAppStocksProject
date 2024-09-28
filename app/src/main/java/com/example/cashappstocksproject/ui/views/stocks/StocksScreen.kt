package com.example.cashappstocksproject.ui.views.stocks

import android.content.res.Configuration
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cashappstocksproject.R
import com.example.cashappstocksproject.models.StockDTO
import com.example.cashappstocksproject.ui.theme.CashAppStocksProjectTheme
import com.example.cashappstocksproject.ui.views.stocks.StocksContract.Event
import com.example.cashappstocksproject.ui.views.stocks.StocksContract.State
import java.time.Instant
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StocksScreen(
    viewState: androidx.compose.runtime.State<State>,
    onEvent: (event: Event) -> Unit
) {
    val state = viewState.value
    BackHandler { onEvent(Event.OnBack) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        HeaderSection()
        StocksListSection(state)
    }
}

@Composable
private fun HeaderSection() {
    Text(
        text = stringResource(id = R.string.stocks),
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp)
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StocksListSection(state: State) {
    if (state.isLoading) {
        AlertDialog(
            onDismissRequest = {},
            content = {
                Card {
                    Text(
                        text = stringResource(id = R.string.loading),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(24.dp).fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        )
    } else {
        if (state.showError) {
            Text(
                text = stringResource(id = R.string.error_has_occurred),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.error
            )
        } else {
            if (state.stocks.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.no_stocks_to_display),
                    style = MaterialTheme.typography.titleLarge
                )
            } else {
                Box(modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.onSurfaceVariant)
                )
                LazyColumn {
                    items(state.stocks) {
                        StockListItem(it)
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun StockListItem(item: StockDTO) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)){
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.ticker,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = String.format(
                    stringResource(id = R.string.quantity),
                    item.quantity ?: 0
                ),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Column(modifier = Modifier.weight(3f)) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            val dollarAmount = item.currentPriceCents.toDouble() / 100
            val date = Instant.ofEpochSecond(item.currentPriceTimestamp.toLong()).atZone(ZoneId.systemDefault()).toLocalDate()
            Text(
                text = String.format(
                    stringResource(id = R.string.dollar_amount),
                    dollarAmount,
                    item.currency,
                    date.monthValue,
                    date.dayOfMonth,
                    date.year
                ),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
    Box(modifier = Modifier
        .height(1.dp)
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.onSurfaceVariant)
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun StocksScreenPreview() {
    CashAppStocksProjectTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            StocksScreen(
                viewState = remember {
                    mutableStateOf(
                        State(
                            stocks = listOf(
                                StockDTO(
                                    ticker = "RUNWAY",
                                    name = "Rent The Runway",
                                    currency = "USD",
                                    currentPriceCents = 24819,
                                    quantity = 20,
                                    currentPriceTimestamp = 1681845832
                                ),
                                StockDTO(
                                    ticker = "^DJI",
                                    name = "Dow Jones Industrial Average",
                                    currency = "USD",
                                    currentPriceCents = 2648154,
                                    quantity = 5,
                                    currentPriceTimestamp = 1681845832
                                ),
                                StockDTO(
                                    ticker = "^TNX",
                                    name = "Treasury Yield 10 Years - Treasury Yield 10 Years",
                                    currency = "USD",
                                    currentPriceCents = 61,
                                    quantity = null,
                                    currentPriceTimestamp = 1681845832
                                )
                            ),
                            isLoading = false,
                            showError = false
                        )
                    )
                },
                onEvent = {}
            )
        }
    }
}