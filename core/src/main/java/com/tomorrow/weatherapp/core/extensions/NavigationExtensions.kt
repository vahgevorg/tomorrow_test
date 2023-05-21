package com.tomorrow.weatherapp.core.extensions

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.Navigator
import androidx.navigation.findNavController

fun <F : Fragment> F.navigate(
    navHostId: Int,
    destination: NavDirections,
    bundle: Navigator.Extras? = null,
) {
    val navController = requireActivity()
        .findNavController(navHostId)
    bundle?.let {
        navController
            .navigate(destination, it)
    } ?: navController
        .navigate(destination)
}