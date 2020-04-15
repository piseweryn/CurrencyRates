package com.seweryn.ratesapplication.utils.extensions

fun String.toFloatSafely(): Float {
    return this.replace(",", ".").toFloat()
}