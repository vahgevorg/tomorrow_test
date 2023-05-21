package com.tomorrow.weatherapp.core.mapper

interface Mapper<SOURCE, RESULT> {
    fun map(from: SOURCE): RESULT
}