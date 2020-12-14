package com.lc.android.presentation.profile

data class ProfileViewState(
    val name: String = "",
    val picture: String? = null,
    val isLoading: Boolean = false
)
