package com.lc.library.domain.usecase

import com.lc.library.data.repository.Resource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.usecase.FetchCommunityUseCase
import com.lc.server.models.response.FetchCommunityResponse

class FetchCommunityUseCaseImpl(
    private val repository: LanguageCenterRepository
) : FetchCommunityUseCase {
    override suspend fun invoke(): Resource<FetchCommunityResponse> {
        return repository.callFetchCommunity()
    }
}
