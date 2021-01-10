package com.lc.android.presentation.chatgroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lc.android.R
import kotlinx.android.synthetic.main.dialog_more_chat_group.*

class MoreChatGroupDialog : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_more_chat_group, container, false)
    }

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
