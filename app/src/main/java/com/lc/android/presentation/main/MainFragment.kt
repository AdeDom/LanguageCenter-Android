package com.lc.android.presentation.main

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.lc.android.R
import com.lc.android.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment(R.layout.fragment_main) {

    private val viewModel by viewModel<MainViewModel>()
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestServerAuthCode(getString(R.string.server_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        observeViewModel()
        viewEvent()
    }

    private fun observeViewModel() {
        viewModel.attachFirstTime.observe {
            viewModel.callFetchUserInfo()
        }

        viewModel.state.observe { state ->
            animationLoading.isVisible = state.isLoading
            textView.text = state.name
        }

        viewModel.error.observeError()
    }

    private fun viewEvent() {
        btSignOut.setOnClickListener {
            signOut()
        }
    }

    private fun signOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener(requireActivity()) {
            viewModel.signOut()
            findNavController().navigate(R.id.action_mainFragment_to_authActivity)
            activity?.finishAffinity()
        }
    }

}
