package com.lc.android.presentation.vocabularydetail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.lc.android.R
import com.lc.android.base.BaseFragment
import com.lc.android.util.toast
import kotlinx.android.synthetic.main.fragment_vocabulary_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class VocabularyDetailFragment : BaseFragment(R.layout.fragment_vocabulary_detail) {

    private val viewModel by viewModel<VocabularyDetailViewModel>()
    private val args by navArgs<VocabularyDetailFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.callFetchVocabularyDetailUseCase(args.vocabularyGroupId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.state.observe { state ->
            animationLoading.isVisible = state.isLoading

            context.toast(state.vocabularies.size.toString())
        }

        viewModel.error.observeError()
    }

}
