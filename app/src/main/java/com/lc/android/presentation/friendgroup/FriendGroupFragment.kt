package com.lc.android.presentation.friendgroup

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
import kotlinx.android.synthetic.main.fragment_friend_group.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FriendGroupFragment : BaseFragment(R.layout.fragment_friend_group) {

    private val viewModel by viewModel<FriendGroupViewModel>()
    private val mAdapter by lazy { FriendGroupAdapter() }

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
                val navDirections = FriendGroupFragmentDirections
                    .actionFriendGroupFragmentToFriendGroupDetailFragment(
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
            findNavController().navigate(R.id.action_friendGroupFragment_to_moreFriendGroupDialog)
        }

        fab.setOnClickListener {
            findNavController().navigate(R.id.action_friendGroupFragment_to_addFriendGroupDialog)
        }
    }

    private fun resultListener() {
        setFragmentResultListener(AddFriendGroupDialog.ADD_CHAT_GROUP) { _, bundle ->
            val groupName = bundle.getString(AddFriendGroupDialog.GROUP_NAME)
            viewModel.callAddChatGroup(groupName)
        }

        setFragmentResultListener(RenameFriendGroupDialog.RENAME_CHAT_GROUP) { _, bundle ->
            val groupName = bundle.getString(RenameFriendGroupDialog.GROUP_NAME)
            viewModel.callRenameChatGroup(groupName)
        }

        setFragmentResultListener(MoreFriendGroupDialog.MORE_CHAT_GROUP) { _, bundle ->
            when (bundle.getString(MoreFriendGroupDialog.MORE_KEY, "")) {
                MoreFriendGroupDialog.RENAME -> dialogRenameChatGroup()
                MoreFriendGroupDialog.REMOVE -> dialogRemoveChatGroup()
            }
        }
    }

    private fun dialogRenameChatGroup() {
        val groupName = viewModel.state.value?.chatGroup?.groupName
        val navDirections = FriendGroupFragmentDirections
            .actionFriendGroupFragmentToRenameFriendGroupDialog(groupName)
        findNavController().navigate(navDirections)
    }

    private fun dialogRemoveChatGroup() {
        AlertDialog.Builder(requireActivity()).apply {
            setTitle(R.string.dialog_remove_friend_group_title)
            setMessage(R.string.dialog_remove_friend_group_message)
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
