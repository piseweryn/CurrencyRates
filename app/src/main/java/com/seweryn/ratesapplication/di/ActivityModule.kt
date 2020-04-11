package com.seweryn.ratesapplication.di

import com.seweryn.ratesapplication.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule  {
    @ContributesAndroidInjector
    abstract fun contributeEventsFragment(): MainActivity
}