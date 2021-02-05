package com.lc.android.presentation.addvocabulary

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.lc.android.R
import com.lc.android.base.BaseFragment
import com.lc.android.util.snackbar
import com.lc.server.util.LanguageCenterConstant
import kotlinx.android.synthetic.main.fragment_add_vocabulary.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddVocabularyFragment : BaseFragment(R.layout.fragment_add_vocabulary) {

    private val viewModel by viewModel<AddVocabularyViewModel>()
    private val args by navArgs<AddVocabularyFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        viewEvent()
    }

    private fun observeViewModel() {
        viewModel.attachFirstTime.observe {
            viewModel.setStateSourceAndTarget(source = args.source, target = args.target)
        }

        viewModel.state.observe { state ->
            // vocabulary source
            when (state.source) {
                LanguageCenterConstant.THAI -> tvVocabularyText.text = getString(R.string.th)
                LanguageCenterConstant.ENGLISH -> tvVocabularyText.text = getString(R.string.en)
            }

            // translation target
            when (state.target) {
                LanguageCenterConstant.THAI -> tvTranslationText.text = getString(R.string.th)
                LanguageCenterConstant.ENGLISH -> tvTranslationText.text = getString(R.string.en)
            }

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

        ibSwitch.setOnClickListener {
            viewModel.onSwitchTranslate()
        }

        btConfirm.setOnClickListener {
            viewModel.callAddVocabularyTranslation()
        }

        btCancel.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}
