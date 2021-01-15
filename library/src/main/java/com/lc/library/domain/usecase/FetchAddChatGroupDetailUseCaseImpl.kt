package com.lc.library.domain.usecase

import com.lc.library.data.repository.Resource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.usecase.FetchAddChatGroupDetailUseCase
import com.lc.server.models.response.FetchAddChatGroupDetailResponse

class FetchAddChatGroupDetailUseCaseImpl(
    private val repository: LanguageCenterRepository,
) : FetchAddChatGroupDetailUseCase {
    override suspend fun invoke(): Resource<FetchAddChatGroupDetailResponse> {
        return repository.callFetchAddChatGroupDetail()
    }
}
