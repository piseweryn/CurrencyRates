package com.seweryn.ratesapplication.data

import com.seweryn.ratesapplication.data.local.database.Database
import com.seweryn.ratesapplication.data.local.database.DatabaseResponseParser
import com.seweryn.ratesapplication.data.model.CurrencyRate
import com.seweryn.ratesapplication.data.remote.RatesApi
import com.seweryn.ratesapplication.data.remote.RatesResponseParser
import io.reactivex.Observable

class RatesRepositoryImpl(private val apiInterface: RatesApi, private val database: Database) :
    RatesRepository {
    override fun getRates(baseCurrencyCode: String): Observable<List<CurrencyRate>> {
        return getRatesFromApi(baseCurrencyCode)
            .onErrorResumeNext { throwable: Throwable ->
                getRatesFromDatabase(
                    baseCurrencyCode
                )
            }
    }

    private fun getRatesFromApi(baseCurrencyCode: String): Observable<List<CurrencyRate>> {
        return apiInterface.getRates(baseCurrencyCode).map {
            RatesResponseParser.parseResponse(it)
        }.doOnNext { database.currencyRateDao().insertUsers(it) }
    }

    private fun getRatesFromDatabase(baseCurrencyCode: String): Observable<List<CurrencyRate>> {
        return database.currencyRateDao().queryUsers().map {
            DatabaseResponseParser.parseCurrencyRate(it, baseCurrencyCode)
        }
    }

}