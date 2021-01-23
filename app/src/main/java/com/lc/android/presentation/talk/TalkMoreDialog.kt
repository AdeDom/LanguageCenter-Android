package com.lc.android.presentation.talk

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.lc.android.R
import com.lc.android.base.BaseBottomSheetDialog
import kotlinx.android.synthetic.main.dialog_talk_more.*

class TalkMoreDialog : BaseBottomSheetDialog(R.layout.dialog_talk_more) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewEvent()
    }

    private fun viewEvent() {
        tvResendMessage.setOnClickListener {
            findNavController().popBackStack()
            setFragmentResult(
                TALK_MORE,
                bundleOf(TALK_MORE_KEY to RESEND_MESSAGE)
            )
        }
    }

    companion object {
        const val TALK_MORE = "TalkMore"
        const val TALK_MORE_KEY = "TalkMoreKey"
        const val RESEND_MESSAGE = "resendMessage"
    }

}
