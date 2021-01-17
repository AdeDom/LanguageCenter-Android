package com.lc.android.presentation.main

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.lc.android.R
import com.lc.android.base.BaseActivity
import com.lc.library.sharedpreference.pref.ConfigPref
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

        when (viewModel.getSelectPage()) {
            ConfigPref.SELECT_PAGE_COMMUNITY -> navGraphStartDestination(R.id.communityFragment)
            ConfigPref.SELECT_PAGE_CHATS -> navGraphStartDestination(R.id.chatsFragment)
            ConfigPref.SELECT_PAGE_CHAT_GROUP -> navGraphStartDestination(R.id.chatGroupFragment)
            ConfigPref.SELECT_PAGE_VOCABULARY -> navGraphStartDestination(R.id.vocabularyFragment)
            ConfigPref.SELECT_PAGE_PROFILE -> navGraphStartDestination(R.id.profileFragment)
        }

        fragment.findNavController().addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.communityFragment -> showBottomNav(ConfigPref.SELECT_PAGE_COMMUNITY)
                R.id.chatsFragment -> showBottomNav(ConfigPref.SELECT_PAGE_CHATS)
                R.id.chatGroupFragment -> showBottomNav(ConfigPref.SELECT_PAGE_CHAT_GROUP)
                R.id.vocabularyFragment -> showBottomNav(ConfigPref.SELECT_PAGE_VOCABULARY)
                R.id.profileFragment -> showBottomNav(ConfigPref.SELECT_PAGE_PROFILE)
                R.id.userInfoFragment -> hideBottomNav()
                R.id.chatGroupDetailFragment -> hideBottomNav()
                R.id.editProfileFragment -> hideBottomNav()
                R.id.editLocaleNativeFragment -> hideBottomNav()
                R.id.editLocaleLearningFragment -> hideBottomNav()
            }
        }
    }

    private fun navGraphStartDestination(@IdRes destinationFragment: Int) {
        fragment.findNavController().apply {
            val navGraph = navInflater.inflate(R.navigation.nav_main).apply {
                startDestination = destinationFragment
            }
            graph = navGraph
        }
    }

    private fun showBottomNav(selectPage: String) {
        viewModel.setSelectPage(selectPage)
        bottomNavigationView.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        bottomNavigationView.visibility = View.GONE
    }

    private fun observeViewModel() {
        viewModel.attachFirstTime.observe {
            viewModel.callFetchFriendInfo()
        }
    }

}
