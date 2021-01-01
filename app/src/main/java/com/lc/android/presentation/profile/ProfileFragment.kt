package com.lc.android.presentation.profile

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
    private val mLocalNativeAdapter by lazy { ProfileLocaleAdapter() }
    private val mLocalLearningAdapter by lazy { ProfileLocaleAdapter() }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestServerAuthCode(getString(R.string.server_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        initialView()
        observeViewModel()
        viewEvent()
    }

    private fun initialView() {
        recyclerViewLocalNatives.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mLocalNativeAdapter
        }

        recyclerViewLocalLearnings.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mLocalLearningAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe { state ->
            animationLoading.isVisible = state.isLoading
        }

        viewModel.getDbUserInfoLiveData.observe(viewLifecycleOwner, { userInfo ->
            if (userInfo == null) return@observe

            val (_, email, givenName, familyName, _, picture, gender, birthDateString, _, _, aboutMe, _, _, localNatives, localLearnings) = userInfo

            // set visibility widget
            if (email.isNullOrBlank()) tvEmail.visibility = View.GONE
            if (givenName.isNullOrBlank() && familyName.isNullOrBlank())
                tvName.visibility = View.GONE
            if (picture.isNullOrBlank()) ivPicture.visibility = View.GONE
            if (gender.isNullOrBlank()) tvGender.visibility = View.GONE
            if (birthDateString.isNullOrBlank()) tvBirthDate.visibility = View.GONE
            if (aboutMe.isNullOrBlank()) tvAboutMe.visibility = View.GONE

            // set value to widget
            if (givenName != null && familyName != null) {
                val name = "$givenName $familyName"
                tvName.text = getString(R.string.str_name, name)
            }
            email?.let { tvEmail.text = getString(R.string.str_email, email) }
            gender?.let { tvGender.text = getString(R.string.str_gender, gender) }
            birthDateString?.let {
                tvBirthDate.text = getString(R.string.str_birth_date, birthDateString)
            }
            aboutMe?.let { tvAboutMe.text = getString(R.string.str_about_me, aboutMe) }
            ivPicture.load(picture)

            mLocalNativeAdapter.setList(localNatives)
            mLocalLearningAdapter.setList(localLearnings)
        })

        viewModel.error.observeError()
    }

    private fun viewEvent() {
        btSignOut.setOnClickListener {
            dialogSignOut()
        }

        ivEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileActivity)
        }
    }

    private fun dialogSignOut() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(R.string.sign_out)
            setMessage(R.string.dialog_sign_out_message)
            setPositiveButton(android.R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            setNegativeButton(android.R.string.ok) { _, _ ->
                signOut()
            }
            show()
        }
    }

    private fun signOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener(requireActivity()) {
            viewModel.signOut()
            findNavController().navigate(R.id.action_profileFragment_to_authActivity)
            activity?.finishAffinity()
        }
    }

}
