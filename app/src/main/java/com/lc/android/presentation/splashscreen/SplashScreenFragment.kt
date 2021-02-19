package com.lc.android.presentation.splashscreen

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.lc.android.R
import com.lc.android.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_splash_screen.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashScreenFragment : BaseFragment(R.layout.fragment_splash_screen) {

    private val viewModel by viewModel<SplashScreenViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelObserve()
    }

    private fun viewModelObserve() {
        viewModel.attachFirstTime.observe {
            if (viewModel.isValidateSignIn()) {
                viewModel.callValidateToken()
            } else {
                findNavController().navigate(R.id.action_splashScreenFragment_to_signInFragment)
            }
        }

        viewModel.state.observe { state ->
            animationLoading.isVisible = state.isLoading
        }

        viewModel.validateToken.observe { response ->
            if (response.success) {
                findNavController().navigate(R.id.action_splashScreenFragment_to_mainActivity)
                activity?.finishAffinity()
            } else {
                findNavController().navigate(R.id.action_splashScreenFragment_to_signInFragment)
            }
        }

        viewModel.error.observeError()
    }

}
