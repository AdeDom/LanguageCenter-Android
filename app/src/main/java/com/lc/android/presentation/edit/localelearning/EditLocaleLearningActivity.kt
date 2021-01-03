package com.lc.android.presentation.edit.localelearning

import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.lc.android.R
import com.lc.android.base.BaseActivity
import com.lc.android.util.toast
import kotlinx.android.synthetic.main.activity_edit_locale_learning.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditLocaleLearningActivity : BaseActivity() {

    private val viewModel by viewModel<EditLocaleLearningViewModel>()
    private val mAdapter by lazy { EditLocaleLearningAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_locale_learning)

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
                toast(response.message)
                finish()
            } else {
                toast(response.message, Toast.LENGTH_LONG)
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
            finish()
        }
    }

}
