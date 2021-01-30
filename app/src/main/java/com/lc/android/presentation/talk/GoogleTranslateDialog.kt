package com.lc.android.presentation.talk

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.lc.android.R
import com.lc.android.base.BaseBottomSheetDialog
import kotlinx.android.synthetic.main.dialog_google_translate.*

class GoogleTranslateDialog : BaseBottomSheetDialog(R.layout.dialog_google_translate) {

    private val args by navArgs<GoogleTranslateDialogArgs>()
    private var mIsTranslateThToEn :Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mIsTranslateThToEn = args.isTranslateThToEn

        initialView()
        viewEvent()
    }

    private fun initialView() {
        if (mIsTranslateThToEn) {
            tvVocabularyText.text = getString(R.string.th)
            tvTranslationText.text = getString(R.string.en)
        } else {
            tvVocabularyText.text = getString(R.string.en)
            tvTranslationText.text = getString(R.string.th)
        }
    }

    private fun viewEvent() {
        ibSwitch.setOnClickListener {
            mIsTranslateThToEn = !mIsTranslateThToEn
            initialView()
            setFragmentResult(
                IS_TRANSLATE_TH_TO_EN_KEY,
                bundleOf(IS_TRANSLATE_TH_TO_EN to mIsTranslateThToEn)
            )
        }

        btConfirm.setOnClickListener {
            findNavController().popBackStack()
            setFragmentResult(
                TRANSLATE_TEXT_KEY,
                bundleOf(TRANSLATE_TEXT to etTranslateText.text.toString().trim())
            )
        }
    }

    companion object {
        const val IS_TRANSLATE_TH_TO_EN_KEY = "isTranslateThToEnKey"
        const val IS_TRANSLATE_TH_TO_EN = "isTranslateThToEn"
        const val TRANSLATE_TEXT_KEY = "translateTextKey"
        const val TRANSLATE_TEXT = "translateText"
    }

}
