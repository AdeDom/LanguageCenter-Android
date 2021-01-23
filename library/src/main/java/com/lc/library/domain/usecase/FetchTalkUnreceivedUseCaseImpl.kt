package com.lc.library.domain.usecase

import com.lc.library.data.repository.Resource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.usecase.FetchTalkUnreceivedUseCase
import com.lc.server.models.response.FetchTalkUnreceivedResponse

class FetchTalkUnreceivedUseCaseImpl(
    private val repository: LanguageCenterRepository,
) : FetchTalkUnreceivedUseCase {
    override suspend fun invoke(): Resource<FetchTalkUnreceivedResponse> {
        return repository.callFetchTalkUnreceived()
    }
}
