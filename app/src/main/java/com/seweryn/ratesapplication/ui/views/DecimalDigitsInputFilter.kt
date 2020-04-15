package com.seweryn.ratesapplication.ui.views

import android.text.InputFilter
import android.text.Spanned
import java.util.regex.Pattern

class DecimalDigitsInputFilter(private val maxDecimalDigits: Int) : InputFilter {

    private val pattern = Pattern.compile("\\d*[.,]?\\d{0,$maxDecimalDigits}")

    override fun filter(
        source: CharSequence?, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int
    ): CharSequence? {
        val newInput = dest?.substring(0, dstart) + source + dest?.substring(dend, dest.length)
        return if (pattern.matcher(newInput).matches()) null
        else ""
    }

}