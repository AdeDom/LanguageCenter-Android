package com.lc.android.presentation.chatgroupdetail

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.lc.android.R
import com.lc.android.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_chat_group_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatGroupDetailFragment : BaseFragment(R.layout.fragment_chat_group_detail) {

    private val viewModel by viewModel<ChatGroupDetailViewModel>()
    private val args by navArgs<ChatGroupDetailFragmentArgs>()
    private val mAdapter by lazy { ChatGroupDetailAdapter() }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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
        viewModel.attachFirstTime.observe {
            viewModel.callFetchChatGroupDetail(args.chatGroupId)
        }

        viewModel.state.observe { state ->
            animationLoading.isVisible = state.isLoading

            ivPlaceHolderDefault.isVisible = state.chatGroupDetails.isNullOrEmpty()
            mAdapter.setList(state.chatGroupDetails)
        }

        viewModel.error.observeError()
    }

    private fun viewEvent() {
        ivAddChatGroupDetail.setOnClickListener {
            findNavController().navigate(R.id.action_chatGroupDetailFragment_to_addChatGroupDetailFragment)
        }
    }

}
