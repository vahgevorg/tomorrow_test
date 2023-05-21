package com.tomorrow.weatherapp.core.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.asImmutable(): LiveData<T> = this