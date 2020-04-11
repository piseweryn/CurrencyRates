package com.seweryn.ratesapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.seweryn.ratesapplication.utils.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

open class BaseViewModel(private val schedulerProvider: SchedulerProvider) : ViewModel() {

    private val subscriptions = CompositeDisposable()

    fun onDestroy() = clearSubscriptions()

    protected fun clearSubscriptions() = subscriptions.clear()

    protected fun <T> poll(
        command: Observable<T>,
        intervalInSeconds: Long,
        onNext: (T) -> Unit,
        onError: (Throwable) -> Unit,
        onComplete: () -> Unit = {}
    ) {
        subscriptions.add(
            command.subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.uiScheduler(), true)
                .repeatWhen { completed -> completed.delay(intervalInSeconds, TimeUnit.SECONDS) }
                .subscribe(
                    { result -> onNext.invoke(result) },
                    { error -> onError.invoke(error) },
                    { onComplete.invoke() })
        )
    }
}