package com.example.munch.helpers

import java.text.NumberFormat
import java.util.Locale

object CurrencyUtils {
    fun Long.toRupiah():String{
        val numberFormat = NumberFormat.getCurrencyInstance(Locale("in","ID"))
        return numberFormat.format(this)
    }
}