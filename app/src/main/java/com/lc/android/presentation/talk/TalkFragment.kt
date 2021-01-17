package com.lc.android.presentation.talk

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.lc.android.R
import com.lc.android.base.BaseFragment
import com.lc.android.util.hideSoftKeyboard
import com.lc.android.util.snackbar
import kotlinx.android.synthetic.main.fragment_talk.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TalkFragment : BaseFragment(R.layout.fragment_talk) {

    private val viewModel by viewModel<TalkViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        viewEvent()
    }

    private fun observeViewModel() {
        viewModel.state.observe { state ->
            animationLoading.isVisible = state.isLoading
        }

        viewModel.sendMessageEvent.observe { response ->
            if (response.success) {
                requireView().snackbar(response.message)
            } else {
                requireView().snackbar(response.message)
            }
        }

        viewModel.clearTextEvent.observe {
            etMessage.text?.clear()
        }

        viewModel.error.observeError()
    }

    private fun viewEvent() {
        etMessage.addTextChangedListener { viewModel.setStateMessage(it.toString()) }

        ibSendMessage.setOnClickListener { viewModel.callSendMessage() }

        requireView().setOnClickListener { activity?.hideSoftKeyboard() }
    }

}
