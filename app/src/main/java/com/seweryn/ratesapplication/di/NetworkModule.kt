package com.seweryn.ratesapplication.di

import com.seweryn.ratesapplication.data.Constants
import com.seweryn.ratesapplication.data.remote.RatesApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule  {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    @Singleton
    @Named("rates")
    fun provideEventsApiClient(okHttpClient: OkHttpClient): Retrofit {
        return buildRetrofitClient(
            okHttpClient,
            Constants.RATES_BASE_URL
        )
    }

    @Provides
    @Singleton
    fun provideEventsApiInterface(@Named("rates") retrofit: Retrofit): RatesApi {
        return retrofit.create(RatesApi::class.java)
    }

    private fun buildRetrofitClient(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder().client(okHttpClient).baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}