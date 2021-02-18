package com.lc.android.presentation.friendgroupdetail

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.lc.android.R
import com.lc.android.base.BaseBottomSheetDialog
import kotlinx.android.synthetic.main.dialog_change_friend_group_detail.*

class ChangeFriendGroupDetailDialog :
    BaseBottomSheetDialog(R.layout.dialog_change_friend_group_detail) {

    private val args by navArgs<ChangeFriendGroupDetailDialogArgs>()
    private val mAdapter by lazy { ChangeFriendGroupDetailAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialView()
    }

    private fun initialView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }

        mAdapter.submitList(args.otherChatGroups.toList())

        mAdapter.setListener {
            findNavController().popBackStack()
            setFragmentResult(
                CHANGE_CHAT_GROUP,
                bundleOf(CHANGE_KEY to it)
            )
        }
    }

    companion object {
        const val CHANGE_CHAT_GROUP = "ChangeChatGroup"
        const val CHANGE_KEY = "changeKey"
    }

}
