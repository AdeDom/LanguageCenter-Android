package com.lc.android.presentation.guide.birthdate

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.lc.android.R
import com.lc.android.base.BaseFragment
import com.lc.android.util.toast
import kotlinx.android.synthetic.main.fragment_guide_birth_date.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class GuideBirthDateFragment : BaseFragment(R.layout.fragment_guide_birth_date) {

    private val args by navArgs<GuideBirthDateFragmentArgs>()
    private val viewModel by viewModel<GuideBirthDateViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observeViewModel()
        viewEvent()
    }

    private fun observeViewModel() {
        viewModel.attachFirstTime.observe {
            viewModel.getDbUserInfo()
        }

        viewModel.state.observe { state ->
            animationLoading.isVisible = state.isLoading

            ivBirthDate.isClickable = state.isClickable
            btBirthDate.isClickable = state.isClickable
            btConfirm.isClickable = state.isClickable

            tvBirthDate.text = state.birthDateString

            state.age?.let { tvAge.text = getString(R.string.str_age, it) }
        }

        viewModel.error.observeError()

        viewModel.guideUpdateProfileEvent.observe { response ->
            context.toast(response.message)
            if (response.success) {
                findNavController().navigate(R.id.action_global_splashScreenFragment)
                activity?.finishAffinity()
            }
        }
    }

    private fun viewEvent() {
        ivBirthDate.setOnClickListener {
            selectBirthDate()
        }

        btBirthDate.setOnClickListener {
            selectBirthDate()
        }

        btConfirm.setOnClickListener {
            viewModel.callGuideUpdateProfile(args.guideUpdateProfile)
        }
    }

    private fun selectBirthDate() {
        val calendar = viewModel.state.value?.birthDateCalendar ?: Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                viewModel.setStateBirthDate(calendar)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

}
