package com.tomorrow.weatherapp.core.formatter

import com.tomorrow.weatherapp.core.common.Constants.DATE_FORMAT_FULL_DATE
import com.tomorrow.weatherapp.core.common.Constants.DATE_FORMAT_ISO_8601
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateTimeFormatter {

    fun formatIso8601ToFullDateTime(iso8601Time: String): String {
        val inputFormat = SimpleDateFormat(DATE_FORMAT_ISO_8601, Locale.getDefault())
        val outputFormat = SimpleDateFormat(DATE_FORMAT_FULL_DATE, Locale.getDefault())

        try {
            val date: Date? = inputFormat.parse(iso8601Time)
            if (date != null) {
                return outputFormat.format(date)
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return ""
    }
}