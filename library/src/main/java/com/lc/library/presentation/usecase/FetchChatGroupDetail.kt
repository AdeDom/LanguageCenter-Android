package com.lc.library.presentation.usecase

import com.lc.library.data.repository.Resource
import com.lc.server.models.response.FetchChatGroupDetailResponse

interface FetchChatGroupDetail {
    suspend operator fun invoke(chatGroupId: Int?): Resource<FetchChatGroupDetailResponse>
}
