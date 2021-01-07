package com.lc.android.presentation.edit.profile

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.lc.android.R
import com.lc.android.base.BaseFragment
import com.lc.android.util.clicks
import com.lc.android.util.hideSoftKeyboard
import com.lc.android.util.toast
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

@FlowPreview
@ExperimentalCoroutinesApi
class EditProfileFragment : BaseFragment(R.layout.fragment_edit_profile) {

    private val viewModel by viewModel<EditProfileViewModel>()

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

            when (state.genderEvent) {
                is EditProfileGenderEvent.Male -> rbMale.isChecked = true
                is EditProfileGenderEvent.Female -> rbFemale.isChecked = true
            }

            tvBirthDate.text = getStringBirthDate(state.birthDateLong)

            rbMale.isClickable = state.isClickable
            rbFemale.isClickable = state.isClickable
            btConfirm.isClickable = state.isClickable
            btCancel.isClickable = state.isClickable
            layoutBirthDate.isClickable = state.isClickable
            tvBirthDate.isClickable = state.isClickable
            ivBirthDate.isClickable = state.isClickable
        }

        viewModel.getDbUserInfoLiveData.observe(viewLifecycleOwner, { userInfo ->
            if (userInfo == null) return@observe

            val (_, _, givenName, familyName, _, _, _, _, _, _, aboutMe) = userInfo
            etGivenName.setText(givenName)
            etFamilyName.setText(familyName)
            etAboutMe.setText(aboutMe)
        })

        viewModel.editProfileEvent.observe { response ->
            if (response.success) {
                context.toast(response.message)
                findNavController().popBackStack()
            } else {
                context.toast(response.message, Toast.LENGTH_LONG)
            }
        }

        viewModel.error.observeError()
    }

    private fun viewEvent() {
        etGivenName.addTextChangedListener { viewModel.setStateGivenName(it.toString()) }
        etFamilyName.addTextChangedListener { viewModel.setStateFamilyName(it.toString()) }
        etAboutMe.addTextChangedListener { viewModel.setStateAboutMe(it.toString()) }

        merge(
            rbMale.clicks().map { EditProfileGenderEvent.Male },
            rbFemale.clicks().map { EditProfileGenderEvent.Female },
        ).observe { viewModel.process(it) }

        layoutBirthDate.setOnClickListener {
            selectBirthDate()
        }

        tvBirthDate.setOnClickListener {
            selectBirthDate()
        }

        ivBirthDate.setOnClickListener {
            selectBirthDate()
        }

        btConfirm.setOnClickListener {
            viewModel.callEditProfile()
        }

        btCancel.setOnClickListener {
            findNavController().popBackStack()
        }

        rootLayout.setOnClickListener {
            activity?.hideSoftKeyboard()
        }
    }

    private fun selectBirthDate() {
        val calendar = Calendar.getInstance().apply {
            viewModel.state.value?.birthDateLong?.let { birthDateLong ->
                timeInMillis = birthDateLong
            }
        }
        DatePickerDialog(
            requireActivity(),
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
