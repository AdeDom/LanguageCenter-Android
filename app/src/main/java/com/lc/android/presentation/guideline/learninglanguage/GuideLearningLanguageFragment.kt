package com.lc.android.presentation.guideline.learninglanguage

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.lc.android.R
import com.lc.android.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_guideline_learning_language.*

class GuideLearningLanguageFragment : BaseFragment(R.layout.fragment_guideline_learning_language) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btSkip.setOnClickListener {
            findNavController().navigate(R.id.action_guideLearningLanguageFragment_to_guideGenderFragment)
        }
    }

}
