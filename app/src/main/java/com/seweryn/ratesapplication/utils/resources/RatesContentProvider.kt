package com.seweryn.ratesapplication.utils.resources

interface RatesContentProvider {

    fun getCurrencyName(currencyCode: String): String

    fun getCurrencyIconResId(currencyCode: String): Int

}