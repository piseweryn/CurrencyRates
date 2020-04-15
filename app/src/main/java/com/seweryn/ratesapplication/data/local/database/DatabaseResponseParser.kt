package com.seweryn.ratesapplication.data.local.database

import com.seweryn.ratesapplication.data.model.CurrencyRate

class DatabaseResponseParser {
    companion object {
        fun parseCurrencyRate(
            rates: List<CurrencyRate>,
            baseCurrencyCode: String
        ): List<CurrencyRate> {
            val baseCurrency = rates.first { it.currencyCode == baseCurrencyCode }
            val list = rates.mapNotNull {
                if (it == baseCurrency) null
                else {
                    CurrencyRate(
                        currencyCode = it.currencyCode,
                        rate = it.rate / baseCurrency.rate,
                        isBaseCurrency = false
                    )
                }
            }.sortedBy { it.currencyCode }.toMutableList()

            list.add(0, baseCurrency.copy(rate = 1f, isBaseCurrency = true))
            return list
        }
    }
}