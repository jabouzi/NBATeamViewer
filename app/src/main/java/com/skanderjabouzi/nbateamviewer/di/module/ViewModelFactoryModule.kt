package com.skanderjabouzi.nbateamviewer.di.modules

import androidx.lifecycle.ViewModelProvider
import com.skanderjabouzi.nbateamviewer.presentation.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class ViewModelFactoryModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}