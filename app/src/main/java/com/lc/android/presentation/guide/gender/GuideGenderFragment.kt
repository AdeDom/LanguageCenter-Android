package com.lc.android.presentation.guide.gender

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.lc.android.R
import com.lc.android.base.BaseFragment
import com.lc.android.util.clicks
import kotlinx.android.synthetic.main.fragment_guide_gender.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import org.koin.androidx.viewmodel.ext.android.viewModel

@FlowPreview
@ExperimentalCoroutinesApi
class GuideGenderFragment : BaseFragment(R.layout.fragment_guide_gender) {

    private val args by navArgs<GuideGenderFragmentArgs>()
    private val viewModel by viewModel<GuideGenderViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observeViewModel()
        viewEvent()
    }

    private fun observeViewModel() {
        viewModel.state.observe { state ->
            when (state.genderEvent) {
                is GuideGenderEvent.Male -> ivGender.setImageResource(R.drawable.ic_male)
                is GuideGenderEvent.Female -> ivGender.setImageResource(R.drawable.ic_female)
            }
        }
    }

    private fun viewEvent() {
        merge(
            rbMale.clicks().map { GuideGenderEvent.Male },
            rbFemale.clicks().map { GuideGenderEvent.Female },
        ).observe { viewModel.process(it) }

        btNext.setOnClickListener {
            val navDirections = GuideGenderFragmentDirections
                .actionGuideGenderFragmentToGuideBirthDateFragment(
                    args.guideUpdateProfile.copy(gender = viewModel.state.value?.gender)
                )
            findNavController().navigate(navDirections)
        }
    }

}
