package com.lc.android.presentation.community

import com.lc.server.models.model.Community

data class CommunityViewState(
    val communities: List<Community> = emptyList(),
    val isLoading: Boolean = false,
)
