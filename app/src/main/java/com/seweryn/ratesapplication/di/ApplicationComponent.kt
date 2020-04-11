package com.seweryn.ratesapplication.di

import com.seweryn.ratesapplication.App
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, ApplicationModule::class, NetworkModule::class, ActivityModule::class, ViewModelModule::class])
interface ApplicationComponent : AndroidInjector<App>