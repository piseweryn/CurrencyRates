package com.seweryn.ratesapplication.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.seweryn.ratesapplication.data.model.CurrencyRate
import io.reactivex.Observable

@Dao
interface CurrencyRateDao {
    @Query("SELECT * FROM currencyrate")
    fun queryUsers(): Observable<List<CurrencyRate>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(events: List<CurrencyRate>)
}