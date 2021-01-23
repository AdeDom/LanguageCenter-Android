package com.lc.android.presentation.chatgroup

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.lc.android.R
import com.lc.android.base.BaseFragment
import com.lc.android.presentation.model.ChatGroup
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

            fab.isClickable = state.isClickable

            ivPlaceHolderDefault.isVisible = state.chatGroups.isNullOrEmpty()
            mAdapter.submitList(state.chatGroups)
        }

        viewModel.chatGroupEvent.observe { response ->
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
        mAdapter.setListener { chatGroupId, chatGroups ->
            chatGroupId?.let {
                val navDirections = ChatGroupFragmentDirections
                    .actionChatGroupFragmentToChatGroupDetailFragment(
                        chatGroupId = chatGroupId,
                        chatGroups = chatGroups.map {
                            ChatGroup(
                                chatGroupId = it.chatGroupId,
                                groupName = it.groupName,
                                userId = it.userId,
                            )
                        }.toTypedArray(),
                    )
                findNavController().navigate(navDirections)
            }
        }

        mAdapter.setMoreListener { chatGroup ->
            viewModel.setStateChatGroup(chatGroup)
            findNavController().navigate(R.id.action_chatGroupFragment_to_moreChatGroupDialog)
        }

        fab.setOnClickListener {
            findNavController().navigate(R.id.action_chatGroupFragment_to_addChatGroupDialog)
        }
    }

    private fun resultListener() {
        setFragmentResultListener(AddChatGroupDialog.ADD_CHAT_GROUP) { _, bundle ->
            val groupName = bundle.getString(AddChatGroupDialog.GROUP_NAME)
            viewModel.callAddChatGroup(groupName)
        }

        setFragmentResultListener(RenameChatGroupDialog.RENAME_CHAT_GROUP) { _, bundle ->
            val groupName = bundle.getString(RenameChatGroupDialog.GROUP_NAME)
            viewModel.callRenameChatGroup(groupName)
        }

        setFragmentResultListener(MoreChatGroupDialog.MORE_CHAT_GROUP) { _, bundle ->
            when (bundle.getString(MoreChatGroupDialog.MORE_KEY, "")) {
                MoreChatGroupDialog.RENAME -> dialogRenameChatGroup()
                MoreChatGroupDialog.REMOVE -> dialogRemoveChatGroup()
            }
        }
    }

    private fun dialogRenameChatGroup() {
        val groupName = viewModel.state.value?.chatGroup?.groupName
        val navDirections = ChatGroupFragmentDirections
            .actionChatGroupFragmentToRenameChatGroupDialog(groupName)
        findNavController().navigate(navDirections)
    }

    private fun dialogRemoveChatGroup() {
        AlertDialog.Builder(requireActivity()).apply {
            setTitle(R.string.dialog_remove_chat_group_title)
            setMessage(R.string.dialog_remove_chat_group_message)
            setPositiveButton(android.R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            setNegativeButton(android.R.string.ok) { dialog, _ ->
                viewModel.callRemoveChatGroup()
                dialog.dismiss()
            }
            show()
        }
    }

}
