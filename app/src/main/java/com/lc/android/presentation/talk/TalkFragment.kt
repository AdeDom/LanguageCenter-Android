package com.lc.android.presentation.talk

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.lc.android.R
import com.lc.android.base.BaseFragment
import com.lc.android.util.hideSoftKeyboard
import com.lc.android.util.loadCircle
import kotlinx.android.synthetic.main.fragment_talk.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TalkFragment : BaseFragment(R.layout.fragment_talk) {

    private val viewModel by viewModel<TalkViewModel>()
    private val args by navArgs<TalkFragmentArgs>()
    private val mAdapter by lazy { TalkAdapter(args.userInfo.picture) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialView()
        observeViewModel()
        viewEvent()
    }

    override fun onStart() {
        super.onStart()

        viewModel.callReadMessages(args.userInfo.userId)
    }

    private fun initialView() {
        val name = "${args.userInfo.givenName} ${args.userInfo.familyName}"
        tvName.text = name
        ivPicture.loadCircle(args.userInfo.picture)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context).apply {
                stackFromEnd = true
            }
            adapter = mAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.attachFirstTime.observe {
            viewModel.saveChatList(args.userInfo)
        }

        viewModel.state.observe { state ->
            animationLoading.isVisible = state.isLoading
            ibSendMessage.isClickable = state.isSendMessage
        }

        viewModel.clearTextEvent.observe {
            etMessage.text?.clear()
        }

        viewModel.getDbTalkByOtherUserIdLiveData(args.userInfo.userId)
            .observe(viewLifecycleOwner, { talkEntity ->
                if (talkEntity == null) return@observe

                if (talkEntity.isNotEmpty()) {
                    mAdapter.submitList(talkEntity)
                    recyclerView.smoothScrollToPosition(talkEntity.lastIndex)
                }
            })

        viewModel.error.observeError()
    }

    private fun viewEvent() {
        etMessage.addTextChangedListener { viewModel.setStateMessages(it.toString()) }

        ibSendMessage.setOnClickListener { viewModel.callSendMessage(args.userInfo.userId) }

        requireView().setOnClickListener { activity?.hideSoftKeyboard() }

        ivPicture.setOnClickListener { navToUserInfo() }
        tvName.setOnClickListener { navToUserInfo() }
    }

    private fun navToUserInfo() {
        val navDirections = TalkFragmentDirections
            .actionTalkFragmentToUserInfoFragment(null, true, args.userInfo)
        findNavController().navigate(navDirections)
    }

}
