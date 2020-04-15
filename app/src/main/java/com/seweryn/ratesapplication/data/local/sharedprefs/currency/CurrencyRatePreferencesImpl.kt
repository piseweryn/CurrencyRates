package com.seweryn.ratesapplication.data.local.sharedprefs.currency

import android.content.Context
import com.seweryn.ratesapplication.data.local.sharedprefs.SharedPrefs

class CurrencyRatePreferencesImpl(context: Context) : SharedPrefs(context),
    CurrencyRatePreferences {
    private val PREFERENCE_KEY = "currencyPrefs"
    private val CURRENCY_CODE = "currencyCode"

    override fun preferenceKey() = PREFERENCE_KEY

    override fun getCurrencyCode(): String? {
        return getString(CURRENCY_CODE)
    }

    override fun rememberCurrencyCode(currencyCode: String) {
        putString(CURRENCY_CODE, currencyCode)
    }

}