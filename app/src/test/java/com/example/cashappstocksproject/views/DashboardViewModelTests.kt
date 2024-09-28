package com.example.cashappstocksproject.views

import com.example.cashappstocksproject.MainDispatcherRule
import com.example.cashappstocksproject.models.ApiOption
import com.example.cashappstocksproject.ui.views.dashboard.DashboardContract.Effect.*
import com.example.cashappstocksproject.ui.views.dashboard.DashboardContract.Event.*
import com.example.cashappstocksproject.ui.views.dashboard.DashboardContract.State
import com.example.cashappstocksproject.ui.views.dashboard.DashboardViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class DashboardViewModelTests {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()
    private lateinit var sut: DashboardViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        sut = DashboardViewModel()
    }

    @Test
    fun dashboardViewModel_setInitialState_initialStateCorrect() {
        // Given
        // When
        val result = sut.setInitialState()
        // Then
        Assert.assertEquals(State, result)
    }

    @Test
    fun dashboardViewModel_handleEventsOnGetStocks_setsEffectCorrectly() = runTest {
        // Given
        // When
        sut.handleEvents(OnGetStocks)
        // Then
        Assert.assertEquals(ToStocksScreen(ApiOption.NORMAL), sut.effect.first())
    }

    @Test
    fun dashboardViewModel_handleEventsOnGetEmptyStocks_setsEffectCorrectly() = runTest {
        // Given
        // When
        sut.handleEvents(OnGetEmptyStocks)
        // Then
        Assert.assertEquals(ToStocksScreen(ApiOption.EMPTY), sut.effect.first())
    }

    @Test
    fun dashboardViewModel_handleEventsOnGetErrorStocks_setsEffectCorrectly() = runTest {
        // Given
        // When
        sut.handleEvents(OnGetErrorStocks)
        // Then
        Assert.assertEquals(ToStocksScreen(ApiOption.ERROR), sut.effect.first())
    }
}