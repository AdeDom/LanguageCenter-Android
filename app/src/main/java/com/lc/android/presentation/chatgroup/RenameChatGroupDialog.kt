package com.lc.android.presentation.chatgroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lc.android.R
import kotlinx.android.synthetic.main.dialog_add_chat_group.*

class RenameChatGroupDialog : BottomSheetDialogFragment() {

    private val args by navArgs<RenameChatGroupDialogArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_rename_chat_group, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialView()
        viewEvent()
    }

    private fun initialView() {
        etChatGroup.setText(args.groupName)
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
        findNavController().popBackStack()
        setFragmentResult(
            RENAME_CHAT_GROUP,
            bundleOf(GROUP_NAME to etChatGroup.text.toString().trim())
        )
    }

    companion object {
        const val RENAME_CHAT_GROUP = "RenameChatGroup"
        const val GROUP_NAME = "groupName"
    }

}
