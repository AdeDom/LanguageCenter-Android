package com.lc.android.presentation.vocabularytranslation

import android.os.Bundle
import android.view.View
import com.lc.android.R
import com.lc.android.base.BaseFragment
import com.lc.android.util.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class VocabularyTranslationFragment : BaseFragment(R.layout.fragment_vocabulary_translation) {

    private val viewModel by viewModel<VocabularyTranslationViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.attachFirstTime.observe {
            viewModel.callFetchVocabularyTranslation()
        }

        viewModel.state.observe { state ->
            context.toast(state.vocabularies.toString())
        }

        viewModel.error.observeError()
    }

}
