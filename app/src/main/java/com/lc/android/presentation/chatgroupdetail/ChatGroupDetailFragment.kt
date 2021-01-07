package com.lc.android.presentation.chatgroupdetail

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.lc.android.R
import com.lc.android.base.BaseFragment
import com.lc.android.presentation.ChatGroupDetailActivity
import kotlinx.android.synthetic.main.fragment_chat_group_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatGroupDetailFragment : BaseFragment(R.layout.fragment_chat_group_detail) {

    private val viewModel by viewModel<ChatGroupDetailViewModel>()
    private val mAdapter by lazy { ChatGroupDetailAdapter() }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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
            val chatGroupId = arguments?.getInt(ChatGroupDetailActivity.CHAT_GROUP_ID, 0)
            viewModel.callFetchChatGroupDetail(chatGroupId)
        }

        viewModel.state.observe { state ->
            animationLoading.isVisible = state.isLoading
            mAdapter.setList(state.chatGroupDetails)
        }

        viewModel.error.observeError()
    }

}
