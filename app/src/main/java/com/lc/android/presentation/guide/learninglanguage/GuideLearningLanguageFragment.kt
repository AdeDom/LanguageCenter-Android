package com.lc.android.presentation.guide.learninglanguage

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.lc.android.R
import com.lc.android.base.BaseFragment
import com.lc.android.presentation.model.UserInfoLocaleParcelable
import kotlinx.android.synthetic.main.fragment_guide_learning_language.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class GuideLearningLanguageFragment : BaseFragment(R.layout.fragment_guide_learning_language) {

    private val args by navArgs<GuideLearningLanguageFragmentArgs>()
    private val viewModel by viewModel<GuideLearningLanguageViewModel>()
    private val mAdapter by lazy { GuideLearningLanguageAdapter() }

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
            viewModel.getSettingLocaleLearnings()
        }

        viewModel.state.observe { state ->
            mAdapter.setList(state.localLearnings)
        }

        viewModel.error.observeError()
    }

    private fun viewEvent() {
        btNext.setOnClickListener {
            val localLearnings = mAdapter.list
                .filter { it.isChecked }
                .map {
                    UserInfoLocaleParcelable(
                        locale = it.locale,
                        level = it.level,
                    )
                }
            val navDirections = GuideLearningLanguageFragmentDirections
                .actionGuideLearningLanguageFragmentToGuideGenderFragment(
                    args.guideUpdateProfile.copy(localLearnings = localLearnings)
                )
            findNavController().navigate(navDirections)
        }
    }

}
