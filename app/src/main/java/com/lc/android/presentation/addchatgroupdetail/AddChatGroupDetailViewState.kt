package com.lc.android.presentation.addchatgroupdetail

import com.lc.server.models.model.AddChatGroupDetail

data class AddChatGroupDetailViewState(
    val addChatGroupDetailList: List<AddChatGroupDetail> = emptyList(),
    val isLoading: Boolean = false,
)
