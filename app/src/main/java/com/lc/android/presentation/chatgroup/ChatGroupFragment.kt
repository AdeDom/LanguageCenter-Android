package com.lc.android.presentation.chatgroup

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.lc.android.R
import com.lc.android.base.BaseFragment
import com.lc.android.util.snackbar
import kotlinx.android.synthetic.main.fragment_chat_group.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatGroupFragment : BaseFragment(R.layout.fragment_chat_group) {

    private val viewModel by viewModel<ChatGroupViewModel>()
    private val mAdapter by lazy { ChatGroupAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.callFetchChatGroup()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialView()
        observeViewModel()
        viewEvent()
        resultListener()
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

            ibAddChatGroup.isClickable = state.isClickable

            ivPlaceHolderDefault.isVisible = state.chatGroups.isNullOrEmpty()
            mAdapter.setList(state.chatGroups)
        }

        viewModel.addChatGroupEvent.observe { response ->
            if (response.success) {
                requireView().snackbar(response.message, Snackbar.LENGTH_SHORT)
                viewModel.callFetchChatGroup()
            } else {
                requireView().snackbar(response.message)
            }
        }

        viewModel.error.observeError()
    }

    private fun viewEvent() {
        mAdapter.setListener { chatGroupId ->
            chatGroupId?.let {
                val navDirections = ChatGroupFragmentDirections
                    .actionChatGroupFragmentToChatGroupDetailFragment(chatGroupId)
                findNavController().navigate(navDirections)
            }
        }

        ibAddChatGroup.setOnClickListener {
            findNavController().navigate(R.id.action_chatGroupFragment_to_addChatGroupDialog)
        }
    }

    private fun resultListener() {
        setFragmentResultListener(AddChatGroupDialog.ADD_CHAT_GROUP) { _, bundle ->
            val groupName = bundle.getString(AddChatGroupDialog.GROUP_NAME)
            viewModel.callAddChatGroup(groupName)
        }
    }

}
