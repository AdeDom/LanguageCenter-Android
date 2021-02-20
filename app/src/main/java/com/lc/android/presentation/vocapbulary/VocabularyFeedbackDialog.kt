package com.lc.android.presentation.vocapbulary

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.lc.android.R
import com.lc.android.presentation.model.TranslationParcelable
import kotlinx.android.synthetic.main.dialog_vocabulary_feedback.*

class VocabularyFeedbackDialog : DialogFragment(R.layout.dialog_vocabulary_feedback) {

    private val args by navArgs<VocabularyFeedbackDialogArgs>()
    private val mAdapter by lazy { VocabularyFeedbackAdapter() }
    private var mTranslationList = mutableListOf<TranslationParcelable>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog_NoActionBar_MinWidth)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialView()
        viewEvent()
    }

    private fun initialView() {
        args.vocabularyFeedback.translations?.let { mTranslationList.addAll(it) }
        tvVocabulary.text = args.vocabularyFeedback.vocabulary

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }

        mAdapter.setList(mTranslationList)
    }

    private fun viewEvent() {
        mAdapter.setCorrectListener {
            val index = mTranslationList.indexOf(it)
            mTranslationList[index] = it.copy(isCorrect = true)
            mAdapter.setList(mTranslationList)
        }

        mAdapter.setIncorrectListener {
            val index = mTranslationList.indexOf(it)
            mTranslationList[index] = it.copy(isCorrect = false)
            mAdapter.setList(mTranslationList)
        }

        btConfirm.setOnClickListener {
            findNavController().popBackStack()
            setFragmentResult(
                VOCABULARY_FEEDBACK,
                bundleOf(
                    VOCABULARY_FEEDBACK_KEY to args.vocabularyFeedback.copy(
                        rating = ratingBar.rating,
                        translations = mTranslationList,
                    )
                )
            )
        }
    }

    companion object {
        const val VOCABULARY_FEEDBACK = "VocabularyFeedback"
        const val VOCABULARY_FEEDBACK_KEY = "vocabularyFeedbackKey"
    }

}
