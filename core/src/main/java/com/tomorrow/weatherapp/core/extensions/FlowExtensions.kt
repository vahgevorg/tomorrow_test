package com.tomorrow.weatherapp.core.extensions

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

fun <T> Flow<T>.flowOnBackground(): Flow<T> = flowOn(Dispatchers.Default)

fun <T> Flow<T>.flowOnUIImmediate(): Flow<T> = flowOn(Dispatchers.Main.immediate)
