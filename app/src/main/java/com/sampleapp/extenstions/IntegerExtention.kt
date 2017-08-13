package com.sampleapp.extenstions

import java.text.NumberFormat
import java.util.*

/**
 * Created by saveen_dhiman on 13-Aug-17.
 */

fun Int.inTwoDigitFormat(): String {
    if (this < 10)
        return String.format("%02d", this)
    else
        return this.toString()
}

fun Int.formatNumberWithComma() : String{
    return NumberFormat.getNumberInstance(Locale.US).format(this);
}