package com.mkdev.cafebazaarandroidtest

import android.app.Application
import com.mkdev.cafebazaarandroidtest.di.AppInjector
import com.pixplicity.easyprefs.library.Prefs
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

class MyApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector() = androidInjector

    override fun onCreate() {
        super.onCreate()

        AppInjector.init(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        Prefs.Builder()
            .setContext(this)
            .setUseDefaultSharedPreference(true)
            .build()
    }
}