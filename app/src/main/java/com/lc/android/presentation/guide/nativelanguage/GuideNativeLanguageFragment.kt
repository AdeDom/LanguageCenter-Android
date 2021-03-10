package com.lc.android.presentation.guide.nativelanguage

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lc.android.R
import com.lc.android.base.BaseFragment
import com.lc.android.presentation.model.GuideUpdateProfileParcelable
import com.lc.android.presentation.model.UserInfoLocaleParcelable
import kotlinx.android.synthetic.main.fragment_guide_native_language.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class GuideNativeLanguageFragment : BaseFragment(R.layout.fragment_guide_native_language) {

    private val viewModel by viewModel<GuideNativeLanguageViewModel>()
    private val mAdapter by lazy { GuideNativeLanguageAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialView()
        observeViewModel()
        viewEvent()
    }

    private fun initialView() {
        recyclerViewLocalNatives.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.attachFirstTime.observe {
            viewModel.getSettingLocaleNatives()
        }

        viewModel.state.observe { state ->
            mAdapter.setList(state.localNatives)
        }

        viewModel.error.observeError()
    }

    private fun viewEvent() {
        btNext.setOnClickListener {
            val localNatives = mAdapter.list
                .filter { it.isChecked }
                .map {
                    UserInfoLocaleParcelable(
                        locale = it.locale,
                        level = it.level,
                    )
                }
            val guideUpdateProfileRequest = GuideUpdateProfileParcelable(
                localNatives = localNatives
            )
            val navDirections = GuideNativeLanguageFragmentDirections
                .actionGuideNativeLanguageFragmentToGuideLearningLanguageFragment(
                    guideUpdateProfileRequest
                )
            findNavController().navigate(navDirections)
        }
    }

}
