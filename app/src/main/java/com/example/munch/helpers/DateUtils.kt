package com.example.munch.helpers

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateUtils {
    fun LocalDate.getFormatted() : String{
        val formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy")
        return this.format(formatter)
    }
}