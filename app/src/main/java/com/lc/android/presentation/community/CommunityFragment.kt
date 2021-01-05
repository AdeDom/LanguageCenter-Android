package com.lc.android.presentation.community

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.lc.android.R
import com.lc.android.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_community.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CommunityFragment : BaseFragment(R.layout.fragment_community) {

    private val viewModel by viewModel<CommunityViewModel>()
    private val mAdapter by lazy { CommunityAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialView()
        observeViewModel()
    }

    private fun initialView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.attachFirstTime.observe {
            viewModel.callFetchCommunity()
        }

        viewModel.state.observe { state ->
            animationLoading.isVisible = state.isLoading

            mAdapter.setList(state.userInfoList)
        }

        viewModel.error.observeError()
    }

}
