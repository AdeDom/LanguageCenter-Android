package com.lc.android.presentation.friendgroupdetail

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.lc.android.R
import com.lc.android.base.BaseBottomSheetDialog
import kotlinx.android.synthetic.main.dialog_more_friend_group_detail.*

class MoreFriendGroupDetailDialog :
    BaseBottomSheetDialog(R.layout.dialog_more_friend_group_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewEvent()
    }

    private fun viewEvent() {
        tvChangeChatGroup.setOnClickListener {
            onSelectItem(CHANGE_CHAT_GROUP)
        }

        tvRemove.setOnClickListener {
            onSelectItem(REMOVE)
        }
    }

    private fun onSelectItem(value: String) {
        findNavController().popBackStack()
        setFragmentResult(
            MORE_CHAT_GROUP_DETAIL,
            bundleOf(MORE_KEY to value)
        )
    }

    companion object {
        const val MORE_CHAT_GROUP_DETAIL = "MoreChatGroupDetail"
        const val MORE_KEY = "moreKey"
        const val CHANGE_CHAT_GROUP = "change_chat_group_detail"
        const val REMOVE = "remove"
    }

}
