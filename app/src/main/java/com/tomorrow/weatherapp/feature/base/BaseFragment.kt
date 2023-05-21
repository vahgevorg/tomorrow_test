package com.tomorrow.weatherapp.feature.base

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    abstract fun observeViewModelValues()

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeViewModelValues()
    }
}