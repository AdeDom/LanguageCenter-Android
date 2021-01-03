package com.lc.android.presentation.edit.localelearning

import android.os.Bundle
import com.lc.android.R
import com.lc.android.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_locale_learning.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditLocaleLearningActivity : BaseActivity() {

    private val viewModel by viewModel<EditLocaleLearningViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_locale_learning)

        observeViewModel()
        viewEvent()
    }

    private fun observeViewModel() {
        viewModel.state.observe { state ->
        }
    }

    private fun viewEvent() {
        btCancel.setOnClickListener {
            finish()
        }
    }

}
