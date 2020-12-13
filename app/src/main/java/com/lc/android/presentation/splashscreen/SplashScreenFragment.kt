package com.lc.android.presentation.splashscreen

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.lc.android.R
import com.lc.android.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashScreenFragment : BaseFragment(R.layout.fragment_splash_screen) {

    private val viewModel by viewModel<SplashScreenViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.attachFirstTime.observe {
            if (viewModel.isValidateSignIn()) {
                findNavController().navigate(R.id.action_splashScreenFragment_to_mainActivity)
                activity?.finishAffinity()
            } else {
                findNavController().navigate(R.id.action_splashScreenFragment_to_signInFragment)
            }
        }
    }

}
