package com.example.cashappstocksproject.ui.views.dashboard

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cashappstocksproject.R
import com.example.cashappstocksproject.ui.theme.CashAppStocksProjectTheme
import com.example.cashappstocksproject.ui.views.dashboard.DashboardContract.*

@Composable
fun DashboardScreen(onEvent: (event: Event) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        HeaderSection()
        ButtonsSection(onEvent)
    }
}

@Composable
private fun HeaderSection() {
    Text(
        text = stringResource(id = R.string.cash_app_stocks_project),
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
    Text(
        text = stringResource(id = R.string.by_sam_christiansen),
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth().padding(24.dp)
    )
}

@Composable
private fun ButtonsSection(onEvent: (event: Event) -> Unit) {
    DashboardButton(
        text = stringResource(id = R.string.get_stocks),
        onClick = { onEvent(Event.OnGetStocks) }
    )
    DashboardButton(
        text = stringResource(id = R.string.get_empty_stocks),
        onClick = { onEvent(Event.OnGetEmptyStocks) }
    )
    DashboardButton(
        text = stringResource(id = R.string.get_error_stocks),
        onClick = { onEvent(Event.OnGetErrorStocks) }
    )
}

@Composable
private fun DashboardButton(text: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        onClick = { onClick() },
        content = { Text(text) }
    )
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DashboardScreenPreview() {
    CashAppStocksProjectTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            DashboardScreen(onEvent = {})
        }
    }
}