package com.lc.android.presentation.guideline.gender

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.lc.android.R
import com.lc.android.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_guideline_gender.*

class GuideGenderFragment : BaseFragment(R.layout.fragment_guideline_gender) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btSkip.setOnClickListener {
            findNavController().navigate(R.id.action_guideGenderFragment_to_guideBirthDateFragment)
        }
    }

}
