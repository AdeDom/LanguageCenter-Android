package com.lc.android.presentation.main

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.lc.android.R
import com.lc.android.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialView()
        observeViewModel()
    }

    private fun initialView() {
        bottomNavigationView.setupWithNavController(fragment.findNavController())

        fragment.findNavController().addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.communityFragment -> showBottomNav()
                R.id.chatGroupFragment -> showBottomNav()
                R.id.profileFragment -> showBottomNav()
                R.id.userInfoFragment -> hideBottomNav()
                R.id.chatGroupDetailFragment -> hideBottomNav()
                R.id.editProfileFragment -> hideBottomNav()
                R.id.editLocaleNativeFragment -> hideBottomNav()
                R.id.editLocaleLearningFragment -> hideBottomNav()
            }
        }
    }

    private fun showBottomNav() {
        bottomNavigationView.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        bottomNavigationView.visibility = View.GONE
    }

    private fun observeViewModel() {
        viewModel.attachFirstTime.observe { state ->
            viewModel.callFetchFriendInfo()
        }
    }

}
