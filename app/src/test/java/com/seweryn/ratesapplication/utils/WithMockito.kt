package com.seweryn.ratesapplication.utils

import org.mockito.ArgumentMatchers

interface WithMockito {
    fun <T> any(): T = ArgumentMatchers.any<T>()
}