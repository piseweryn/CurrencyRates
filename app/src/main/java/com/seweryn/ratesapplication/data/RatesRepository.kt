package com.seweryn.ratesapplication.data

import com.seweryn.ratesapplication.data.model.CurrencyRate
import io.reactivex.Observable

interface RatesRepository {
    fun getRates(baseCurrencyCode: String): Observable<List<CurrencyRate>>
}