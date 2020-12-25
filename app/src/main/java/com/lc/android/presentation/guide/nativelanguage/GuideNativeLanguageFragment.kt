package com.lc.android.presentation.guide.nativelanguage

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.lc.android.R
import com.lc.android.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_guide_native_language.*

class GuideNativeLanguageFragment : BaseFragment(R.layout.fragment_guide_native_language) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btSkip.setOnClickListener {
            findNavController().navigate(R.id.action_guideNativeLanguageFragment_to_guideLearningLanguageFragment)
        }
    }

}
