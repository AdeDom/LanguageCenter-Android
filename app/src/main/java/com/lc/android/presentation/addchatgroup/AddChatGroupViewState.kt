package com.lc.android.presentation.addchatgroup

data class AddChatGroupViewState(
    val groupName: String = "",
    val isLoading: Boolean = false,
    val isClickable: Boolean = true,
)
