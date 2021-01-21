package com.lc.android.presentation.chats

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lc.android.R
import com.lc.android.base.BaseFragment
import com.lc.android.presentation.model.UserInfoLocaleParcelable
import com.lc.android.presentation.model.UserInfoParcelable
import kotlinx.android.synthetic.main.fragment_chats.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatsFragment : BaseFragment(R.layout.fragment_chats) {

    private val viewModel by viewModel<ChatsViewModel>()
    private val mAdapter by lazy { ChatsAdapter() }

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

    private fun observeViewModel() {
        viewModel.getDbChatListLiveData.observe(viewLifecycleOwner, { chatListEntity ->
            if (chatListEntity == null) return@observe

            mAdapter.submitList(chatListEntity)
        })

        viewModel.error.observeError()
    }

    private fun viewEvent() {
        mAdapter.setListener { chatListEntity ->
            val userInfo = UserInfoParcelable(
                userId = chatListEntity.userId,
                email = chatListEntity.email,
                givenName = chatListEntity.givenName,
                familyName = chatListEntity.familyName,
                name = chatListEntity.name,
                picture = chatListEntity.picture,
                gender = chatListEntity.gender,
                age = chatListEntity.age,
                birthDateString = chatListEntity.birthDateString,
                birthDateLong = chatListEntity.birthDateLong,
                aboutMe = chatListEntity.aboutMe,
                localNatives = chatListEntity.localNatives.map {
                    UserInfoLocaleParcelable(locale = it.locale, level = it.level)
                },
                localLearnings = chatListEntity.localLearnings.map {
                    UserInfoLocaleParcelable(locale = it.locale, level = it.level)
                },
            )
            val navDirections = ChatsFragmentDirections
                .actionChatsFragmentToTalkFragment(userInfo)
            findNavController().navigate(navDirections)
        }
    }

}
