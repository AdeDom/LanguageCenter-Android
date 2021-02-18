package com.lc.android.presentation.friendgroupdetail

import com.lc.android.presentation.model.ChatGroup
import com.lc.server.models.model.ChatGroupDetail

data class FriendGroupDetailViewState(
    val chatGroupId: Int = 0,
    val otherChatGroups: List<ChatGroup> = emptyList(),
    val friendUserId: String? = "",
    val chatGroupDetails: List<ChatGroupDetail> = emptyList(),
    val isLoading: Boolean = false,
    val isClickable: Boolean = true,
)
