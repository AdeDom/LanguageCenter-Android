package com.lc.android.presentation.friendgroup

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.lc.android.R
import com.lc.android.base.BaseBottomSheetDialog
import kotlinx.android.synthetic.main.dialog_more_friend_group.*

class MoreFriendGroupDialog : BaseBottomSheetDialog(R.layout.dialog_more_friend_group) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewEvent()
    }

    private fun viewEvent() {
        tvRename.setOnClickListener {
            onSelectItem(RENAME)
        }

        tvRemove.setOnClickListener {
            onSelectItem(REMOVE)
        }
    }

    private fun onSelectItem(value: String) {
        findNavController().popBackStack()
        setFragmentResult(
            MORE_CHAT_GROUP,
            bundleOf(MORE_KEY to value)
        )
    }

    companion object {
        const val MORE_CHAT_GROUP = "MoreChatGroup"
        const val MORE_KEY = "moreKey"
        const val RENAME = "rename"
        const val REMOVE = "remove"
    }

}
