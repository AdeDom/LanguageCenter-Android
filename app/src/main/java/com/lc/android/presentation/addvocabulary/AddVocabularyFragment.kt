package com.lc.android.presentation.addvocabulary

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.lc.android.R
import com.lc.android.base.BaseFragment
import com.lc.android.util.snackbar
import kotlinx.android.synthetic.main.fragment_add_vocabulary.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddVocabularyFragment : BaseFragment(R.layout.fragment_add_vocabulary) {

    private val viewModel by viewModel<AddVocabularyViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        viewEvent()
    }

    private fun observeViewModel() {
        viewModel.state.observe { state ->
            animationLoading.isVisible = state.isLoading
            btConfirm.isClickable = state.isClickable
            btCancel.isClickable = state.isClickable
        }

        viewModel.addVocabularyTranslationEvent.observe { response ->
            if (response.success) {
                findNavController().popBackStack()
                requireView().snackbar(response.message, Snackbar.LENGTH_SHORT)
            } else {
                requireView().snackbar(response.message)
            }
        }

        viewModel.error.observeError()
    }

    private fun viewEvent() {
        etVocabulary.addTextChangedListener { viewModel.setStateVocabulary(it.toString()) }
        etTranslation.addTextChangedListener { viewModel.setStateTranslation(it.toString()) }

        btConfirm.setOnClickListener {
            viewModel.callAddVocabularyTranslation(source = "en", target = "th")
        }

        btCancel.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}
