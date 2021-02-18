package com.lc.android.presentation.friendgroup

import com.lc.server.models.model.ChatGroup

data class FriendGroupViewState(
    val chatGroups: List<ChatGroup> = emptyList(),
    val chatGroup: ChatGroup? = null,
    val isLoading: Boolean = false,
    val isClickable: Boolean = true,
)
