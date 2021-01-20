package com.lc.android.presentation.talk

data class TalkViewState(
    val messages: String = "",
    val isSendMessage: Boolean = false,
    val isLoading: Boolean = false,
)
