package com.seweryn.ratesapplication.viewmodel.rates

import androidx.lifecycle.MutableLiveData
import com.seweryn.ratesapplication.R
import com.seweryn.ratesapplication.data.RatesRepository
import com.seweryn.ratesapplication.data.model.CurrencyRate
import com.seweryn.ratesapplication.utils.SchedulerProvider
import com.seweryn.ratesapplication.viewmodel.BaseViewModel
import javax.inject.Inject

class RatesViewModel @Inject constructor(
    private val ratesRepository: RatesRepository,
    schedulerProvider: SchedulerProvider
) : BaseViewModel(schedulerProvider) {

    var rates: MutableLiveData<List<RateWrapper>> = MutableLiveData()

    private var currentBaseAmount: Float = 1f

    fun run() {
        pollRates()
    }

    private fun pollRates(baseCurrencyCode: String = "EUR") {
        clearSubscriptions()
        poll(
            command = ratesRepository.getRates(baseCurrencyCode),
            intervalInSeconds = 1,
            onNext = { response ->
                rates.value = response.map {
                    RateWrapper(
                        currencyName = it.currencyCode,
                        currentDisplayRate = (it.rate * currentBaseAmount).toString(),
                        currencyIconResId = R.drawable.ic_eur_round,
                        currencyRate = it,
                        selectAction = { amount ->
                            currentBaseAmount = amount
                            pollRates(it.currencyCode)
                        }
                    )
                }
            },
            onError = {},
            onComplete = {}
        )

    }

    data class RateWrapper(
        val currentDisplayRate: String,
        val currencyName: String,
        val currencyIconResId: Int,
        val currencyRate: CurrencyRate,
        val selectAction: (currentAmount: Float) -> Unit
    )
}