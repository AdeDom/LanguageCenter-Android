package com.lc.library.domain.usecase

import com.lc.library.data.repository.Resource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.usecase.FetchChatGroupUseCase
import com.lc.server.models.response.FetchChatGroupResponse

class FetchChatGroupUseCaseImpl(
    private val repository: LanguageCenterRepository
) : FetchChatGroupUseCase {
    override suspend fun invoke(): Resource<FetchChatGroupResponse> {
        return repository.callFetchChatGroup()
    }
}
