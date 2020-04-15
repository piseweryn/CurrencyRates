package com.seweryn.ratesapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrencyRate(
    @PrimaryKey val currencyCode: String,
    val rate: Float,
    val isBaseCurrency: Boolean = false
)
