package com.lc.android.presentation.vocabularydetail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.lc.android.R
import com.lc.android.base.BaseFragment
import com.lc.android.presentation.model.UserInfoLocaleParcelable
import com.lc.android.presentation.model.UserInfoParcelable
import com.lc.android.util.toast
import kotlinx.android.synthetic.main.fragment_vocabulary_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class VocabularyDetailFragment : BaseFragment(R.layout.fragment_vocabulary_detail) {

    private val viewModel by viewModel<VocabularyDetailViewModel>()
    private val args by navArgs<VocabularyDetailFragmentArgs>()
    private val mAdapter by lazy { VocabularyDetailAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.callFetchVocabularyDetailUseCase(args.vocabularyGroupId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialView()
        observeViewModel()
        viewEvent()
    }

    private fun initialView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe { state ->
            animationLoading.isVisible = state.isLoading

            mAdapter.submitList(state.vocabularies)
        }

        viewModel.error.observeError()
    }

    private fun viewEvent() {
        mAdapter.userInfoListener { community ->
            val userInfoParcelable = UserInfoParcelable(
                userId = community?.userId,
                email = community?.email,
                givenName = community?.givenName,
                familyName = community?.familyName,
                name = community?.name,
                picture = community?.picture,
                gender = community?.gender,
                age = community?.age,
                birthDateString = community?.birthDateString,
                birthDateLong = community?.birthDateLong,
                aboutMe = community?.aboutMe,
                localNatives = community?.localNatives?.map {
                    UserInfoLocaleParcelable(locale = it.locale, level = it.level)
                },
                localLearnings = community?.localLearnings?.map {
                    UserInfoLocaleParcelable(locale = it.locale, level = it.level)
                },
            )
            val navDirections = VocabularyDetailFragmentDirections
                .actionVocabularyDetailFragmentToUserInfoFragment(null, false, userInfoParcelable)
            findNavController().navigate(navDirections)
        }

        mAdapter.vocabularyListener {
            viewModel.setCopyTextMessage(it)
            val vocabulary = "Copy $it"
            context.toast(vocabulary)
        }

        mAdapter.translateListener {
            viewModel.setCopyTextMessage(it)
            val translate = "Copy $it"
            context.toast(translate)
        }
    }

}
