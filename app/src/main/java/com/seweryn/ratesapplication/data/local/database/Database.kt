package com.seweryn.ratesapplication.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.seweryn.ratesapplication.data.model.CurrencyRate

@Database(entities = [CurrencyRate::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun currencyRateDao(): CurrencyRateDao
}