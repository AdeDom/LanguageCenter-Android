package com.lc.android.presentation.addchatgroup

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.lc.android.R
import com.lc.android.base.BaseActivity
import com.lc.android.util.toast
import kotlinx.android.synthetic.main.activity_add_chat_group.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddChatGroupActivity : BaseActivity() {

    private val viewModel by viewModel<AddChatGroupViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_chat_group)

        observeViewModel()
        setState()
        viewEvent()
    }

    private fun observeViewModel() {
        viewModel.state.observe { state ->
            animationLoading.isVisible = state.isLoading

            btConfirm.isClickable = state.isClickable
            btCancel.isClickable = state.isClickable
        }

        viewModel.addChatGroupEvent.observe { response ->
            if (response.success) {
                toast(response.message)
                finish()
            } else {
                toast(response.message, Toast.LENGTH_LONG)
            }
        }

        viewModel.error.observeError()
    }

    private fun setState() {
        etChatGroup.addTextChangedListener { viewModel.setStateGroupName(it.toString()) }
    }

    private fun viewEvent() {
        etChatGroup.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_DONE) viewModel.callAddChatGroup()
            true
        }

        btConfirm.setOnClickListener {
            viewModel.callAddChatGroup()
        }

        btCancel.setOnClickListener {
            finish()
        }
    }

}
