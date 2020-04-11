package com.seweryn.ratesapplication.data.model

data class RatesResponse(
    val baseCurrency: String,
    val rates: RateResponseObject
)