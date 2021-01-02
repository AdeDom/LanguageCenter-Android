package com.lc.android.presentation.editprofile

sealed class EditProfileGenderEvent {
    object Male : EditProfileGenderEvent()
    object Female : EditProfileGenderEvent()
}
