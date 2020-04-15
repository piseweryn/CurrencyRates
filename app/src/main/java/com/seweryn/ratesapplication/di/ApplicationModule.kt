package com.seweryn.ratesapplication.di

import android.app.Application
import androidx.room.Room
import com.seweryn.ratesapplication.data.Constants
import com.seweryn.ratesapplication.data.RatesRepository
import com.seweryn.ratesapplication.data.RatesRepositoryImpl
import com.seweryn.ratesapplication.data.local.database.Database
import com.seweryn.ratesapplication.data.local.sharedprefs.currency.CurrencyRatePreferences
import com.seweryn.ratesapplication.data.local.sharedprefs.currency.CurrencyRatePreferencesImpl
import com.seweryn.ratesapplication.data.remote.RatesApi
import com.seweryn.ratesapplication.utils.SchedulerProvider
import com.seweryn.ratesapplication.utils.SchedulerProviderImpl
import com.seweryn.ratesapplication.utils.resources.RatesContentProvider
import com.seweryn.ratesapplication.utils.resources.RatesContentProviderImpl
import com.seweryn.ratesapplication.utils.resources.ResourceWrapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideSchedulerProvider(): SchedulerProvider = SchedulerProviderImpl()

    @Provides
    @Singleton
    fun provideRatesRepository(apiInterface: RatesApi, database: Database): RatesRepository =
        RatesRepositoryImpl(apiInterface, database)

    @Provides
    @Singleton
    fun provideEventsDatabase(): Database = Room.databaseBuilder(
        application,
        Database::class.java, Constants.RATES_DATABASE_NAME
    ).fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideResourceWrapper(): ResourceWrapper = ResourceWrapper(application)

    @Provides
    @Singleton
    fun provideRatesContentProvider(resourceWrapper: ResourceWrapper): RatesContentProvider =
        RatesContentProviderImpl(application.resources, resourceWrapper)

    @Provides
    @Singleton
    fun provideCurrencyRatesPreferences(): CurrencyRatePreferences =
        CurrencyRatePreferencesImpl(application)
}

