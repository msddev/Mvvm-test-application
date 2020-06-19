package com.mkdev.cafebazaarandroidtest.di.module

import com.mkdev.cafebazaarandroidtest.ui.details.LocationDetailsFragment
import com.mkdev.cafebazaarandroidtest.ui.locations.LocationsFragment
import com.mkdev.cafebazaarandroidtest.ui.splash.SplashFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeSplashFragment(): SplashFragment

    @ContributesAndroidInjector
    abstract fun contributeLocationsFragment(): LocationsFragment

    @ContributesAndroidInjector
    abstract fun contributeLocationDetailsFragment(): LocationDetailsFragment
}
