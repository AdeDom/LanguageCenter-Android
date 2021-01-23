package com.lc.android.presentation.chatgroupdetail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.lc.android.R
import com.lc.android.base.BaseFragment
import com.lc.android.presentation.model.ChatGroup
import com.lc.android.presentation.model.UserInfoLocaleParcelable
import com.lc.android.presentation.model.UserInfoParcelable
import com.lc.android.util.snackbar
import kotlinx.android.synthetic.main.fragment_chat_group_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatGroupDetailFragment : BaseFragment(R.layout.fragment_chat_group_detail) {

    private val viewModel by viewModel<ChatGroupDetailViewModel>()
    private val args by navArgs<ChatGroupDetailFragmentArgs>()
    private val mAdapter by lazy { ChatGroupDetailAdapter() }

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
        viewModel.attachFirstTime.observe {
            viewModel.setStateChatGroups(args.chatGroupId, args.chatGroups.toList())
            viewModel.callFetchChatGroupDetail()
        }

        viewModel.state.observe { state ->
            animationLoading.isVisible = state.isLoading
            fab.isClickable = state.isClickable

            ivPlaceHolderDefault.isVisible = state.chatGroupDetails.isNullOrEmpty()
            mAdapter.submitList(state.chatGroupDetails)
        }

        viewModel.chatGroupDetailEvent.observe { response ->
            if (response.success) {
                requireView().snackbar(response.message, Snackbar.LENGTH_SHORT)
                viewModel.callFetchChatGroupDetail()
            } else {
                requireView().snackbar(response.message)
            }
        }

        viewModel.error.observeError()
    }

    private fun viewEvent() {
        fab.setOnClickListener {
            val navDirections = ChatGroupDetailFragmentDirections
                .actionChatGroupDetailFragmentToAddChatGroupDetailFragment(args.chatGroupId)
            findNavController().navigate(navDirections)
        }

        mAdapter.setListener { chatGroupDetail ->
            val userInfo = UserInfoParcelable(
                userId = chatGroupDetail.userId,
                email = chatGroupDetail.email,
                givenName = chatGroupDetail.givenName,
                familyName = chatGroupDetail.familyName,
                name = chatGroupDetail.name,
                picture = chatGroupDetail.picture,
                gender = chatGroupDetail.gender,
                age = chatGroupDetail.age,
                birthDateString = chatGroupDetail.birthDateString,
                birthDateLong = chatGroupDetail.birthDateLong,
                aboutMe = chatGroupDetail.aboutMe,
                localNatives = chatGroupDetail.localNatives.map {
                    UserInfoLocaleParcelable(locale = it.locale, level = it.level)
                },
                localLearnings = chatGroupDetail.localLearnings.map {
                    UserInfoLocaleParcelable(locale = it.locale, level = it.level)
                },
            )
            val navDirections = ChatGroupDetailFragmentDirections
                .actionChatGroupDetailFragmentToUserInfoFragment(
                    args.chatGroupId.toString(),
                    false,
                    userInfo,
                )
            findNavController().navigate(navDirections)
        }

        mAdapter.setMoreListener {
            viewModel.setStateFriendUserId(it.userId)
            findNavController().navigate(R.id.action_chatGroupDetailFragment_to_moreChatGroupDetailDialog)
        }
    }

    private fun resultListener() {
        setFragmentResultListener(MoreChatGroupDetailDialog.MORE_CHAT_GROUP_DETAIL) { _, bundle ->
            when (bundle.getString(MoreChatGroupDetailDialog.MORE_KEY, "")) {
                MoreChatGroupDetailDialog.CHANGE_CHAT_GROUP -> dialogChangeChatGroup()
                MoreChatGroupDetailDialog.REMOVE -> dialogRemoveChatGroupDetail()
            }
        }

        setFragmentResultListener(ChangeChatGroupDialog.CHANGE_CHAT_GROUP) { _, bundle ->
            val chatGroup = bundle.getParcelable<ChatGroup>(ChangeChatGroupDialog.CHANGE_KEY)
            viewModel.callChangeChatGroup(chatGroup?.chatGroupId)
        }
    }

    private fun dialogChangeChatGroup() {
        val otherChatGroups = viewModel.state.value?.otherChatGroups?.toTypedArray()
        otherChatGroups?.let {
            val navDirections = ChatGroupDetailFragmentDirections
                .actionChatGroupDetailFragmentToChangeChatGroupDialog(it)
            findNavController().navigate(navDirections)
        }
    }

    private fun dialogRemoveChatGroupDetail() {
        AlertDialog.Builder(requireActivity()).apply {
            setTitle(R.string.dialog_remove_chat_group_detail_title)
            setMessage(R.string.dialog_remove_chat_group_detail_message)
            setPositiveButton(android.R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            setNegativeButton(android.R.string.ok) { dialog, _ ->
                viewModel.callRemoveChatGroupDetail()
                dialog.dismiss()
            }
            show()
        }
    }

}
