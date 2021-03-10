package com.lc.android.presentation.edit.localelearning

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.lc.android.R
import com.lc.android.base.BaseFragment
import com.lc.android.util.snackbar
import kotlinx.android.synthetic.main.fragment_edit_locale_learning.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditLocaleLearningFragment : BaseFragment(R.layout.fragment_edit_locale_learning) {

    private val viewModel by viewModel<EditLocaleLearningViewModel>()
    private val mAdapter by lazy { EditLocaleLearningAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialView()
        observeViewModel()
        viewEvent()
    }

    private fun initialView() {
        recyclerViewLocalLearnings.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.attachFirstTime.observe {
            viewModel.getLocaleLearnings()
        }

        viewModel.state.observe { state ->
            animationLoading.isVisible = state.isLoading
            btConfirm.isClickable = state.isClickable
            btCancel.isClickable = state.isClickable

            mAdapter.setList(state.localLearnings)
        }

        viewModel.editLocaleLearningEvent.observe { response ->
            if (response.success) {
                requireView().snackbar(response.message, Snackbar.LENGTH_SHORT)
                findNavController().popBackStack()
            } else {
                requireView().snackbar(response.message)
            }
        }

        viewModel.error.observeError()
    }

    private fun viewEvent() {
        btConfirm.setOnClickListener {
            val list = mAdapter.list.filter { it.isChecked }
            viewModel.callEditLocale(list)
        }

        btCancel.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}
