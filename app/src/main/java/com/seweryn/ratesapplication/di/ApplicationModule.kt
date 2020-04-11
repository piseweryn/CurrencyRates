package com.seweryn.ratesapplication.di

import android.app.Application
import android.content.Context
import com.seweryn.ratesapplication.data.RatesRepository
import com.seweryn.ratesapplication.data.RatesRepositoryImpl
import com.seweryn.ratesapplication.data.remote.RatesApi
import com.seweryn.ratesapplication.utils.SchedulerProvider
import com.seweryn.ratesapplication.utils.SchedulerProviderImpl
import com.seweryn.ratesapplication.utils.network.ConnectionManager
import com.seweryn.ratesapplication.utils.network.ConnectionManagerImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideConnectionManager(): ConnectionManager = ConnectionManagerImpl(application)

    @Provides
    @Singleton
    fun provideSchedulerProvider(): SchedulerProvider = SchedulerProviderImpl()

    @Provides
    @Singleton
    fun provideRatesRepository(apiInterface: RatesApi): RatesRepository = RatesRepositoryImpl(apiInterface)
}

