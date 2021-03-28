package com.skanderjabouzi.nbateamviewer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory (private val viewModels: MutableMap<Class<out ViewModel>,
        ViewModel>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModels[modelClass] as T
}