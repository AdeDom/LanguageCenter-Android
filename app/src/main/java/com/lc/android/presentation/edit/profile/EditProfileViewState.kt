package com.lc.android.presentation.edit.profile

data class EditProfileViewState(
    val givenName: String? = "",
    val familyName: String? = "",
    val aboutMe: String? = "",
    val genderEvent: EditProfileGenderEvent? = null,
    val gender: String? = null,
    val birthDateLong: Long? = null,
    val isLoading: Boolean = false,
    val isClickable: Boolean = true,
)
