package com.seweryn.ratesapplication.data.local.sharedprefs.currency

interface CurrencyRatePreferences {
    fun getCurrencyCode(): String?

    fun rememberCurrencyCode(currencyCode: String)
}