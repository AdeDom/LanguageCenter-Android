package com.lc.library.domain.usecase

import com.lc.library.data.repository.Resource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.usecase.FetchChatGroup
import com.lc.server.models.response.FetchChatGroupResponse

class FetchChatGroupImpl(
    private val repository: LanguageCenterRepository
) : FetchChatGroup {
    override suspend fun invoke(): Resource<FetchChatGroupResponse> {
        return repository.callFetchChatGroup()
    }
}
