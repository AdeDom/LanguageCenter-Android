package com.lc.android.presentation.vocapbulary

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.lc.android.R
import com.lc.android.base.BaseFragment
import com.lc.library.util.LanguageCenterConstant
import kotlinx.android.synthetic.main.fragment_vocabulary.*

class VocabularyFragment : BaseFragment(R.layout.fragment_vocabulary) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewEvent()
        resultListener()
    }

    private fun viewEvent() {
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_vocabularyFragment_to_addTranslateDialog)
        }
    }

    private fun resultListener() {
        setFragmentResultListener(AddTranslateDialog.ADD_TRANSLATE) { _, bundle ->
            val translate = bundle.getString(AddTranslateDialog.TRANSLATE_KEY)
            val navDirections = when (translate) {
                AddTranslateDialog.THAI_TO_ENGLISH -> {
                    VocabularyFragmentDirections.actionVocabularyFragmentToAddVocabularyFragment(
                        source = LanguageCenterConstant.THAI,
                        target = LanguageCenterConstant.ENGLISH,
                    )
                }
                else -> VocabularyFragmentDirections.actionVocabularyFragmentToAddVocabularyFragment(
                    source = LanguageCenterConstant.ENGLISH,
                    target = LanguageCenterConstant.THAI,
                )
            }
            findNavController().navigate(navDirections)
        }
    }

}
