package com.mkdev.cafebazaarandroidtest.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mkdev.cafebazaarandroidtest.di.ViewModelFactory
import com.mkdev.cafebazaarandroidtest.di.key.ViewModelKey
import com.mkdev.cafebazaarandroidtest.ui.details.LocationDetailFragmentViewModel
import com.mkdev.cafebazaarandroidtest.ui.locations.LocationsFragmentViewModel
import com.mkdev.cafebazaarandroidtest.ui.locations.result.LocationResultViewModel
import com.mkdev.cafebazaarandroidtest.ui.main.MainActivityViewModel
import com.mkdev.cafebazaarandroidtest.ui.splash.SplashFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @IntoMap
    @Binds
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun provideMainActivityViewModel(mainActivityViewModel: MainActivityViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(SplashFragmentViewModel::class)
    abstract fun provideSplashFragmentViewModel(splashFragmentViewModel: SplashFragmentViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(LocationsFragmentViewModel::class)
    abstract fun provideLocationsFragmentViewModel(locationsFragmentViewModel: LocationsFragmentViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(LocationResultViewModel::class)
    abstract fun provideLocationResultViewModel(locationResultViewModel: LocationResultViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(LocationDetailFragmentViewModel::class)
    abstract fun provideLocationDetailFragmentViewModel(locationDetailFragmentViewModel: LocationDetailFragmentViewModel): ViewModel
}
