package com.lc.android.presentation.vocapbulary

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lc.android.R
import com.lc.android.base.BaseFragment
import com.lc.android.presentation.model.TranslationParcelable
import com.lc.android.presentation.model.VocabularyFeedbackParcelable
import com.lc.server.util.LanguageCenterConstant
import kotlinx.android.synthetic.main.fragment_vocabulary.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class VocabularyFragment : BaseFragment(R.layout.fragment_vocabulary) {

    private val viewModel by viewModel<VocabularyViewModel>()
    private val mAdapter by lazy { VocabularyAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.callFetchVocabularyGroupUseCase()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialView()
        observeViewModel()
        viewEvent()
        resultListener()
    }

    private fun initialView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.attachFirstTime.observe {
            viewModel.randomVocabularyFeedback()
        }

        viewModel.state.observe { state ->
            animationLoading.isVisible = state.isLoading
            mAdapter.submitList(state.vocabularyGroups)
        }

        viewModel.getVocabularyFeedback.observe { entity ->
            val vocabularyFeedback = VocabularyFeedbackParcelable(
                vocabularyId = entity.vocabularyId,
                vocabulary = entity.vocabulary,
                translations = entity.translations.map {
                    TranslationParcelable(
                        translationId = it.translationId,
                        translation = it.translation,
                    )
                },
            )
            val navDirections = VocabularyFragmentDirections
                .actionVocabularyFragmentToVocabularyFeedbackDialog(vocabularyFeedback)
            findNavController().navigate(navDirections)
        }

        viewModel.error.observeError()
    }

    private fun viewEvent() {
        mAdapter.setListener {
            val navDirections = VocabularyFragmentDirections
                .actionVocabularyFragmentToVocabularyDetailFragment(it.vocabularyGroupId)
            findNavController().navigate(navDirections)
        }

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

        setFragmentResultListener(VocabularyFeedbackDialog.VOCABULARY_FEEDBACK) { _, bundle ->
            val vocabularyFeedback = bundle.getParcelable<VocabularyFeedbackParcelable>(
                VocabularyFeedbackDialog.VOCABULARY_FEEDBACK_KEY
            )
        }
    }

}
