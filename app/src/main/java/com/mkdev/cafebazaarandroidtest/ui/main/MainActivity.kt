package com.mkdev.cafebazaarandroidtest.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.common.api.Status
import com.mkdev.cafebazaarandroidtest.R
import com.mkdev.cafebazaarandroidtest.core.BaseActivity
import com.mkdev.cafebazaarandroidtest.databinding.ActivityMainBinding
import com.mkdev.cafebazaarandroidtest.utils.ActivityResultCallback
import com.mkdev.cafebazaarandroidtest.utils.extensions.hide
import com.mkdev.cafebazaarandroidtest.utils.extensions.show
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainActivity : BaseActivity<MainActivityViewModel, ActivityMainBinding>
    (MainActivityViewModel::class.java), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>
    private lateinit var activityResultCallback: ActivityResultCallback

    override fun androidInjector() = androidInjector

    override fun initViewModel(viewModel: MainActivityViewModel) {
        binding.viewModel = viewModel
    }

    override fun getLayoutRes() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)

        setupNavigation()
    }

    private fun setupNavigation() {
        val navController = findNavController(R.id.container_fragment)
        setupActionBarWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashFragment -> {
                    binding.appBarLayout.hide()
                }
                R.id.locationsFragment -> {
                    setupToolbarTitleAndAction(getString(R.string.locations), false)
                }
                R.id.locationDetailFragment -> {
                    setupToolbarTitleAndAction(getString(R.string.details), true)
                }
            }
        }
    }

    private fun setupToolbarTitleAndAction(title: String, isDisplayHomeAsUp: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(isDisplayHomeAsUp)
        viewModel.toolbarTitle.set(title)
        binding.appBarLayout.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.container_fragment).navigateUp()
    }

    fun requestActivityResult(
        activityResultCode: Int,
        resolvableApiException: ResolvableApiException? = null,
        intent: Intent? = null,
        activityResultCallback: ActivityResultCallback
    ) {
        this.activityResultCallback = activityResultCallback
        if (resolvableApiException != null)
            resolvableApiException.startResolutionForResult(this, activityResultCode)
        else
            startActivityForResult(intent, activityResultCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        activityResultCallback.onResult(requestCode, resultCode, data)
    }
}
