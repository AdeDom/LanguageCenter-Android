package com.lc.android.presentation.community

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lc.android.R
import com.lc.android.base.BaseFragment
import com.lc.android.presentation.model.UserInfoCommunity
import com.lc.android.presentation.model.UserInfoLocaleParcelable
import kotlinx.android.synthetic.main.fragment_community.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CommunityFragment : BaseFragment(R.layout.fragment_community) {

    private val viewModel by viewModel<CommunityViewModel>()
    private val mAdapter by lazy { CommunityAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.callFetchCommunity()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialView()
        observeViewModel()
        viewEvent()
    }

    private fun initialView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe { state ->
            animationLoading.isVisible = state.isLoading

            ivPlaceHolderDefault.isVisible = state.communities.isNullOrEmpty()
            mAdapter.setList(state.communities)
        }

        viewModel.error.observeError()
    }

    private fun viewEvent() {
        mAdapter.setListener { community ->
            val userInfoCommunity = UserInfoCommunity(
                userId = community.userId,
                email = community.email,
                name = "${community.givenName} ${community.familyName}",
                picture = community.picture,
                gender = community.gender,
                age = community.age,
                aboutMe = community.aboutMe,
                algorithm = community.algorithm,
                localNatives = community.localNatives.map {
                    UserInfoLocaleParcelable(
                        locale = it.locale,
                        level = it.level,
                    )
                },
                localLearnings = community.localLearnings.map {
                    UserInfoLocaleParcelable(
                        locale = it.locale,
                        level = it.level,
                    )
                },
            )
            val navDirections = CommunityFragmentDirections
                .actionCommunityFragmentToUserInfoFragment(userInfoCommunity)
            findNavController().navigate(navDirections)
        }
    }

}
