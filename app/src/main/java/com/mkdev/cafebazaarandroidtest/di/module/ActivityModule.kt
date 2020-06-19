package com.mkdev.cafebazaarandroidtest.di.module

import com.mkdev.cafebazaarandroidtest.ui.main.MainActivity
import com.mkdev.mvvmsample.di.scope.PerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    /**
     * Injects [MainActivity]
     *
     * @return an instance of [MainActivity]
     */

    @PerActivity
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    internal abstract fun mainActivity(): MainActivity
}
