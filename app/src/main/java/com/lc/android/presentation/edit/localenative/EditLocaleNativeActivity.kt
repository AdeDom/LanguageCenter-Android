package com.lc.android.presentation.edit.localenative

import android.os.Bundle
import com.lc.android.R
import com.lc.android.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_locale_native.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditLocaleNativeActivity : BaseActivity() {

    private val viewModel by viewModel<EditLocaleNativeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_locale_native)

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
