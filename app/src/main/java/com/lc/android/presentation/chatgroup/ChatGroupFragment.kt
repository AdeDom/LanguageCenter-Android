package com.lc.android.presentation.chatgroup

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lc.android.R
import com.lc.android.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_chat_group.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatGroupFragment : BaseFragment(R.layout.fragment_chat_group) {

    private val viewModel by viewModel<ChatGroupViewModel>()
    private val mAdapter by lazy { ChatGroupAdapter() }

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

    override fun onStart() {
        super.onStart()

        viewModel.callFetchChatGroup()
    }

    private fun observeViewModel() {
        viewModel.state.observe { state ->
            animationLoading.isVisible = state.isLoading

            mAdapter.setList(state.chatGroups)
        }

        viewModel.error.observeError()
    }

    private fun viewEvent() {
        ibAddChatGroup.setOnClickListener {
            findNavController().navigate(R.id.action_chatGroupFragment_to_addChatGroupActivity)
        }
    }

}
