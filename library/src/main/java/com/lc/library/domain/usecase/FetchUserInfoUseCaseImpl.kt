package com.lc.library.domain.usecase

import com.lc.library.data.repository.Resource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.FetchUserInfoUseCase
import com.lc.server.models.response.UserInfoResponse

class FetchUserInfoUseCaseImpl(
    private val repository: LanguageCenterRepository
) : FetchUserInfoUseCase {
    override suspend fun invoke(): Resource<UserInfoResponse> {
        return repository.callFetchUserInfo()
    }
}
