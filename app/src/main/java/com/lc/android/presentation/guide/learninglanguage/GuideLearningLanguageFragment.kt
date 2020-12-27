package com.lc.android.presentation.guide.learninglanguage

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.lc.android.R
import com.lc.android.base.BaseFragment
import com.lc.android.util.toast
import kotlinx.android.synthetic.main.fragment_guide_learning_language.*

class GuideLearningLanguageFragment : BaseFragment(R.layout.fragment_guide_learning_language) {

    private val args by navArgs<GuideLearningLanguageFragmentArgs>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initialView()
        viewEvent()
    }

    private fun initialView() {
        context.toast("$args")
    }

    private fun viewEvent() {
        btSkip.setOnClickListener {
            findNavController().navigate(R.id.action_guideLearningLanguageFragment_to_guideGenderFragment)
        }
    }

}
