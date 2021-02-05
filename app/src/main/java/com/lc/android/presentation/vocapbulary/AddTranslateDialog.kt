package com.lc.android.presentation.vocapbulary

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.lc.android.R
import com.lc.android.base.BaseBottomSheetDialog
import kotlinx.android.synthetic.main.dialog_add_translate.*

class AddTranslateDialog : BaseBottomSheetDialog(R.layout.dialog_add_translate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewEvent()
    }

    private fun viewEvent() {
        tvThaiToEnglish.setOnClickListener {
            onSelectTranslate(THAI_TO_ENGLISH)
        }

        tvEnglishToThai.setOnClickListener {
            onSelectTranslate(ENGLISH_TO_THAI)
        }
    }

    private fun onSelectTranslate(translate: String) {
        findNavController().popBackStack()
        setFragmentResult(
            ADD_TRANSLATE,
            bundleOf(TRANSLATE_KEY to translate)
        )
    }

    companion object {
        const val ADD_TRANSLATE = "AddTranslate"
        const val TRANSLATE_KEY = "translateKey"
        const val THAI_TO_ENGLISH = "thaiToEnglish"
        const val ENGLISH_TO_THAI = "EnglishToThai"
    }

}
