package com.seweryn.ratesapplication.data

import com.seweryn.ratesapplication.data.model.CurrencyRate
import com.seweryn.ratesapplication.data.remote.RatesApi
import io.reactivex.Observable

class RatesRepositoryImpl(private val apiInterface: RatesApi) : RatesRepository {
    override fun getRates(baseCurrencyCode: String): Observable<List<CurrencyRate>> {
        return apiInterface.getRates(baseCurrencyCode).map {
            RatesResponseParser.parseResponse(it)
        }
    }

}