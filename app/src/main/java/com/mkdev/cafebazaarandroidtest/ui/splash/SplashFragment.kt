package com.mkdev.cafebazaarandroidtest.ui.splash

import androidx.navigation.fragment.findNavController
import com.mkdev.cafebazaarandroidtest.R
import com.mkdev.cafebazaarandroidtest.core.BaseFragment
import com.mkdev.cafebazaarandroidtest.databinding.FragmentSplashBinding
import com.mkdev.cafebazaarandroidtest.di.Injectable
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import java.util.concurrent.TimeUnit

class SplashFragment : BaseFragment<SplashFragmentViewModel, FragmentSplashBinding>
    (SplashFragmentViewModel::class.java), Injectable {

    var disposable: Disposable? = null

    override fun getLayoutRes() = R.layout.fragment_splash

    override fun initViewModel() {
        mBinding.viewModel = viewModel
    }

    override fun init() {
        super.init()

        disposable = Completable.timer(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
            .subscribe({
                showNextActivity()
            }, {})
    }

    private fun showNextActivity() {
        findNavController().graph.startDestination = R.id.locationsFragment

        findNavController().navigate(
            SplashFragmentDirections.actionSplashFragmentToLocationsFragment()
        )
    }

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }
}
