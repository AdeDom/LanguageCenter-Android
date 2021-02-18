package com.lc.android.presentation.friendgroup

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.lc.android.R
import com.lc.android.base.BaseBottomSheetDialog
import kotlinx.android.synthetic.main.dialog_add_friend_group.*

class AddFriendGroupDialog : BaseBottomSheetDialog(R.layout.dialog_add_friend_group) {

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
        findNavController().popBackStack()
        setFragmentResult(
            ADD_CHAT_GROUP,
            bundleOf(GROUP_NAME to etChatGroup.text.toString().trim())
        )
    }

    companion object {
        const val ADD_CHAT_GROUP = "AddChatGroup"
        const val GROUP_NAME = "groupName"
    }

}
