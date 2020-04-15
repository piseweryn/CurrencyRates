package com.seweryn.ratesapplication.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.seweryn.ratesapplication.data.RatesRepository
import com.seweryn.ratesapplication.data.local.sharedprefs.currency.CurrencyRatePreferences
import com.seweryn.ratesapplication.utils.SchedulerProvider
import com.seweryn.ratesapplication.utils.resources.RatesContentProvider
import com.seweryn.ratesapplication.viewmodel.rates.RatesViewModel
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule  {

    @Provides
    fun provideRatesViewModelFactory(
        ratesRepository: RatesRepository,
        ratesContentProvider: RatesContentProvider,
        currencyRatePreferences: CurrencyRatePreferences,
        schedulerProvider: SchedulerProvider
    ) = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RatesViewModel(
                ratesRepository,
                ratesContentProvider,
                currencyRatePreferences,
                schedulerProvider
            ) as T
        }

    }
}