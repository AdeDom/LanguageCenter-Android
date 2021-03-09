package com.lc.android.presentation.splashscreen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
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

        initialView()
        viewModelObserve()
        viewEvent()
    }

    private fun initialView() {
        val fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        ivLogo.startAnimation(fadeIn)
        tvLanguageCenter.startAnimation(fadeIn)
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

    private fun viewEvent() {
        tvDevBy.setOnClickListener {
            Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://github.com/adedom")
                startActivity(this)
            }
        }
    }

}
