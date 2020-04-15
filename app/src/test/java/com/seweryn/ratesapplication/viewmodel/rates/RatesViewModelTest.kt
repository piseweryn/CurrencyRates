package com.seweryn.ratesapplication.viewmodel.rates

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.seweryn.ratesapplication.data.RatesRepository
import com.seweryn.ratesapplication.data.local.sharedprefs.currency.CurrencyRatePreferences
import com.seweryn.ratesapplication.data.model.CurrencyRate
import com.seweryn.ratesapplication.utils.SchedulerProvider
import com.seweryn.ratesapplication.utils.resources.RatesContentProvider
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class RatesViewModelTest {

    private lateinit var systemUnderTest: RatesViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var ratesRepository: RatesRepository

    @Mock
    lateinit var ratesContentProvider: RatesContentProvider

    @Mock
    lateinit var currencyRatePreferences: CurrencyRatePreferences

    @Mock
    lateinit var schedulerProvider: SchedulerProvider


    @Before
    fun setUp() {
        systemUnderTest = RatesViewModel(
            ratesRepository,
            ratesContentProvider,
            currencyRatePreferences,
            schedulerProvider
        )
        mockSchedulers()
        mockLiveData()
        mockContentProvider()
    }

    @Test
    fun `should load rates on start`() {
        simulateRatesCanBeLoaded()
        systemUnderTest.start()
        verify(ratesRepository).getRates(ArgumentMatchers.anyString())
    }

    @Test
    fun `should load rates for default currency when there in no remembered currency`() {
        simulateRatesCanBeLoaded()
        val systemUnderTest = RatesViewModel(
            ratesRepository,
            ratesContentProvider,
            currencyRatePreferences,
            schedulerProvider
        )
        systemUnderTest.start()
        verify(ratesRepository).getRates("EUR")
    }

    @Test
    fun `should load rates for remembered currency code`() {
        simulateRatesCanBeLoaded()
        simulatePreferredCurrencyCanByFetched("PLN")
        val systemUnderTest = RatesViewModel(
            ratesRepository,
            ratesContentProvider,
            currencyRatePreferences,
            schedulerProvider
        )
        systemUnderTest.start()
        verify(ratesRepository).getRates("PLN")
    }

    @Test
    fun `should change currencies amount when value for base currency changed`() {
        simulateRatesCanBeLoaded()
        systemUnderTest.start()
        systemUnderTest.rates.value?.find { it.currencyRate.isBaseCurrency }
            ?.amountEditAction?.invoke("10")
        assertEquals(
            "20",
            systemUnderTest.rates.value?.find { !it.currencyRate.isBaseCurrency }?.currentDisplayRate
        )
    }

    @Test
    fun `should load rates for selected currency`() {
        simulateRatesCanBeLoaded()
        systemUnderTest.start()
        systemUnderTest.rates.value?.find { !it.currencyRate.isBaseCurrency }?.selectAction?.invoke(
            "2"
        )
        verify(ratesRepository).getRates("PLN")
    }

    @Test
    fun `should remember selected currency`() {
        simulateRatesCanBeLoaded()
        systemUnderTest.start()
        systemUnderTest.rates.value?.find { !it.currencyRate.isBaseCurrency }?.selectAction?.invoke(
            "2"
        )
        verify(currencyRatePreferences).rememberCurrencyCode("PLN")
    }

    private fun simulateRatesCanBeLoaded() {
        `when`(ratesRepository.getRates(ArgumentMatchers.anyString())).thenReturn(
            Observable.just(
                createCurrencyRates()
            )
        )
    }

    private fun simulatePreferredCurrencyCanByFetched(currencyCode: String) {
        `when`(currencyRatePreferences.getCurrencyCode()).thenReturn(currencyCode)
    }

    private fun mockLiveData() {
        systemUnderTest.rates.observeForever { }
    }

    private fun mockSchedulers() {
        `when`(schedulerProvider.ioScheduler()).thenReturn(Schedulers.trampoline())
        `when`(schedulerProvider.uiScheduler()).thenReturn(Schedulers.trampoline())
    }

    private fun mockContentProvider() {
        `when`(ratesContentProvider.getCurrencyIconResId(ArgumentMatchers.anyString())).thenReturn(1)
        `when`(ratesContentProvider.getCurrencyName(ArgumentMatchers.anyString())).thenReturn("name")
    }

    private fun createCurrencyRates() = listOf(
        CurrencyRate(
            currencyCode = "EUR",
            rate = 1f,
            isBaseCurrency = true
        ), CurrencyRate(
            currencyCode = "PLN",
            rate = 2f,
            isBaseCurrency = false
        )
    )
}