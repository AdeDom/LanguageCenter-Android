package com.lc.android.presentation.signin

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.lc.android.R
import com.lc.android.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_sign_in.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInFragment : BaseFragment(R.layout.fragment_sign_in) {

    private val viewModel by viewModel<SignInViewModel>()
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestServerAuthCode(getString(R.string.server_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        observeViewModel()
        viewEvent()
    }

    private fun observeViewModel() {
        viewModel.state.observe { state->
            animationLoading.isVisible = state.isLoading
            signInButton.isClickable = state.isClickable
        }

        viewModel.signInEvent.observe { response ->
            if (response.success) {
                findNavController().navigate(R.id.action_global_splashScreenFragment)
                activity?.finishAffinity()
            }
        }

        viewModel.error.observeError()
    }

    private fun viewEvent() {
        signInButton.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_GET_AUTH_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_GET_AUTH_CODE) {
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)

                val account = task.getResult(ApiException::class.java)
                viewModel.callSignIn(account?.serverAuthCode)
            } catch (e: ApiException) {
            }
        }
    }

    companion object {
        private const val RC_GET_AUTH_CODE = 9001
    }

}
