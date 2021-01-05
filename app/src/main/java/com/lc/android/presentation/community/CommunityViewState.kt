package com.lc.android.presentation.community

import com.lc.server.models.model.UserInfo

data class CommunityViewState(
    val userInfoList: List<UserInfo> = emptyList(),
    val isLoading: Boolean = false,
)
