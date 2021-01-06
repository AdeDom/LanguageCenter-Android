package com.lc.library.domain.usecase

import com.lc.library.data.repository.Resource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.usecase.FetchChatGroupDetail
import com.lc.server.models.response.FetchChatGroupDetailResponse

class FetchChatGroupDetailImpl(
    private val repository: LanguageCenterRepository
) : FetchChatGroupDetail {
    override suspend fun invoke(chatGroupId: Int?): Resource<FetchChatGroupDetailResponse> {
        return repository.callFetchChatGroupDetail(chatGroupId)
    }
}
