package com.seweryn.ratesapplication.data.remote

import com.seweryn.ratesapplication.data.model.RatesResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RatesApi {
    @GET("latest")
    fun getRates(@Query("base") baseCurrencyCode: String): Observable<RatesResponse>
}