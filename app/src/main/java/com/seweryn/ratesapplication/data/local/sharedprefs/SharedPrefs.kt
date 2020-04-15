package com.seweryn.ratesapplication.data.local.sharedprefs

import android.content.Context

abstract class SharedPrefs(private val context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences(preferenceKey(), Context.MODE_PRIVATE)

    protected abstract fun preferenceKey(): String

    protected fun putString(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    protected fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }
}