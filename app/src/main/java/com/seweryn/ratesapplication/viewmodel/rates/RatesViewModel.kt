package com.seweryn.ratesapplication.viewmodel.rates

import androidx.lifecycle.MutableLiveData
import com.seweryn.ratesapplication.data.RatesRepository
import com.seweryn.ratesapplication.data.local.sharedprefs.currency.CurrencyRatePreferences
import com.seweryn.ratesapplication.data.model.CurrencyRate
import com.seweryn.ratesapplication.utils.SchedulerProvider
import com.seweryn.ratesapplication.utils.extensions.toFloatSafely
import com.seweryn.ratesapplication.utils.resources.RatesContentProvider
import com.seweryn.ratesapplication.viewmodel.BaseViewModel
import java.text.DecimalFormat
import javax.inject.Inject

class RatesViewModel @Inject constructor(
    private val ratesRepository: RatesRepository,
    private val ratesContentProvider: RatesContentProvider,
    private val currencyRatePreferences: CurrencyRatePreferences,
    schedulerProvider: SchedulerProvider
) : BaseViewModel(schedulerProvider) {

    val rates: MutableLiveData<List<RateWrapper>> = MutableLiveData()
    val error: MutableLiveData<Error?> = MutableLiveData()


    private var baseCurrencyCode = "EUR"
    private var currentBaseAmount: Float = 1f

    init {
        currencyRatePreferences.getCurrencyCode()?.let { baseCurrencyCode = it }
    }

    override fun start() {
        pollRates()
    }

    private fun pollRates() {
        clearSubscriptions()
        poll(
            command = ratesRepository.getRates(baseCurrencyCode),
            intervalInSeconds = 1,
            onNext = { response ->
                error.value = null
                rates.value = response.map {
                    RateWrapper(
                        currencyName = ratesContentProvider.getCurrencyName(it.currencyCode),
                        currentDisplayRate = prepareAmountDisplay(it),
                        currencyIconResId = ratesContentProvider.getCurrencyIconResId(it.currencyCode),
                        currencyRate = it,
                        amountEditAction = createEditAction(it),
                        selectAction = { amount ->
                            baseCurrencyCode = it.currencyCode
                            currencyRatePreferences.rememberCurrencyCode(it.currencyCode)
                            currentBaseAmount = parseAmount(amount)
                            pollRates()
                        }
                    )
                }
            },
            onError = {
                error.value = Error {
                    error.value = null
                    pollRates()
                }
            },
            onComplete = {}
        )

    }

    private fun createEditAction(currencyRate: CurrencyRate): (String) -> Unit {
        return { newAmount ->
            if (baseCurrencyCode == currencyRate.currencyCode){
                currentBaseAmount = parseAmount(newAmount)
                updateRates()
            }
        }
    }

    private fun updateRates() {
        rates.value = rates.value?.map {
            it.copy(currentDisplayRate = prepareAmountDisplay(it.currencyRate))
        }
    }

    private fun parseAmount(amount: String): Float {
        return if (amount.isEmpty()) 0f else amount.toFloatSafely()
    }

    private fun calculateAmount(currencyRate: CurrencyRate): Float {
        return if (currencyRate.isBaseCurrency) currentBaseAmount
        else currencyRate.rate * currentBaseAmount
    }

    private fun prepareAmountDisplay(currencyRate: CurrencyRate): String {
        val calculatedAmount = calculateAmount(currencyRate)
        return DecimalFormat("#.##").format(calculatedAmount)
    }

    data class RateWrapper(
        val currentDisplayRate: String,
        val currencyName: String,
        val currencyIconResId: Int,
        val currencyRate: CurrencyRate,
        val selectAction: (currentAmount: String) -> Unit,
        val amountEditAction: (newAmount: String) -> Unit
    )

    data class Error(
        val retryAction: () -> Unit
    )
}