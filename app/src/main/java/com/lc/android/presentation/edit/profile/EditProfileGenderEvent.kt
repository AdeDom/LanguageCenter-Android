package com.lc.android.presentation.edit.profile

sealed class EditProfileGenderEvent {
    object Male : EditProfileGenderEvent()
    object Female : EditProfileGenderEvent()
}
