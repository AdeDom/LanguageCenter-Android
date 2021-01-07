package com.lc.android.presentation.chatgroup

import com.lc.server.models.model.ChatGroup

data class ChatGroupViewState(
    val chatGroups: List<ChatGroup> = emptyList(),
    val isLoading: Boolean = false,
    val isClickable: Boolean = true,
)
