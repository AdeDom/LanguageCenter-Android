package com.lc.android.presentation.talk

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.lc.android.R
import com.lc.android.base.BaseFragment
import com.lc.android.util.hideSoftKeyboard
import com.lc.android.util.loadCircle
import kotlinx.android.synthetic.main.fragment_talk.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TalkFragment : BaseFragment(R.layout.fragment_talk) {

    private val viewModel by viewModel<TalkViewModel>()
    private val args by navArgs<TalkFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialView()
        observeViewModel()
        viewEvent()
    }

    private fun initialView() {
        val name = "${args.userInfo.givenName} ${args.userInfo.familyName}"
        tvName.text = name
        ivPicture.loadCircle(args.userInfo.picture)
    }

    private fun observeViewModel() {
        viewModel.attachFirstTime.observe {
            viewModel.setStateToUserId(args.userInfo.userId)
        }

        viewModel.state.observe { state ->
            animationLoading.isVisible = state.isLoading
            ibSendMessage.isClickable = state.isSendMessage
        }

        viewModel.clearTextEvent.observe {
            etMessage.text?.clear()
        }

        viewModel.error.observeError()
    }

    private fun viewEvent() {
        etMessage.addTextChangedListener { viewModel.setStateMessages(it.toString()) }

        ibSendMessage.setOnClickListener { viewModel.callSendMessage() }

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
