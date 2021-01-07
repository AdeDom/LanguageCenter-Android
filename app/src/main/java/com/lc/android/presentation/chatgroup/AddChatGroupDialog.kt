package com.lc.android.presentation.chatgroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lc.android.R
import kotlinx.android.synthetic.main.dialog_add_chat_group.*

class AddChatGroupDialog : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_add_chat_group, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewEvent()
    }

    private fun viewEvent() {
        btConfirm.setOnClickListener {
            onClickConfirm()
        }

        etChatGroup.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_DONE) onClickConfirm()
            true
        }
    }

    private fun onClickConfirm() {
        setFragmentResult(
            ADD_CHAT_GROUP,
            bundleOf(GROUP_NAME to etChatGroup.text.toString().trim())
        )

        findNavController().popBackStack()
    }

    companion object {
        const val ADD_CHAT_GROUP = "AddChatGroup"
        const val GROUP_NAME = "groupName"
    }

}
