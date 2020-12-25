package com.lc.android.presentation.guide.birthdate

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.lc.android.R
import com.lc.android.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_guide_birth_date.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class GuideBirthDateFragment : BaseFragment(R.layout.fragment_guide_birth_date) {

    private val viewModel by viewModel<GuideBirthDateViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observeViewModel()
        viewEvent()
    }

    private fun observeViewModel() {
        viewModel.state.observe { state ->
            tvBirthDate.text = state.birthDateString

            state.age?.let { tvAge.text = getString(R.string.str_age, it) }
        }
    }

    private fun viewEvent() {
        ivBirthDate.setOnClickListener {
            selectBirthDate()
        }

        btBirthDate.setOnClickListener {
            selectBirthDate()
        }

        btSkip.setOnClickListener {
            findNavController().navigate(R.id.action_global_splashScreenFragment)
            activity?.finishAffinity()
        }
    }

    private fun selectBirthDate() {
        viewModel.state.value?.birthDateCalendar?.let { calendar ->
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

}
