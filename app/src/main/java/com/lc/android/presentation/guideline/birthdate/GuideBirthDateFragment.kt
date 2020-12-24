package com.lc.android.presentation.guideline.birthdate

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.lc.android.R
import com.lc.android.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_guideline_birth_date.*

class GuideBirthDateFragment : BaseFragment(R.layout.fragment_guideline_birth_date) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btSkip.setOnClickListener {
            findNavController().navigate(R.id.action_global_splashScreenFragment)
            activity?.finishAffinity()
        }
    }

}
