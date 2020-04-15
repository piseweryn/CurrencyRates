package com.seweryn.ratesapplication.data

import com.seweryn.ratesapplication.data.local.database.CurrencyRateDao
import com.seweryn.ratesapplication.data.local.database.Database
import com.seweryn.ratesapplication.data.model.RateResponseObject
import com.seweryn.ratesapplication.data.model.RatesResponse
import com.seweryn.ratesapplication.data.remote.RatesApi
import com.seweryn.ratesapplication.utils.WithMockito
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class RatesRepositoryImplTest : WithMockito {

    private lateinit var systemUnderTest: RatesRepositoryImpl

    @Mock
    lateinit var api: RatesApi

    @Mock
    lateinit var database: Database

    @Mock
    lateinit var currencyDao: CurrencyRateDao

    @Before
    fun setUp() {
        systemUnderTest = RatesRepositoryImpl(api, database)
        mockDatabase()
    }

    @Test
    fun `should fetch rates from api`() {
        simulateRatesCanBeFetchedFromApi()
        systemUnderTest.getRates("code").test()

        verify(api).getRates("code")
    }

    @Test
    fun `should save currencies to database after successful api request`() {
        simulateRatesCanBeFetchedFromApi()
        systemUnderTest.getRates("code").test()

        verify(currencyDao).insertUsers(any())
    }

    @Test
    fun `should load rates from database on api error`() {
        simulateRatesCanNotBeFetchedFromApi()
        systemUnderTest.getRates("code").test()

        verify(currencyDao).queryUsers()
    }

    private fun simulateRatesCanBeFetchedFromApi() {
        `when`(api.getRates(ArgumentMatchers.anyString())).thenReturn(
            Observable.just(
                createRatesResponse()
            )
        )
    }

    private fun simulateRatesCanNotBeFetchedFromApi() {
        `when`(api.getRates(ArgumentMatchers.anyString())).thenReturn(Observable.error(Exception()))
    }

    private fun mockDatabase() {
        `when`(database.currencyRateDao()).thenReturn(currencyDao)
    }

    private fun createRatesResponse() =
        RatesResponse(
            baseCurrency = "EUR",
            rates = RateResponseObject(
                1f,
                1f,
                1f,
                1f,
                1f,
                1f,
                1f,
                1f,
                1f,
                1f,
                1f,
                1f,
                1f,
                1f,
                1f,
                1f,
                1f,
                1f,
                1f,
                1f,
                1f,
                1f,
                1f,
                1f,
                1f,
                1f,
                1f,
                1f,
                1f,
                1f,
                1f,
                1f
            )
        )
}