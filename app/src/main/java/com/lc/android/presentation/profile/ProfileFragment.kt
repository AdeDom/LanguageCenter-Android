package com.lc.android.presentation.profile

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.lc.android.R
import com.lc.android.base.BaseFragment
import com.lc.android.util.load
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    private val viewModel by viewModel<ProfileViewModel>()
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
        }

        viewModel.getDbUserInfoLiveData.observe(viewLifecycleOwner, { userInfo ->
            if (userInfo == null) return@observe

            tvName.text = getString(R.string.name, userInfo.name)
            ivPicture.load(userInfo.picture)
        })

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
            findNavController().navigate(R.id.action_global_authActivity)
            activity?.finishAffinity()
        }
    }

}
