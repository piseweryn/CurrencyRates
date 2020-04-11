package com.seweryn.ratesapplication.utils

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun ioScheduler(): Scheduler

    fun uiScheduler(): Scheduler
}