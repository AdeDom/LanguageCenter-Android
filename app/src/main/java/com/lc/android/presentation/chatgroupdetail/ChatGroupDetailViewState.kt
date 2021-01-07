package com.lc.android.presentation.chatgroupdetail

import com.lc.server.models.model.ChatGroupDetail

data class ChatGroupDetailViewState(
    val chatGroupDetails: List<ChatGroupDetail> = emptyList(),
    val isLoading: Boolean = false,
)
