package com.lc.android.presentation.chatgroup

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.lc.android.R
import kotlinx.android.synthetic.main.dialog_more_chat_group.*

class MoreChatGroupDialog : DialogFragment(R.layout.dialog_more_chat_group) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Material_Light_Dialog_MinWidth)
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
