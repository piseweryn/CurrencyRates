package com.seweryn.ratesapplication.data.remote

import com.seweryn.ratesapplication.data.Currencies
import com.seweryn.ratesapplication.data.model.CurrencyRate
import com.seweryn.ratesapplication.data.model.RatesResponse

class RatesResponseParser {
    companion object {
        fun parseResponse(response: RatesResponse): List<CurrencyRate> {
            return mutableListOf<CurrencyRate>().apply {
                add(CurrencyRate(Currencies.AUD.name, response.rates.AUD))
                add(CurrencyRate(Currencies.BGN.name, response.rates.BGN))
                add(CurrencyRate(Currencies.BRL.name, response.rates.BRL))
                add(CurrencyRate(Currencies.CAD.name, response.rates.CAD))
                add(CurrencyRate(Currencies.CHF.name, response.rates.CHF))
                add(CurrencyRate(Currencies.CNY.name, response.rates.CNY))
                add(CurrencyRate(Currencies.CZK.name, response.rates.CZK))
                add(CurrencyRate(Currencies.DKK.name, response.rates.DKK))
                add(CurrencyRate(Currencies.EUR.name, response.rates.EUR))
                add(CurrencyRate(Currencies.GBP.name, response.rates.GBP))
                add(CurrencyRate(Currencies.HKD.name, response.rates.HKD))
                add(CurrencyRate(Currencies.HRK.name, response.rates.HRK))
                add(CurrencyRate(Currencies.HUF.name, response.rates.HUF))
                add(CurrencyRate(Currencies.IDR.name, response.rates.IDR))
                add(CurrencyRate(Currencies.ILS.name, response.rates.ILS))
                add(CurrencyRate(Currencies.INR.name, response.rates.INR))
                add(CurrencyRate(Currencies.ISK.name, response.rates.ISK))
                add(CurrencyRate(Currencies.JPY.name, response.rates.JPY))
                add(CurrencyRate(Currencies.KRW.name, response.rates.KRW))
                add(CurrencyRate(Currencies.MXN.name, response.rates.MXN))
                add(CurrencyRate(Currencies.MYR.name, response.rates.MYR))
                add(CurrencyRate(Currencies.NOK.name, response.rates.NOK))
                add(CurrencyRate(Currencies.NZD.name, response.rates.NZD))
                add(CurrencyRate(Currencies.PHP.name, response.rates.PHP))
                add(CurrencyRate(Currencies.PLN.name, response.rates.PLN))
                add(CurrencyRate(Currencies.RON.name, response.rates.RON))
                add(CurrencyRate(Currencies.RUB.name, response.rates.RUB))
                add(CurrencyRate(Currencies.SEK.name, response.rates.SEK))
                add(CurrencyRate(Currencies.SGD.name, response.rates.SGD))
                add(CurrencyRate(Currencies.THB.name, response.rates.THB))
                add(CurrencyRate(Currencies.USD.name, response.rates.USD))
                add(CurrencyRate(Currencies.ZAR.name, response.rates.ZAR))

                val baseCurrency = response.baseCurrency.toUpperCase()
                remove(find { it.currencyCode == baseCurrency })

                add(0, CurrencyRate(baseCurrency, 1f, true))

            }
        }
    }
}