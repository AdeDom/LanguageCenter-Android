package com.lc.android.presentation.guide.birthdate

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.lc.android.R
import com.lc.android.base.BaseFragment
import com.lc.android.util.snackbar
import kotlinx.android.synthetic.main.fragment_guide_birth_date.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class GuideBirthDateFragment : BaseFragment(R.layout.fragment_guide_birth_date) {

    private val args by navArgs<GuideBirthDateFragmentArgs>()
    private val viewModel by viewModel<GuideBirthDateViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

            tvBirthDate.text = getStringBirthDate(state.birthDateLong)

            tvAge.text = getString(R.string.str_age, viewModel.getAge(state.birthDateLong))
        }

        viewModel.error.observeError()

        viewModel.guideUpdateProfileEvent.observe { response ->
            if (response.success) {
                requireView().snackbar(response.message, Snackbar.LENGTH_SHORT)
                findNavController().navigate(R.id.action_global_splashScreenFragment)
                activity?.finishAffinity()
            } else {
                requireView().snackbar(response.message)
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
        val calendar = Calendar.getInstance().apply {
            viewModel.state.value?.birthDateLong?.let { birthDateLong ->
                timeInMillis = birthDateLong
            }
        }
        DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                viewModel.setStateBirthDate(calendar.timeInMillis)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun getStringBirthDate(time: Long?): String {
        val calendar = Calendar.getInstance().apply {
            time?.let {
                timeInMillis = time
            }
        }
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        return "$dayOfMonth/${month.plus(1)}/$year"
    }

}
