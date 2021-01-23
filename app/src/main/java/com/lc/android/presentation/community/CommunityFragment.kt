package com.lc.android.presentation.community

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lc.android.R
import com.lc.android.base.BaseFragment
import com.lc.android.presentation.model.UserInfoLocaleParcelable
import com.lc.android.presentation.model.UserInfoParcelable
import com.lc.android.util.snackbar
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

        swipeRefreshLayout.apply {
            setColorSchemeColors(Color.GREEN, Color.YELLOW, Color.RED, Color.BLUE)
            setOnRefreshListener {
                viewModel.callFetchCommunity()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe { state ->
            animationLoading.isVisible = state.isLoading
            swipeRefreshLayout.isRefreshing = state.isLoading

            ivPlaceHolderDefault.isVisible = state.communities.isNullOrEmpty()
            mAdapter.submitList(state.communities)

            showAlgorithm(state)
        }

        viewModel.error.observeError()
    }

    private fun showAlgorithm(state: CommunityViewState) {
        val algorithmA = state.communities.filter { it.algorithm == "A" }.size
        val algorithmB = state.communities.filter { it.algorithm == "B" }.size
        val algorithmC = state.communities.filter { it.algorithm == "C" }.size
        val algorithmD1 = state.communities.filter { it.algorithm == "D1" }.size
        val algorithmD2 = state.communities.filter { it.algorithm == "D2" }.size
        val algorithmE1 = state.communities.filter { it.algorithm == "E1" }.size
        val algorithmE2 = state.communities.filter { it.algorithm == "E2" }.size
        val algorithmF = state.communities.filter { it.algorithm == "F" }.size
        val report = "A : $algorithmA, B : $algorithmB, C : $algorithmC, F : $algorithmF" +
                "\nD1 : $algorithmD1, D2 : $algorithmD2, E1 : $algorithmE1, E2 : $algorithmE2  [${state.communities.size}]"
        requireView().snackbar(report)
    }

    private fun viewEvent() {
        mAdapter.setListener { community ->
            val userInfoCommunity = UserInfoParcelable(
                userId = community.userId,
                email = community.email,
                givenName = community.givenName,
                familyName = community.familyName,
                name = community.name,
                picture = community.picture,
                gender = community.gender,
                age = community.age,
                birthDateString = community.birthDateString,
                birthDateLong = community.birthDateLong,
                aboutMe = community.aboutMe,
                algorithm = community.algorithm,
                localNatives = community.localNatives.map {
                    UserInfoLocaleParcelable(locale = it.locale, level = it.level)
                },
                localLearnings = community.localLearnings.map {
                    UserInfoLocaleParcelable(locale = it.locale, level = it.level)
                },
            )
            val navDirections = CommunityFragmentDirections
                .actionCommunityFragmentToUserInfoFragment(null, false, userInfoCommunity)
            findNavController().navigate(navDirections)
        }
    }

}
