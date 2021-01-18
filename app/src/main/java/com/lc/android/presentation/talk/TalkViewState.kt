package com.lc.android.presentation.talk

data class TalkViewState(
    val toUserId: String? = "",
    val messages: String = "",
    val isSendMessage: Boolean = false,
    val isLoading: Boolean = false,
)
