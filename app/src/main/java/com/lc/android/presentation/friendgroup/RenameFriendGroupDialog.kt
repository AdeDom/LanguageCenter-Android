package com.lc.android.presentation.friendgroup

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.lc.android.R
import com.lc.android.base.BaseBottomSheetDialog
import kotlinx.android.synthetic.main.dialog_rename_friend_group.*

class RenameFriendGroupDialog : BaseBottomSheetDialog(R.layout.dialog_rename_friend_group) {

    private val args by navArgs<RenameFriendGroupDialogArgs>()

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
