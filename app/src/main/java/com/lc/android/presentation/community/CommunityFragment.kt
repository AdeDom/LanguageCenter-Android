package com.lc.android.presentation.community

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lc.android.R
import com.lc.android.base.BaseFragment
import com.lc.android.presentation.model.UserInfoLocaleParcelable
import com.lc.android.presentation.model.UserInfoParcelable
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
            val llm = LinearLayoutManager(context)
            layoutManager = llm
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val communitiesSize = viewModel.state.value?.communities?.size?.minus(1) ?: 0
                    val visibleItemPosition = llm.findLastCompletelyVisibleItemPosition()

                    if (visibleItemPosition == communitiesSize) {
                        viewModel.callFetchCommunity()
                    }
                }
            })
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
        }

        viewModel.error.observeError()
    }

    private fun viewEvent() {
        etSearch.addTextChangedListener { viewModel.searchCommunity(it.toString()) }

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
