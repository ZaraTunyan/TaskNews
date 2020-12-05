package com.nytimes.task.utils

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nytimes.task.features.home.HomeFragmentDirections


fun Fragment.navigateToNewsDetails(id: Long) {
    val directions = HomeFragmentDirections.actionNavNewsDetails(id)
    findNavController().navigate(directions)
}
