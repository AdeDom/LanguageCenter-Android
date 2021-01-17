package com.lc.library.domain.usecase

import com.lc.library.data.repository.Resource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.usecase.FetchFriendInfoUseCase
import com.lc.server.models.request.FetchFriendInfoResponse

class FetchFriendInfoUseCaseImpl(
    private val repository: LanguageCenterRepository,
) : FetchFriendInfoUseCase {
    override suspend fun invoke(): Resource<FetchFriendInfoResponse> {
        return repository.callFetchFriendInfo()
    }
}
